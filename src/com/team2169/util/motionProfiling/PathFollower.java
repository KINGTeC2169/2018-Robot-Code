package com.team2169.util.motionProfiling;

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
	
	public void loop() {
		leftFollower.control();
		rightFollower.control();
	}
	
	public void reset() {
		leftFollower.reset();
		rightFollower.reset();
	}
	
}
