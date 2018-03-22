package com.team2169.util;

import java.util.ArrayList;

public class MotionProfile {

	public int kNumPoints;
	
	// Position (rotations) Velocity (RPM) Duration (ms)
	
	public ArrayList<ArrayList<Double>> leftPath;
	public ArrayList<ArrayList<Double>> rightPath;
	
	public int getLeftPathLength() {
		return leftPath.size()+1;
	}
	
	public int getRightPathLength() {
		return rightPath.size()+1;
	}
	
	public MotionProfile() {
		
		leftPath = new ArrayList<ArrayList<Double>>();
		rightPath = new ArrayList<ArrayList<Double>>();
		
	}
	
}
