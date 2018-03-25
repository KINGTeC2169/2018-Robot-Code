package com.team2169.util.motionProfiling;

import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class PathFollower {

	TalonSRX left;
	TalonSRX right;
	MotionProfilePath path;
	MotionProfileFollower leftFollower;
	MotionProfileFollower rightFollower;
	
	public PathFollower(TalonSRX left_, TalonSRX right_, MotionProfilePath path_) {
		left = left_;
		right = right_;
		path = path_;
		
		leftFollower = new MotionProfileFollower(left, path.leftPath);
		rightFollower = new MotionProfileFollower(right, path.rightPath);
		
	}
	
	public void start() {
		leftFollower.startMotionProfile();
		rightFollower.startMotionProfile();
	}
	
	public void loop() {
		System.out.println("Left Data:");
		leftFollower.control();
		System.out.println("Right Data:");
		rightFollower.control();
		
		SetValueMotionProfile leftOutput = leftFollower.getSetValue();
		left.set(ControlMode.MotionProfile, leftOutput.value);
		

		SetValueMotionProfile rightOutput = rightFollower.getSetValue();
		right.set(ControlMode.MotionProfile, rightOutput.value);
		
	}
	
	public void reset() {
		leftFollower.reset();
		rightFollower.reset();
	}
	
}
