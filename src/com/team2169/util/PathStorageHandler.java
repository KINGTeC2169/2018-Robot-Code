package com.team2169.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Trajectory.Segment;

public class PathStorageHandler {
	
	public static void storePath(Trajectory traj) throws FileNotFoundException {
		PrintWriter pw = new PrintWriter(new File(".csv"));
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

}
