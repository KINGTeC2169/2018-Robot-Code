package org.usfirst.frc.team2169.robot;

public class RobotStates {
	
	
	//FMS Handler
	
		public static boolean debugMode = true;
		
		public static boolean isFMSConnected;
		public static String fieldSetup;
		
	//RunningMode Handler
		public static enum runningMode{
			
			IDLE, AUTO, TELEOP, LIMP
			
		}
	
		public static runningMode runningMode;
		
		public static enum driveMode{
			
			LOW, HIGH, SHIFTING
			
		}
		
		public static driveMode driveMode;
	
	//Intake Mode Handler
		public static enum intakeMode{
			
			INTAKE, EXHAUST, IDLE
			
		}
		
		public static boolean intakeClamp;
		public static intakeMode intakeMode;
	

	//Arm Position Handler
		public static enum armPos{
			
			GROUND, FULLY_RETRACTED, PARTIALLY_RETRACTED
			
		}
		
		public static armPos armPos;
	
	//Elevator Position Handler
			public static enum elevatorPos{
				
				HANG, SCALE_HIGH, SCALE_MID, SCALE_LOW, SWITCH, GROUND
				
			}
			
			public static elevatorPos elevatorPos;
			public static double elevatorHeight;
	
}
