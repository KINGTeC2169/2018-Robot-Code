package com.team2169.robot.auto;

public class AutoConstants {

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

	public enum Possesion {
		CLOSE, FAR, CENTER, ERROR
	}

	
	//Left Side Default
	public static class SideAutos {

		public static class Switch {
			public static double startToPoint = 156;
			public static double pointToSwitchTurn = 90;
			public static double pointToSwitch = 18;
			public static double intakeSpeed = .75;
		}
		
		public static class CloseScale {
			public static double startToPoint = 291;
			public static double pointToScaleTurn = 50;
			public static double pointToScale = 20;
			public static double intakeSpeed = .2;
		}
		
		public static class FarScale {
			public static double startToPoint = 235;
			public static double pointToPoint2Turn = 90;
			public static double pointToPoint2 = 217;
			public static double point2ToScaleTurn = -120;
			public static double point2ToScale = 54;
			public static double intakeSpeed = .3;
		}
		
	}

	public static class CenterAutos {

		//Left Switch
		public static class Switch {
			public static double startToPoint = 27;
			public static double pointToSwitchTurn = -55;
			public static double pointToPoint2 = 72;
			public static double pointToSwitch = 30;
			public static double intakeSpeed = .3;
		}

	}

	public static final double driveForwardDistance = 100;
	public static double desireIntakeSpeed;
}
