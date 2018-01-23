package org.usfirst.frc.team2169.robot;

public class RobotStates {
	
	public static boolean debugMode = true;
	
	//FMS Handler
		public static boolean isFMSConnected;
		public static String fieldSetup;
		
	//RunningMode Handler
		public static enum RunningMode{
			
			IDLE, AUTO, TELEOP, LIMP
			
		}
	
		public static RunningMode runningMode;
		
		public static enum DriveMode{
			
			LOW, HIGH, SHIFTING
			
		}
		
		public static DriveMode driveMode;
	
	//Intake Mode Handler
		public static enum IntakeMode{
			
			INTAKE, EXHAUST, IDLE
			
		}
		
		public static boolean intakeClamp;
		public static IntakeMode intakeMode;
	

	//Arm Position Handler
		public static enum ArmPos{
			
			GROUND, FULLY_RETRACTED, PARTIALLY_RETRACTED
			
		}
		
		public static ArmPos armPos;
	
	//Elevator Position Handler
			public static enum ElevatorPos{
				
				HANG, SCALE_HIGH, SCALE_MID, SCALE_LOW, SWITCH, GROUND
				
			}
			
			public static ElevatorPos elevatorPos;
			public static double elevatorHeight;
			public static double getElevatorHeight() {
				return (elevatorHeight/Constants.maxElevatorHeight);
			}
	
}
