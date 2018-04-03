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

        LOW, HIGH, SHIFT_TO_LOW, SHIFT_TO_HIGH

    }

    public static DriveMode driveMode;
    public static boolean ptoActive;

    // DriveOverride Handler
    public enum DriveType {

        IDLE, WANTS_TO_HANG, WANTS_TO_DRIVE, OVERRIDE_DRIVING, HANG, NORMAL_DRIVING, FOLLOW_PATH, WANTS_TO_FOLLOW_PATH, STOP_PATH, EXTERNAL_DRIVING

    }

    public static DriveType driveType;

    public static boolean canCycleMode;

    // Gyro angle
    public static double gyroAngle;

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

        IDLE, EXTENDED, RETRACTED, OVERRIDE, HOLD_POSITION, CONFIG, STOW

    }

    public static ArmPos armPos;
    public static boolean armStickMode = false;
    static boolean armButtonMode = false;
    static boolean armOverride = false;
    public static boolean armInPosition;

    // Elevator Position Handler
    public enum Macro {

        HANG, SCALE_HIGH, SCALE_MID, SCALE_LOW, SWITCH, GROUND, OVERRIDE, HOLD_POSITION

    }
    
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


}
