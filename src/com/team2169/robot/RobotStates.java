package com.team2169.robot;

public class RobotStates {
	
	public static boolean debugMode = true;
	
	//FMS Handler
		public static boolean isFMSConnected;
		
		public static enum FieldSetup {
			LL, LR, RL, RR, FAIL
		}
		
		public static FieldSetup fieldSetup;
		
	//Field Positions
		
		public static enum StartingPosition {
			
			LEFT, CENTER, RIGHT
			
		}
		public static StartingPosition startingPosition;
		
	//RunningMode Handler
		public static enum RunningMode{
			
			IDLE, AUTO, TELEOP, LIMP
			
		}
	
		public static RunningMode runningMode;
		
	//DriveMode Handler
		public static enum DriveMode{
			
			LOW, HIGH, SHIFTING
			
		}
		
		public static DriveMode driveMode;
		public static boolean ptoActive;
	
	//DriveOverride Handler
		public static enum DriveOverride{
			
			WANTS_TO_HANG, WANTS_TO_DRIVE, OVERRIDE, HANG, NONE
			
		}
	
		public static DriveOverride driveOverride;

		public static boolean canCycleMode;
		
	//Intake Mode Handler
		public static enum IntakeMode{
			
			INTAKE, EXHAUST, IDLE
			
		}
		public static IntakeMode intakeMode;
		
		public static enum IntakeClamp{
			
			CLAMP, NEUTRAL, DROP
		}
	    public static IntakeClamp intakeClamp;
	    
	    public static boolean intakeClampOverride;
	//Robot block held state
	    public static boolean ultraWithinRange;  
	    public static boolean operatorWantsUltrasonic;
	    
	//Arm Position Handler
		public static enum ArmPos{
			
			EXTENDED, RETRACTED, OVERRIDE, HOLD_POSITION
			
		}
		public static ArmPos armPos;
		public static boolean armOverrideMode = false;
		public static boolean armInPosition;
		
	//Elevator Position Handler
		public static enum ElevatorPos{
			
			HANG, SCALE_HIGH, SCALE_MID, SCALE_LOW, SWITCH, GROUND, OVERRIDE, HOLD_POSITION
			
		}
		
		public static ElevatorPos elevatorPos;
		public static double elevatorHeight;
		public static double getElevatorHeight() {
			return (elevatorHeight/Constants.maxElevatorHeight);
		}
		public static boolean elevatorInPosition;
		public static boolean elevatorOverrideMode = false;
		public static boolean platformRelease;

		

}
