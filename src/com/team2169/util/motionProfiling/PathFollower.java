package com.team2169.util.motionProfiling;

import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PathFollower {

	ProfileExecuter leftExec;
	ProfileExecuter rightExec;
	TalonSRX _left;
	TalonSRX _right;
	
	public PathFollower(MotionProfilePath path, TalonSRX left, TalonSRX right) {
		
		leftExec = new ProfileExecuter(left, path.leftPath);
		rightExec = new ProfileExecuter(right, path.rightPath);
		_left = left;
		_right = right;
		
	}
	
	public boolean isDone() {
		return leftExec.isDone() && rightExec.isDone();
	}
	
	public void stopPath() {
		leftExec.stop();
		rightExec.stop();
	}
	
	public void startPath() {
		leftExec.startMotionProfile();
		rightExec.startMotionProfile();
	}
	
	public void resetPath() {
		leftExec.reset();
		rightExec.reset();
	}

	public void pathLooper() {
		
		//Execute Both Control Loops
		leftExec.control();
		rightExec.control();
		
		//Set Left Talon to it's correct MP state
		SetValueMotionProfile leftSetOutput = leftExec.getSetValue();
		SmartDashboard.putNumber("Left Output", _left.getMotorOutputPercent());
		SmartDashboard.putNumber("Left Desired Output", _left.getActiveTrajectoryVelocity());
		_left.set(ControlMode.MotionProfile, leftSetOutput.value);

		//Set Right Talon to it's correct MP state
		SetValueMotionProfile rightSetOutput = rightExec.getSetValue();
		SmartDashboard.putNumber("Right Desired Output", _right.getActiveTrajectoryVelocity());
		SmartDashboard.putNumber("Right Output", _right.getMotorOutputPercent());
		_right.set(ControlMode.MotionProfile, rightSetOutput.value);
		
		
	}
	
}
