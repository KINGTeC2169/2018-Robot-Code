package com.team2169.robot;

import jaci.pathfinder.Waypoint;

public class RobotStates {

    public static boolean debugMode = false;

    // FMS Handler
    static boolean isFMSConnected;

    public enum FieldSetup {
        LL, LR, RL, RR, FAIL
    }

    public static FieldSetup fieldSetup;

    // Field Positions

    public enum StartingPosition {

        LEFT, CENTER, RIGHT

    }

    public static StartingPosition startingPosition;

    // RunningMode Handler
    public enum RunningMode {

        IDLE, AUTO, TELEOP

    }

    public static RunningMode runningMode;

    // DriveMode Handler
    public enum DriveMode {

        LOW, HIGH

    }

    public static DriveMode driveMode;
    public static boolean ptoActive;

    // DriveOverride Handler
    public enum DriveType {

        OVERRIDE_DRIVING, NORMAL_DRIVING, EXTERNAL_DRIVING, HANG

    }

    public static DriveType driveType;

    public static boolean canCycleMode;


    // Intake Mode Handler
    public enum IntakeMode {

        INTAKE, EXHAUST, IDLE, MANUAL

    }

    public static IntakeMode intakeMode;

    public enum IntakeClamp {

        CLAMP, OPEN

    }

    public static IntakeClamp intakeClamp;

    static boolean intakeClampOverride;

    // Robot block held state
    //public static ArrayList<Double> blockHeldHistory;
    public enum blockRecentState {
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
    public enum ArmPos {

        IDLE, EXTEND, RETRACT, OVERRIDE, HOLD_POSITION, STOW

    }

    public static ArmPos armPos;
    static boolean armOverride = false;
    public static boolean armInPosition;

    // Elevator Position Handler
    public enum Macro {

        SCALE_HIGH, SCALE_MID, SCALE_LOW, SWITCH, GROUND, OVERRIDE, HOLD_POSITION, PASS

    }
    
	public static enum ElevatorDirection {
		UP, DOWN
	}
	
	public static ElevatorDirection elevatorDirection;
    
    public enum HookState{
    	EXTEND, RETRACT, IDLE
    }

    public static Macro elevatorPos;
    public static double elevatorHeight = 0;

    public static boolean elevatorInPosition;
    public static boolean elevatorOverrideMode = false;
    public static boolean platformRelease;

    public static int leftPathTotalSegments;
    public static int rightPathTotalSegments;

    public static double leftPathCompletionPercent;
    public static double rightPathCompletionPercent;

    public static Waypoint[] currentPath;
    public static boolean reverseCurrentPath;

	public static boolean driveTrainOverride;


}
