package com.team2169.robot.auto;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Waypoint;

public class Paths {
	public static Waypoint[] example = new Waypoint[] {
			new Waypoint(10, -10, 0), 
			new Waypoint(0, 0, 0)
	};

		//Brady
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
		
		//Tom
		public static class LRLPaths {
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
					new Waypoint(83, 300, Pathfinder.d2r(0)),// Final Point
			};
			
			public static Waypoint[] turnAndPlace = new Waypoint[] {
					new Waypoint(83, 300, Pathfinder.d2r(0)), // Starting Point
					new Waypoint(83, 300, Pathfinder.d2r(0)), // Final Point
			};
		}
		
		//Tom
		public static class LLRPaths {
			public static Waypoint[] startToSwitch = new Waypoint[] {
					new Waypoint(49, 20.5, 0),
					new Waypoint(49, 130, 0),
					new Waypoint(70.83, 169.44, Pathfinder.d2r(90))
			};
			
			public static Waypoint[] switchToBlock = new Waypoint[] {
					new Waypoint(70.83, 169.44, Pathfinder.d2r(90)),
					new Waypoint(55.34, 230.35, Pathfinder.d2r(153.5)), 
					new Waypoint(77.27, 214.7, Pathfinder.d2r(135.5)) 
			};
			
			public static Waypoint[] blockToScale = new Waypoint[] {
					new Waypoint(77.27, 214.7, Pathfinder.d2r(135.5)),
					new Waypoint(55.34, 230.35, Pathfinder.d2r(90)),
					new Waypoint(215, 230.35, Pathfinder.d2r(90)),
					new Waypoint(244, 300, Pathfinder.d2r(0)),
			};
		}
		public static class LRRPaths {
			public static Waypoint[] startToScale = new Waypoint[] {
					new Waypoint(49, 20.5, 0),
					new Waypoint(49, 190, Pathfinder.d2r(90)),
					new Waypoint(103.4, 230.35, Pathfinder.d2r(90)),
					new Waypoint(215, 230.35, Pathfinder.d2r(90)),
					new Waypoint(244, 300, Pathfinder.d2r(0)),
			};
			
			public static Waypoint[] scaleToBlock = new Waypoint[] {
					new Waypoint(244, 300, Pathfinder.d2r(0)),
					new Waypoint(244, 300, Pathfinder.d2r(180)), 
					new Waypoint(235, 223.56, Pathfinder.d2r(180)) 
			};
			
			public static Waypoint[] blockToSwitch = new Waypoint[] {
					new Waypoint(77.27, 214.7, Pathfinder.d2r(135.5)),
					new Waypoint(55.34, 230.35, Pathfinder.d2r(90)),
					new Waypoint(215, 230.35, Pathfinder.d2r(90)),
					new Waypoint(244, 300, Pathfinder.d2r(0)),
			};		
			
		}
		
		public static class JeffPaths {
			public static Waypoint[] startToScale = new Waypoint[] {
					new Waypoint(49, 20.5, 0),
					new Waypoint(49, 190, Pathfinder.d2r(90)),
					new Waypoint(103.4, 230.35, Pathfinder.d2r(90)),
					new Waypoint(215, 230.35, Pathfinder.d2r(90)),
					new Waypoint(244, 300, Pathfinder.d2r(0)),
			};
			
			public static Waypoint[] scaleToBlock = new Waypoint[] {
					new Waypoint(244, 300, Pathfinder.d2r(0)),
					new Waypoint(215, 230.35, Pathfinder.d2r(90)), 
					new Waypoint(93.83, 230.35, Pathfinder.d2r(90)),
					new Waypoint(93.83, 230.35, Pathfinder.d2r(180)),
					new Waypoint(93.83, 223.56, Pathfinder.d2r(180)), 
			};
			
			public static Waypoint[] blockToSwitch = new Waypoint[] {
					new Waypoint(93.83, 223.56, Pathfinder.d2r(180)), 
					new Waypoint(93.83, 213.56, Pathfinder.d2r(180)),
			};		
			
		}
}

