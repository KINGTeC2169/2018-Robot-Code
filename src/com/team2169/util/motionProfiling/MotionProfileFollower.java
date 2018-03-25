package com.team2169.util.motionProfiling;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.*;
import com.team2169.robot.Constants;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Notifier;

import java.util.ArrayList;

import com.ctre.phoenix.motion.*;
import com.ctre.phoenix.motion.TrajectoryPoint.TrajectoryDuration;

public class MotionProfileFollower {

	private ArrayList<MotionProfilePoint> mProfile;
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

	public MotionProfileFollower(TalonSRX talon, ArrayList<MotionProfilePoint> profile) {
		_talon = talon;
		mProfile = profile;
		
		_talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		_talon.setSensorPhase(true); /* keep sensor and motor in phase */
		_talon.configNeutralDeadband(Constants.kNeutralDeadband, Constants.kTimeoutMs);

		_talon.config_kF(0, 0.076, Constants.kTimeoutMs);
		_talon.config_kP(0, 2.000, Constants.kTimeoutMs);
		_talon.config_kI(0, 0.0, Constants.kTimeoutMs);
		_talon.config_kD(0, 20.0, Constants.kTimeoutMs);

		/* Our profile uses 10ms timing */
		_talon.configMotionProfileTrajectoryPeriod(10, Constants.kTimeoutMs); 
		/*
		 * status 10 provides the trajectory target for motion profile AND
		 * motion magic
		 */
		_talon.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);
		
		/*
		 * since our MP is 10ms per point, set the control frame rate and the notifer to
		 * half that
		 */
		_talon.changeMotionControlFramePeriod(5);
		_notifer.startPeriodic(0.005);
	}

	public void reset() {

		System.out.println("reset");
		
		_talon.clearMotionProfileTrajectories();
		_setValue = SetValueMotionProfile.Disable;
		_state = 0;
		_loopTimeout = -1;
		_bStart = false;
	}

	public void control() {
		
		_talon.getMotionProfileStatus(_status);

		System.out.println("_bStart: " + _bStart);

		if (_loopTimeout < 0) {
		} else {
			if (_loopTimeout == 0) {
				System.out.println("MP ERROR");
			} else {
				--_loopTimeout;
			}
		}
		
		System.out.println(_talon.getControlMode().name());
		
		/* first check if we are in MP mode */
		if (_talon.getControlMode() != ControlMode.MotionProfile) {
			
			_state = 0;
			_loopTimeout = -1;
		
		} else {
			
			System.out.println("State: " + _state);

			
			switch (_state) {
			case 0: /* wait for application to tell us to start an MP */
				if (_bStart) {
					_bStart = false;
					System.out.println("Starting");
					_setValue = SetValueMotionProfile.Disable;
					startFilling();
					_state = 1;
					_loopTimeout = kNumLoopsTimeout;
				}
				break;
			case 1: 
				if (_status.btmBufferCnt > kMinPointsInTalon) {
					System.out.println("Running");
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

	@SuppressWarnings("unused")
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
		startFilling(mProfile, mProfile.size());
	}

	private void startFilling(ArrayList<MotionProfilePoint> profile, int totalCnt) {

		/* create an empty point */
		TrajectoryPoint point = new TrajectoryPoint();

		if (_status.hasUnderrun) {

			_talon.clearMotionProfileHasUnderrun(0);

		}

		_talon.clearMotionProfileTrajectories();
		_talon.configMotionProfileTrajectoryPeriod(0, 10);

		/* This is fast since it's just into our TOP buffer */
		for (int i = 0; i < totalCnt; ++i) {

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
