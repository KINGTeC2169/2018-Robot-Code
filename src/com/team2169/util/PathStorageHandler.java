package com.team2169.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import com.team2169.robot.subsystems.DriveTrain.PathfinderData;

import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.Segment;
import jaci.pathfinder.modifiers.TankModifier;

public class PathStorageHandler {
	
	public static void storePath(File trajectory, Trajectory traj) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(trajectory);
        StringBuilder sb = new StringBuilder();
        
		for(Segment s: traj.segments) {

	        sb.append(s.position);
	        sb.append(',');
	        sb.append(s.velocity);
	        sb.append(',');		
	        sb.append(s.dt);
	        sb.append('\n');
	        
		}
		
        pw.write(sb.toString());
        pw.close();
        System.out.println("done!");
		
	}
	
	public static MotionProfile pathToProfile(Trajectory traj) {
		
		TankModifier modifier = new TankModifier(traj).modify(PathfinderData.wheel_base_width); 
		Trajectory left = modifier.getLeftTrajectory();
		Trajectory right = modifier.getRightTrajectory();
		
		MotionProfile profile = new MotionProfile();
		int lI = 0;
		int rI = 0;
		
		//Populate Left Path
		for(Segment s: left.segments) {
			
			profile.leftPath.get(lI).set(0, s.position);
			profile.leftPath.get(lI).set(0, s.velocity);
			profile.leftPath.get(lI).set(0, s.dt);
		
			lI++;
		}
		
		//Populate Right Path
		for(Segment s: right.segments) {
			
			profile.rightPath.get(rI).set(0, s.position);
			profile.rightPath.get(rI).set(0, s.velocity);
			profile.rightPath.get(rI).set(0, s.dt);
		
			rI++;
		}
		
		
		
		return null;
		
	}
	
	public static MotionProfile readPath() {
		return null;
		
	}

}
