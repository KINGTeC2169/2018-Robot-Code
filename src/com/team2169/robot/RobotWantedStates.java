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
		public static WantedIntakeMode wantedIntakeMode;
		
		public static enum WantedIntakeClamp{
			
			CLAMP, NEUTRAL, DROP
			
		}
		public static WantedIntakeClamp wantedIntakeClamp;
	

	//Arm Position Handler
		public static enum WantedArmPos{
			
			EXTENDED, RETRACTED, OVERRIDE
			
		}
		
		public static WantedArmPos wantedArmPos;
	
	//Elevator Position Handler
		public static enum WantedMacro{
			
			HANG, SCALE_HIGH, SCALE_MID, SCALE_LOW, SWITCH, GROUND, OVERRIDE
			
		}
		
		public static WantedMacro wantedElevatorPos;
		public static WantedMacro wantedMacro;
		
		
}
