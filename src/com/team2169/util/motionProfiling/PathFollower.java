package com.team2169.util.motionProfiling;

import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

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
		_left.set(ControlMode.MotionProfile, leftSetOutput.value);


		//Set Left Talon to it's correct MP state
		SetValueMotionProfile rightSetOutput = leftExec.getSetValue();
		_right.set(ControlMode.MotionProfile, rightSetOutput.value);

	}
	
}
