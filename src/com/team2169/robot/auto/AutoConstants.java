package com.team2169.robot.auto;

public class AutoConstants {

	public static class LeftAutos {

		public static class SwitchAutos {

			public static class LeftSwitch {
				// Switch Ideal Side Autos
				public static double startToPoint = 146;
				public static double pointToSwitchTurn = 90;
				public static double intakeSpeed = .75;
			}
			
		}

		public static class ScaleAutos {

			public static class LeftScale {
				// Scale Ideal Side Autos
				public static double startToPoint = 237;
				public static double pointToScaleTurn = 45;
				public static double pointToScale = 24;
				public static double intakeSpeed = .25;
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
				// Switch Ideal Side Autos
				public static double startToPoint = 7;
				public static double pointToSwitchTurn = -30;
				public static double pointToSwitch = 87;
				public static double intakeSpeed = .75;
			}

			public static class RightSwitch {
				// Switch Bad Side Autos
				public static double startToPoint = 7;
				public static double pointToSwitchTurn = 30;
				public static double pointToSwitch = 87;
				public static double intakeSpeed = .75;
			}
		}

	}

	public static class RightAutos {

		public static class SwitchAutos {

			public static class RightSwitch {
				// Switch Ideal Side Autos
				public static double startToPoint = 146;
				public static double pointToSwitchTurn = -90;
				public static double pointToSwitch = 8;
				public static double intakeSpeed = .75;
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
				public static double startToPoint = 237;
				public static double pointToScaleTurn = -45;
				public static double pointToScale = 24;
				public static double intakeSpeed = .25;
			}

		}
	}

	public static double desireIntakeSpeed;
}
