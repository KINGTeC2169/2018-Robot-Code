package com.team2169.util.motionProfiling;

import java.util.ArrayList;

public class MotionProfilePath {

	public int kNumPoints;
	
	// Position (rotations) Velocity (RPM) Duration (ms)
	
	public ArrayList<MotionProfilePoint> leftPath;
	public ArrayList<MotionProfilePoint> rightPath;
	
	public int getLeftPathLength() {
		return leftPath.size()+1;
	}
	
	public int getRightPathLength() {
		return rightPath.size()+1;
	}
	
	public MotionProfilePath() {
		
		leftPath = new ArrayList<MotionProfilePoint>();
		rightPath = new ArrayList<MotionProfilePoint>();
		
	}
	
	public class MotionProfilePoint{
		double dt = 0;
		double velocity = 0;
		double position = 0;
		
		public MotionProfilePoint(double pos, double vel, double dt_) {
			position = pos;
			velocity = vel;
			dt = dt_;
			
		}
		
		void printPointData() {

			System.out.println("Position = " + position);
			System.out.println("Velocity  = " + velocity);
			System.out.println("DT  = " + dt);
			
		}
	}
	
}
