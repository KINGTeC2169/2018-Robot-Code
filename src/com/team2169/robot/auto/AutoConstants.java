package com.team2169.robot.auto;

public class AutoConstants {

	public enum ElementSide {
		LEFT, RIGHT, ERROR
	}
	
	public static enum Preference{
		SWITCH, SCALE
	}
	
	public static enum Yield{
		NONE, FAR_SCALE, ALL_SCALE
	}

	public enum RobotSide {
		LEFT, RIGHT, CENTER, ERROR
	}
	
	public enum Possesion {
		CLOSE, FAR, CENTER, ERROR
	}
	
	public static class LeftAutos {

		public static class SwitchAutos {

			public static class LeftSwitch {
				// Switch Ideal Side Autos
				public static double startToPoint = 136;
				public static double pointToSwitchTurn = 90;
				public static double pointToSwitch = 18;
				public static double intakeSpeed = .25;
			}
			
		}

		public static class ScaleAutos {

			public static class LeftScale {
				// Scale Ideal Side Autos
				public static double startToPoint = 234;
				public static double pointToScaleTurn = 50;
				public static double pointToScale = 20;
				public static double intakeSpeed = .2;
			}

			public static class RightScale {
				// Scale Bad Side Autos
				public static double startToPoint = 259;
				public static double pointToPoint2Turn = 90;
				public static double pointToPoint2 = 217;
				public static double point2ToScaleTurn = -120;
				public static double point2ToScale = 24;
				public static double intakeSpeed = .25;
			}
		}

	}

	public static class CenterAutos {
		public static class SwitchAutos {

			public static class LeftSwitch {
				public static double startToPoint = 27;
				public static double pointToSwitchTurn = -45;
				public static double pointToSwitch = 67;
				public static double intakeSpeed = .3;
			}

			public static class RightSwitch {
				public static double startToPoint = 27;
				public static double pointToSwitchTurn = 45;
				public static double pointToSwitch = 67;
				public static double intakeSpeed = .3;

			}
		}

	}

	public static class RightAutos {

		public static class SwitchAutos {

			public static class RightSwitch {
				// Switch Ideal Side Autos
				public static double startToPoint = 136;
				public static double pointToSwitchTurn = -90;
				public static double pointToSwitch = 18;
				public static double intakeSpeed = .25;
			}

		}

		public static class ScaleAutos {

			public static class LeftScale {
				// Scale Bad Side Autos
				// Scale Bad Side Autos
				public static double startToPoint = 259;
				public static double pointToPoint2Turn = -90;
				public static double pointToPoint2 = 217;
				public static double point2ToScaleTurn = 120;
				public static double point2ToScale = 24;
				public static double intakeSpeed = .25;
			}

			public static class RightScale {
				// Scale Ideal Side Autos
				public static double startToPoint = 234;
				public static double pointToScaleTurn = -50;
				public static double pointToScale = 20;
				public static double intakeSpeed = .2;
			}

		}
	}

	public static final double driveForwardDistance = 100;

	public static double desireIntakeSpeed;
}
