/**
 * Example logic for firing and managing motion profiles.
 * This example sends MPs, waits for them to finish
 * Although this code uses a CANTalon, nowhere in this module do we changeMode() or call set() to change the output.
 * This is done in Robot.java to demonstrate how to change control modes on the fly.
 * 
 * The only routines we call on Talon are....
 * 
 * changeMotionControlFramePeriod
 * 
 * getMotionProfileStatus		
 * clearMotionProfileHasUnderrun     to get status and potentially clear the error flag.
 * 
 * pushMotionProfileTrajectory
 * clearMotionProfileTrajectories
 * processMotionProfileBuffer,   to push/clear, and process the trajectory points.
 * 
 * getControlMode, to check if we are in Motion Profile Control mode.
 * 
 * Example of advanced features not demonstrated here...
 * [1] Calling pushMotionProfileTrajectory() continuously while the Talon executes the motion profile, thereby keeping it going indefinitely.
 * [2] Instead of setting the sensor position to zero at the start of each MP, the program could offset the MP's position based on current position. 
 */
package com.team2169.util.motionProfiling;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;
import com.team2169.robot.Constants;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;

import java.util.ArrayList;

import com.ctre.phoenix.motion.*;
import com.ctre.phoenix.motion.TrajectoryPoint.TrajectoryDuration;

public class ProfileExecuter {

	private MotionProfileStatus _status = new MotionProfileStatus();
	double _pos = 0, _vel = 0, _heading = 0;
	private TalonSRX _talon;
	private ArrayList<MotionProfilePoint> _profile;
	private int _state = 0;
	private boolean done = false;
	private int _loopTimeout = -1;
	private boolean _bStart = false;
	private SetValueMotionProfile _setValue = SetValueMotionProfile.Disable;
	private static final int kMinPointsInTalon = 5;
	private static final int kNumLoopsTimeout = 10;

	class PeriodicRunnable implements java.lang.Runnable {
		public void run() {
			_talon.processMotionProfileBuffer();
		}
	}

	Notifier _notifer = new Notifier(new PeriodicRunnable());

	public ProfileExecuter(TalonSRX talon, ArrayList<MotionProfilePoint> profile) {
		_talon = talon;
		_profile = profile;
		/*
		 * since our MP is 10ms per point, set the control frame rate and the notifer to
		 * half that
		 */
		_talon.changeMotionControlFramePeriod(5);
		_notifer.startPeriodic(0.005);
	}


	public void reset() {
		/*
		 * Let's clear the buffer just in case user decided to disable in the middle of
		 * an MP, and now we have the second half of a profile just sitting in memory.
		 */
		_talon.clearMotionProfileTrajectories();
		/* When we do re-enter motionProfile control mode, stay disabled. */
		_setValue = SetValueMotionProfile.Disable;
		/* When we do start running our state machine start at the beginning. */
		_state = 0;
		_loopTimeout = -1;
		/*
		 * If application wanted to start an MP before, ignore and wait for next button
		 * press
		 */
		_bStart = false;
	}

	public void stop() {
		_notifer.stop();
		_talon.clearMotionProfileTrajectories();
		_talon.clearMotionProfileHasUnderrun(10);
		_talon.set(ControlMode.PercentOutput, 0);
	}
	
	public void control() {
		/* Get the motion profile status every loop */
		_talon.getMotionProfileStatus(_status);

		/*
		 * track time, this is rudimentary but that's okay, we just want to make sure
		 * things never get stuck.
		 */
		if (_loopTimeout < 0) {
			/* do nothing, timeout is disabled */
		} else {
			/* our timeout is nonzero */
			if (_loopTimeout == 0) {
				/*
				 * something is wrong. Talon is not present, unplugged, breaker tripped
				 */
			} else {
				--_loopTimeout;
			}
		}

		/* first check if we are in MP mode */
		if (_talon.getControlMode() != ControlMode.MotionProfile) {
			/*
			 * we are not in MP mode. We are probably driving the robot around using
			 * gamepads or some other mode.
			 */
			_state = 0;
			_loopTimeout = -1;
		} else {
			/*
			 * we are in MP control mode. That means: starting Mps, checking Mp progress,
			 * and possibly interrupting MPs if thats what you want to do.
			 */
			switch (_state) {
			case 0: /* wait for application to tell us to start an MP */
				if (_bStart) {
					_bStart = false;

					_setValue = SetValueMotionProfile.Disable;
					startFilling();
					/*
					 * MP is being sent to CAN bus, wait a small amount of time
					 */
					_state = 1;
					_loopTimeout = kNumLoopsTimeout;
				}
				_setValue = SetValueMotionProfile.Disable;
				break;
			case 1: /*
					 * wait for MP to stream to Talon, really just the first few points
					 */
				/* do we have a minimum numberof points in Talon */
				if (_status.btmBufferCnt > kMinPointsInTalon) {
					/* start (once) the motion profile */
					_setValue = SetValueMotionProfile.Enable;
					/* MP will start once the control frame gets scheduled */
					_state = 2;
					_loopTimeout = kNumLoopsTimeout;
				}
				break;
			case 2: /* check the status of the MP */
				/*
				 * if talon is reporting things are good, keep adding to our timeout. Really
				 * this is so that you can unplug your talon in the middle of an MP and react to
				 * it.
				 */
				if (_status.isUnderrun == false) {
					_loopTimeout = kNumLoopsTimeout;
				}
				/*
				 * If we are executing an MP and the MP finished, start loading another. We will
				 * go into hold state so robot servo's position.
				 */
				if (_status.activePointValid && _status.isLast) {
					/*
					 * because we set the last point's isLast to true, we will get here when the MP
					 * is done
					 */
					done = true;
					_setValue = SetValueMotionProfile.Hold;
					_state = 0;
					_loopTimeout = -1;
				}
				break;
			}

			/* Get the motion profile status every loop */
			_talon.getMotionProfileStatus(_status);
			_heading = _talon.getActiveTrajectoryHeading();
			_pos = _talon.getActiveTrajectoryPosition();
			_vel = _talon.getActiveTrajectoryVelocity();

			/* printfs and/or logging */
		}
	}

	public boolean isDone() {
		return done;
	}
	
	/**
	 * Find enum value if supported.
	 * 
	 * @param durationMs
	 * @return enum equivalent of durationMs
	 */
	private TrajectoryDuration GetTrajectoryDuration(int durationMs) {
		/* create return value */
		TrajectoryDuration retval = TrajectoryDuration.Trajectory_Duration_0ms;
		/* convert duration to supported type */
		retval = retval.valueOf(durationMs);
		/* check that it is valid */
		if (retval.value != durationMs) {
			DriverStation.reportError(
					"Trajectory Duration not supported - use configMotionProfileTrajectoryPeriod instead", false);
		}
		/* pass to caller */
		return retval;
	}

	/** Start filling the MPs to all of the involved Talons. */
	private void startFilling() {
		/* since this example only has one talon, just update that one */
		startFilling(_profile);
	}

	private void startFilling(ArrayList<MotionProfilePoint> profile) {

		int totalCnt = profile.size();
		
		/* create an empty point */
		TrajectoryPoint point = new TrajectoryPoint();

		/* did we get an underrun condition since last time we checked ? */
		if (_status.hasUnderrun) {
			/* better log it so we know about it */
			/*
			 * clear the error. This flag does not auto clear, this way we never miss
			 * logging it.
			 */
			_talon.clearMotionProfileHasUnderrun(0);
		}
		/*
		 * just in case we are interrupting another MP and there is still buffer points
		 * in memory, clear it.
		 */
		_talon.clearMotionProfileTrajectories();

		/*
		 * set the base trajectory period to zero, use the individual trajectory period
		 * below
		 */
		_talon.configMotionProfileTrajectoryPeriod(Constants.kBaseTrajPeriodMs, Constants.kTimeoutMs);

		/* This is fast since it's just into our TOP buffer */
		for (int i = 0; i < totalCnt; ++i) {
			double positionRot = profile.get(i).position;
			double velocityRPM = profile.get(i).velocity;
			/* for each point, fill our structure and pass it to API */
			point.position = positionRot * Constants.ticksPerRotation; // Convert Revolutions to Units
			point.velocity = velocityRPM * Constants.ticksPerRotation / 600.0; // Convert RPM to Units/100ms
			point.headingDeg = 0; /* future feature - not used in this example */
			point.profileSlotSelect0 = 0; /* which set of gains would you like to use [0,3]? */
			point.profileSlotSelect1 = 0; /*
											 * future feature - not used in this example - cascaded PID [0,1], leave
											 * zero
											 */
			point.timeDur = GetTrajectoryDuration((int) profile.get(i).timeDur.value);
			point.zeroPos = false;
			if (i == 0)
				point.zeroPos = true; /* set this to true on the first point */

			point.isLastPoint = false;
			if ((i + 1) == totalCnt)
				point.isLastPoint = true; /* set this to true on the last point */

			_talon.pushMotionProfileTrajectory(point);
		}
	}

	/**
	 * Called by application to signal Talon to start the buffered MP (when it's
	 * able to).
	 */
	void startMotionProfile() {
		_bStart = true;
	}

	/**
	 * 
	 * @return the output value to pass to Talon's set() routine. 0 for disable
	 *         motion-profile output, 1 for enable motion-profile, 2 for hold
	 *         current motion profile trajectory point.
	 */
	SetValueMotionProfile getSetValue() {
		return _setValue;
	}
}
