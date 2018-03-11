package com.team2169.robot;

import jaci.pathfinder.Waypoint;

public class RobotStates {

	public static boolean debugMode = true;

	// FMS Handler
	public static boolean isFMSConnected;

	public static enum FieldSetup {
		LL, LR, RL, RR, FAIL
	}

	public static FieldSetup fieldSetup;

	// Field Positions

	public static enum StartingPosition {

		LEFT, CENTER, RIGHT

	}

	public static StartingPosition startingPosition;
	
	public static enum AutoMode{
		SEGMENTED, CONTINUOUS
	}
	
	public static AutoMode autoMode;

	// RunningMode Handler
	public static enum RunningMode {

		IDLE, AUTO, TELEOP, LIMP

	}

	public static RunningMode runningMode;

	// DriveMode Handler
	public static enum DriveMode {

		LOW, HIGH, SHIFTING, SHIFT_TO_LOW, SHIFT_TO_HIGH

	}

	public static DriveMode driveMode;
	public static boolean ptoActive;

	// DriveOverride Handler
	public static enum DriveType {

		WANTS_TO_HANG, WANTS_TO_DRIVE, OVERRIDE_DRIVING, HANG, NORMAL_DRIVING, FOLLOW_PATH, WANTS_TO_FOLLOW_PATH, WAITING

	}

	public static DriveType driveType;

	public static boolean canCycleMode;

	// Gyro angle
	public static double gyroAngle;

	// Intake Mode Handler
	public static enum IntakeMode {

		INTAKE, EXHAUST, IDLE

	}

	public static IntakeMode intakeMode;

	public static enum IntakeClamp {

		CLAMP, NEUTRAL, DROP
		
	}

	public static IntakeClamp intakeClamp;

	public static boolean intakeClampOverride;
	// Robot block held state
	//public static ArrayList<Double> blockHeldHistory;
	public static enum blockRecentState{
		JUST_ENTERED, JUST_LEFT, NO_CHANGE
	}
	public static boolean ultraWithinRange;
	public static boolean ultraAverage;
	public static boolean operatorWantsUltrasonic;
	public static boolean blockHeld;
	public static blockRecentState blockRecent;

	// Arduino
	public static boolean arduinoConnected;

	// Arm Position Handler
	public static enum ArmPos {

		EXTENDED, RETRACTED, OVERRIDE, HOLD_POSITION, CONFIG

	}

	public static ArmPos armPos;
	public static boolean armStickMode = false;
	public static boolean armButtonMode = false;
	public static boolean armInPosition;

	// Elevator Position Handler
	public static enum Macro {

		HANG, SCALE_HIGH, SCALE_MID, SCALE_LOW, SWITCH, GROUND, OVERRIDE, HOLD_POSITION

	}

	public static Macro elevatorPos;
	public static double elevatorHeight = 0;

	public static double getElevatorHeight() {
		return (elevatorHeight / Constants.maxElevatorHeight);
	}

	public static boolean elevatorInPosition;
	public static boolean elevatorOverrideMode = false;
	public static boolean platformRelease;

	public static enum PathfinderState {

		STOPPED, CALCULATING_PATH, LOOPING, INITIALIZING

	}

	public static PathfinderState pathfinderState;
	public static int leftPathTotalSegments;
	public static int rightPathTotalSegments;
	
	public static double leftPathCompletionPercent;
	public static double rightPathCompletionPercent;
	
	public static Waypoint[] currentPath;
	

}
