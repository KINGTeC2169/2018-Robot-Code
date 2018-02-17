package com.team2169.robot.auto;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class Paths {
	public static Waypoint[] example = new Waypoint[] {
			new Waypoint(400, 100, Pathfinder.d2r(-45)), // Waypoint @ x=-4,
			new Waypoint(200, 200, 0), // Waypoint @ x=-2, y=-2, exit angle=0 radians
			new Waypoint(0, 0, 0) // Waypoint @ x=0, y=0, exit angle=0 radians
	};

	public static class LLLPaths {
		public static Waypoint[] startToScale = new Waypoint[] {
				new Waypoint(49, 20.5, 0), // Starting Point
				new Waypoint(49, 190, 0),
				new Waypoint(83, 300, 0)
		};
		
		public static Waypoint[] scaleToBlock = new Waypoint[] {
				new Waypoint(83, 300, 0),  // Starting Point 
				new Waypoint(83, 300, Pathfinder.d2r(180)), 
				new Waypoint(93.83, 223.56, Pathfinder.d2r(180)) 
		};
		
		public static Waypoint[] blockToSwitch = new Waypoint[] {
				new Waypoint(93.83, 223.56, Pathfinder.d2r(180)), // Starting Point
				new Waypoint(93.83, 213.56, Pathfinder.d2r(180)), // Final Point
		};
	}
}
