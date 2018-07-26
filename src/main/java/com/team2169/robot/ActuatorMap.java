package com.team2169.robot;

public class ActuatorMap {

    // Put Actuator Ports here

    // TalonSRX Ports
    // DriveTrain TalonSRX Ports
    public static final int leftMasterDriveTalon = 0;
    public static final int leftTop = 1;
    public static final int leftFront = 2;
    public static final int rightFront = 3;
    public static final int rightTop = 4;
    public static final int rightMasterDriveTalon = 5;

    // Elevator TalonSRX IDs
    public static final int elevatorMasterID = 7;
    public static final int elevatorSlaveID = 6;
    public static final int armID = 10;

    // Intake TalonSRX IDs
    public static final int leftIntakeID = 8;
    public static final int rightIntakeID = 9;
    
	public static final int hookDeplyID = 11;

    // Pnuematics
    // Compressor
    public static final int PCMPort = 12;

    // Intake Piston
    public static final int clampPortForward = 0;
    public static final int clampPortReverse = 1;

    // Drive Train Shifting
    public static final int dtSpeedShiftForward = 3;
    public static final int dtSpeedShiftReverse = 2;

    // PTO Ports
    public static final int ptoShiftForward = 4;
    public static final int ptoShiftReverse = 5;

    public static final int elevatorBottomLimitID = 2;
    public static final int elevatorTopLimitID = 3;
	public static final int armEncoderPort = 2;

}
