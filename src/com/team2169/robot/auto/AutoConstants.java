package com.team2169.robot.auto;

public class AutoConstants {

	public enum AutoSequenceMode{
		NORMAL, DRIVE_FORWARD, NOTHING, SELF_TEST
	}
	
	public enum ElementSide {
		LEFT, RIGHT, ERROR
	}

	public static enum Preference {
		SWITCH, SCALE
	}

	public static enum Yield {
		NONE, FAR_SCALE, ALL_SCALE
	}

	public enum RobotSide {
		LEFT, RIGHT, CENTER, ERROR
	}
	
	public enum CenterPriority {
		ONE_BLOCK, TWO_BLOCK
	}
	
	public enum SidePriority {
		ONE_SCALE, TWO_SCALE, SWITCH_SCALE_ONE_BLOCK, SWITCH_SCALE_TWO_BLOCK
	}
	
	public enum Possesion {
		CLOSE, FAR, CENTER, ERROR
	}

	
	// Left Side Default
	public static class SideAutos {

		public static class OneBlockAutos {
			
			public static class CloseSwitch {
				public static double startToPoint = 156;
				public static double pointToSwitchTurn = 90;
				public static double pointToSwitch = 18;
				public static double intakeSpeed = .75;				
			}
			
			public static class FarSwitch{
				public static double startToPoint = 210;
				public static double pointToPoint2Turn = 90;
				public static double pointToPoint2 = 236;
				public static double point2ToSwitchTurn = 120;
				public static double point2ToSwitch= 36;
				public static double intakeSpeed = .3;
				public static double intakeInSpeed = .5;
			}

			public static class CloseScale {
				public static double startToPoint = 256;
				public static double pointToScaleTurn = 50;
				public static double pointToScale = 24;
				public static double intakeSpeed = .5;
				public static double intakeInSpeed = .5;
				
				public static class ScaleSwitchClose {
					public static double blockToSwitch = 6;
				}
			}

			public static class FarScale {
				public static double startToPoint = 210;
				public static double pointToPoint2Turn = 90;
				public static double pointToPoint2 = 236;
				public static double point2ToScaleTurn = -105;
				public static double point2ToScale = 65;
				public static double intakeSpeed = .4;
				public static double intakeInSpeed = .5;
			}
		
		}

		public static class TwoBlockAutos {

			public static class Close {
				public static double startToPoint = 256;
				public static double pointToScaleTurn = 50;
				public static double pointToScale = 17;
				public static double pointToBlockTurn = 100;
				public static double pointToBlock = 64;
				public static double intakeSpeed = .7;
				public static double intakeInSpeed = .5;
				
				public static class ScaleSwitchClose {
					public static double blockToSwitch = 6;
				}
			}

			public static class Far {
				public static double startToPoint = 210;
				public static double pointToPoint2Turn = 90;
				public static double pointToPoint2 = 236;
				public static double point2ToScaleTurn = -95;
				public static double point2ToScale = 65;
				public static double point2ToPoint3 = -18;
				public static double point3ToBlockTurn = -120;
				public static double point3ToBlock = 48;
				public static double intakeSpeed = .3;
				public static double intakeInSpeed = .5;

				public static class ScaleSwitchFar {
					public static double BlockToSwitch = 6;
				}
			}
		}
	}

	public static class CenterAutos {

		// Left Switch
		public static class OneBlock {
			public static double intakeSpeed = .5;
			public static double intakeInSpeed = .5;

			public static class Right{
				public static double startToPoint = 27;
				public static double pointToSwitchTurn = -55;
				public static double pointToPoint2 = 50;
				public static double pointToSwitch = 35;
			}
			public static class Left{
				public static double startToPoint = 27;
				public static double pointToSwitchTurn = 55;
				public static double pointToPoint2 = 70;
				public static double pointToSwitch = 30;
			}
		}
		
		public static class TwoBlock {
			
			public static double switchToPoint = -70;
			public static double pointToPileTurn = 45;
			public static double pointToPile = 52;
		}

	}

	public static final double driveForwardDistance = 100;
	
}