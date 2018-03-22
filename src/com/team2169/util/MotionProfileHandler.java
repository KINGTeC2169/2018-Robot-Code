package com.team2169.util;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;
import com.team2169.robot.Constants;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;

import java.util.ArrayList;

import com.ctre.phoenix.motion.*;
import com.ctre.phoenix.motion.TrajectoryPoint.TrajectoryDuration;

public class MotionProfileHandler {

	private MotionProfile mProfile;
	private MotionProfileStatus _status = new MotionProfileStatus();
	double _pos = 0, _vel = 0, _heading = 0;
	private TalonSRX _talon;
	private int _state = 0;
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

	public MotionProfileHandler(TalonSRX talon, MotionProfile profile) {
		_talon = talon;
		mProfile = profile;
		/*
		 * since our MP is 10ms per point, set the control frame rate and the notifer to
		 * half that
		 */
		_talon.changeMotionControlFramePeriod(5);
		_notifer.startPeriodic(0.005);
	}

	public void reset() {

		_talon.clearMotionProfileTrajectories();
		_setValue = SetValueMotionProfile.Disable;
		_state = 0;
		_loopTimeout = -1;
		_bStart = false;
	}

	public void control() {

		_talon.getMotionProfileStatus(_status);

		if (_loopTimeout < 0) {
		} else {
			if (_loopTimeout == 0) {
				System.out.println("MP ERROR");
			} else {
				--_loopTimeout;
			}
		}

		/* first check if we are in MP mode */
		if (_talon.getControlMode() != ControlMode.MotionProfile) {
			_state = 0;
			_loopTimeout = -1;
		
		} else {
			
			switch (_state) {
			case 0: /* wait for application to tell us to start an MP */
				if (_bStart) {
					_bStart = false;

					_setValue = SetValueMotionProfile.Disable;
					startFilling();
					_state = 1;
					_loopTimeout = kNumLoopsTimeout;
				}
				break;
			case 1: 
				if (_status.btmBufferCnt > kMinPointsInTalon) {
					_setValue = SetValueMotionProfile.Enable;
					_state = 2;
					_loopTimeout = kNumLoopsTimeout;
				}
				break;
			case 2: /* check the status of the MP */

				if (_status.isUnderrun == false) {
					_loopTimeout = kNumLoopsTimeout;
				}

				if (_status.activePointValid && _status.isLast) {
					_setValue = SetValueMotionProfile.Hold;
					_state = 0;
					_loopTimeout = -1;
				}
				break;
			}

			_talon.getMotionProfileStatus(_status);
			_heading = _talon.getActiveTrajectoryHeading();
			_pos = _talon.getActiveTrajectoryPosition();
			_vel = _talon.getActiveTrajectoryVelocity();

		}
	}

	private TrajectoryDuration GetTrajectoryDuration(int durationMs) {
		TrajectoryDuration retval = TrajectoryDuration.Trajectory_Duration_0ms;
		retval = retval.valueOf(durationMs);
		if (retval.value != durationMs) {
			DriverStation.reportError(
					"Trajectory Duration not supported - use configMotionProfileTrajectoryPeriod instead", false);
		}

		return retval;
	}

	private void startFilling() {
		/* since this example only has one talon, just update that one */
		startFilling(mProfile.leftPath, mProfile.kNumPoints);
	}

	private void startFilling(ArrayList<ArrayList<Double>> profile, int totalCnt) {

		/* create an empty point */
		TrajectoryPoint point = new TrajectoryPoint();

		if (_status.hasUnderrun) {

			_talon.clearMotionProfileHasUnderrun(0);

		}

		_talon.clearMotionProfileTrajectories();
		_talon.configMotionProfileTrajectoryPeriod(0, 10);

		/* This is fast since it's just into our TOP buffer */
		for (int i = 0; i < totalCnt; ++i) {
			double positionRot = profile.get(i).get(0);
			double velocityRPM = profile.get(i).get(1);
			/* for each point, fill our structure and pass it to API */
			point.position = positionRot * Constants.ticksPerRotation; // Convert Revolutions to Units
			point.velocity = velocityRPM * Constants.ticksPerRotation / 600.0; // Convert RPM to Units/100ms
			point.headingDeg = 0;
			point.profileSlotSelect0 = 0;
			point.profileSlotSelect1 = 0;
			point.timeDur = GetTrajectoryDuration((int) profile.get(i).get(2).doubleValue());
			point.zeroPos = false;
			if (i == 0)
				point.zeroPos = true; /* set this to true on the first point */

			point.isLastPoint = false;
			if ((i + 1) == totalCnt)
				point.isLastPoint = true; /* set this to true on the last point */

			_talon.pushMotionProfileTrajectory(point);
		}
	}

	void startMotionProfile() {
		_bStart = true;
	}

	SetValueMotionProfile getSetValue() {
		return _setValue;
	}
}
