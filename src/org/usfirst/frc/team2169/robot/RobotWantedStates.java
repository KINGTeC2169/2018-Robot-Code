package org.usfirst.frc.team2169.robot;

public class RobotWantedStates {

	//FMS Handler
		public static boolean isFMSConnected;
		public static String fieldSetup;
		
	//RunningMode Handler
		public static enum WantedRunningMode{
			
			IDLE, AUTO, TELEOP, LIMP
			
		}
	
		public static WantedRunningMode wantedrunningMode;
	
	//Drive Mode Handler
		public static enum WantedDriveMode{
			
			LOW, HIGH, SHIFT_TO_HIGH, SHIFT_TO_LOW
			
		}
		
		public static WantedDriveMode wantedDriveMode;
	
		
	//Intake Mode Handler
		public static enum WantedIntakeMode{
			
			INTAKE, EXHAUST, IDLE
			
		}
		
		public static boolean intakeClamp;
		public static WantedIntakeMode wantedIntakeMode;
	

	//Arm Position Handler
		public static enum WantedArmPos{
			
			GROUND, FULLY_RETRACTED, PARTIALLY_RETRACTED
			
		}
		
		public static WantedArmPos wantedArmPos;
	
	//Elevator Position Handler
		public static enum WantedElevatorPos{
			
			HANG, SCALE_HIGH, SCALE_MID, SCALE_LOW, SWITCH, GROUND
			
		}
		
		public static WantedElevatorPos wantedElevatorPos;
}
