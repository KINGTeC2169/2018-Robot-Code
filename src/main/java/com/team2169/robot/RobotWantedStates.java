package com.team2169.robot;

import com.team2169.robot.RobotStates.*;

@SuppressWarnings("unused")
public class RobotWantedStates {

    // FMS Handler
    public static boolean isFMSConnected;
    public static String fieldSetup;

    public static RunningMode wantedRunningMode;

    // Drive Mode Handler
    public static DriveMode wantedDriveMode;
    public static boolean wantedPTOActive;

    // DriveType Handler
    public static DriveType wantedDriveType;
    public static boolean platformRelease;

    // Intake Mode Handler
    public static IntakeMode wantedIntakeMode;
    public static IntakeClamp wantedIntakeClamp;

    // Arm Position Handler
    public static ArmPos wantedArmPos;

    // Elevator Position Handler
    public static Macro wantedElevatorPos;
    static Macro wantedMacro;
	public static boolean climberRelease;
	public static HookState hookRelease;

}
