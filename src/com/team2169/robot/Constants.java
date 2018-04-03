package com.team2169.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.team2169.util.TalonMaker.TalonConfig;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Constants {

    // Auto Names
    public static final String defaultAutoName = "Switch";
    public static final String secondAutoName = "Scale";

    // USBCamera Specs

    // Camera1
    public static final String camera1Name = "Intake Cam";
    public static final int camera1ID = 0;
    public static final int camera1FPS = 15;
    public static final int camera1Width = 480;
    public static final int camera1Height = 320;

    // Camera2
    public static final String camera2Name = "Camera 2 Name";
    public static final int camera2ID = 1;
    public static final int camera2FPS = 0;
    public static final int camera2Width = 640;
    public static final int camera2Height = 480;

    // Camera3
    public static final String camera3Name = "Camera 3 Name";
    public static final int camera3ID = 2;
    public static final int camera3FPS = 0;
    public static final int camera3Width = 640;
    public static final int camera3Height = 480;

    // intake constants
    public static final double minUltraTriggerDistance = 0;
    public static final double maxUltraTriggerDistance = 8;
    public static final double intakeSpeed = 1;

    // Climb Speed
    public static final double climbPower = .75;

    //ultrasonic
    public static final double WeightedAverageRequirement = 150;

    // Arduino
    public static final int arduinoMainSignal = 32; // 32 for kingTecRandom 64 for Rainbow 96 for stripes
    public static final int arduinoBlockHeldSignal = 128;

    // Take wanted value of intake wheels and subtract 8.
    // Happens to work perfectly to set it to the height of the object you want to
    // reach.

    public static int extendedArmEncoderPosition = 2500;
    public static int retractedArmEncoderPosition = 1000;
    public static int stowArmEncoderPosition = 0;

    public static TalonConfig elevatorData = new TalonConfig();
    public static TalonConfig armData = new TalonConfig();

    public static void setArmDataFromConstants() {
        armData.slotIDx = 0;
        armData.pidLoopIDx = 0;
        armData.timeoutMs = 10;
        armData.allowedError = 2;
        armData.p = 1;
        armData.i = 0;
        armData.d = 0;
        armData.f = .1;
        armData.zero = false;
        armData.encoderType = FeedbackDevice.PulseWidthEncodedPosition;
        elevatorData.sensorPhase = false;

    }

    public static TalonConfig leftDriveData = new TalonConfig();
    public static TalonConfig rightDriveData = new TalonConfig();


    // Default Values (in case something fails)
    public static int groundElevatorEncoderPosition = 0;
    public static int switchElevatorEncoderPosition = 4000;
    public static int scaleLowElevatorEncoderPosition = 9000;
    public static int scaleMidElevatorEncoderPosition = 17000;
    public static int scaleHighElevatorEncoderPosition = 19500;
    public static int hangElevatorEncoderPosition = 37549 / 2;
    public static double driveTrainP = .015;

    // Robot Contants go here
    public static final double wheelDiameter = 6;
    public static final int ticksPerRotation = 4096;

    // SolenoidStates
    public static final DoubleSolenoid.Value highGear = DoubleSolenoid.Value.kForward;
    public static final DoubleSolenoid.Value lowGear = DoubleSolenoid.Value.kReverse;

    // Current Limits
    public static final int maxDriveTrainCurrent = 40;

    // Current Limit Timeouts
    public static final int driveTrainCurrentTimeout = 5;

    public static final double driveTrainRampRate = .25;
	public static final double kNeutralDeadband = 0;
	public static final int kTimeoutMs = 10;
	public static final int kBaseTrajPeriodMs = 0;
	
	public static final double hookReleasePosition = -90000;

}
