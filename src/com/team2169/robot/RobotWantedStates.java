package com.team2169.robot;

public class RobotWantedStates {

	//FMS Handler
		public static boolean isFMSConnected;
		public static String fieldSetup;
		
	//RunningMode Handler
		public static enum WantedRunningMode{
			
			IDLE, AUTO, TELEOP, LIMP
			
		}
	
		public static WantedRunningMode wantedRunningMode;
	
	//Drive Mode Handler
		public static enum WantedDriveMode{
			
			LOW, HIGH, SHIFT_TO_HIGH, SHIFT_TO_LOW
			
		}
		
		public static WantedDriveMode wantedDriveMode;
		public static boolean wantedPTOActive;
	
	//DriveOverride Handler
		public static enum WantedDriveOverride{
			
			WANTS_TO_HANG, WANTS_TO_DRIVE, OVERRIDE, HANG, NONE
			
		}
	
		public static WantedDriveOverride wantedDriveOverride;
		public static boolean platformRelease;
		
	//Intake Mode Handler
		public static enum WantedIntakeMode{
			
			INTAKE, EXHAUST, IDLE
			
		}
		
		public static boolean intakeClamp;
		public static WantedIntakeMode wantedIntakeMode;
	

	//Arm Position Handler
		public static enum WantedArmPos{
			
			EXTENDED, FULLY_RETRACTED, PARTIALLY_RETRACTED
			
		}
		
		public static WantedArmPos wantedArmPos;
	
	//Elevator Position Handler
		public static enum WantedElevatorPos{
			
			HANG, SCALE_HIGH, SCALE_MID, SCALE_LOW, SWITCH, GROUND
			
		}
		
		public static WantedElevatorPos wantedElevatorPos;
		
}