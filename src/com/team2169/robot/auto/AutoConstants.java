package com.team2169.robot.auto;

public class AutoConstants {

	public static class LeftAutos {

		public static class SwitchAutos {

			public static class LeftSwitch {
				// Switch Ideal Side Autos
				public static double startToPoint = 146;
				public static double pointToSwitchTurn = 90;
			}
			
		}

		public static class ScaleAutos {

			public static class LeftScale {
				// Scale Ideal Side Autos
				public static double startToPoint = 325;
				public static double pointToScaleTurn = 90;
			}

			public static class RightScale {
				// Scale Bad Side Autos
				public static double startToPoint = 259;
				public static double pointToPoint2Turn = 90;
				public static double pointToPoint2 = 193;
				public static double point2ToScaleTurn = -90;
				public static double point2ToScale = 37;
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
			}

			public static class RightSwitch {
				// Switch Bad Side Autos
				public static double startToPoint = 7;
				public static double pointToSwitchTurn = 30;
				public static double pointToSwitch = 87;
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
			}

		}

		public static class ScaleAutos {

			public static class LeftScale {
				// Scale Bad Side Autos
				public static double startToPoint = 259;
				public static double pointToPoint2Turn = -90;
				public static double pointToPoint2 = 193;
				public static double point2ToScaleTurn = 90;
				public static double point2ToScale = 37;
			}

			public static class RightScale {
				// Scale Ideal Side Autos
				public static double startToPoint = 325;
				public static double pointToScaleTurn = -90;
			}

		}
	}
}
