package com.team2169.util.motionProfiling;

import java.util.ArrayList;

public class MotionProfilePath {

	public int kNumthiss;
	
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
	
	public void printProfile() {
		
		for(MotionProfilePoint p: leftPath) {
			System.out.println("  Position: " + p.position + "  Velocity: " + p.velocity + "  DT: " + p.timeDur);
		}
		
		for(MotionProfilePoint p: rightPath) {
			System.out.println("  Position: " + p.position + "  Velocity: " + p.velocity + "  DT: " + p.timeDur);
		}
		
	}
	
}
