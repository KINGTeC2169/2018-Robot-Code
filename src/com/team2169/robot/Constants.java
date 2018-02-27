package com.team2169.robot;

import com.team2169.util.Converter;
import com.team2169.util.TalonMaker.TalonConfig;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Constants {

	// Auto Names
	public static final String defaultAutoName = "Default Auto";
	public static final String secondAutoName = "Auto 2";
	public static final String thirdAutoName = "Auto 3";

	// USBCamera Specs

	// Camera1
	public static final String camera1Name = "Test Camera";
	public static final int camera1ID = 0;
	public static final int camera1FPS = 8;
	public static final int camera1Width = 640;
	public static final int camera1Height = 480;

	// Camera2
	public static final String camera2Name = "Camera 2 Name";
	public static final int camera2ID = 1;
	public static final int camera2FPS = 0;
	public static final int camera2Width = 640;
	public static final int camera2Height = 480;

	// Camera3
	public static final String camera3Name = "Camera 1 Name";
	public static final int camera3ID = 2;
	public static final int camera3FPS = 0;
	public static final int camera3Width = 640;
	public static final int camera3Height = 480;

	// intake constants
	public static final double minUltraTriggerDistance = 0;
	public static final double maxUltraTriggerDistance = 8;
	public static final double intakeSpeed = 0.75;

	// Climb Speed
	public static final double climbPower = .75;
	
	//Arduino
	public static final int arduinoMainSignal = 32; // 32 for kingTecRandom		64 for Rainbow		96 for stripes
	public static final int arduinoBlockHeldSignal = 128;
	public static final int arduinoBlockPlacedSignal = 160;
	public static final int arduinoReadyToClimbSignal = 192;
	
	
	// Take wanted value of intake wheels and subtract 8.
	// Happens to work perfectly to set it to the height of the object you want to
	// reach.
	public static double groundElevatorEncoderInches = 0;
	public static double switchElevatorEncoderInches = 18.75;
	public static double scaleLowElevatorEncoderInches = 48;
	public static double scaleMidElevatorEncoderInches = 60;
	public static double scaleHighElevatorEncoderInches = 72;
	public static double hangElevatorEncoderInches = 65;

	public static int extendedArmEncoderPosition = 0;
	public static int retractedArmEncoderPosition = 750;

	public static TalonConfig elevatorData = new TalonConfig();

	public static void setElevatorDataFromConstants() {
		elevatorData.slotIDx = 0;
		elevatorData.pidLoopIDx = 0;
		elevatorData.timeoutMs = 10;
		elevatorData.allowedError = 2;
		elevatorData.p = .75;
		elevatorData.i = 0;
		elevatorData.d = .5;
		elevatorData.f = .2;
		elevatorData.sensorPhase = false;

	}

	public static final double elevatorDrumReduction = 17.53;

	public static final double elevatorWinchDiameter = 2.5;

	public static TalonConfig armData = new TalonConfig();

	public static void setArmDataFromConstants() {
		armData.slotIDx = 0;
		armData.pidLoopIDx = 0;
		armData.timeoutMs = 10;
		armData.allowedError = 2;
		armData.p = .56;
		armData.i = 0;
		armData.d = .15;
		armData.f = .2;
		elevatorData.sensorPhase = false;

	}

	public static TalonConfig leftDriveData = new TalonConfig();
	public static TalonConfig rightDriveData = new TalonConfig();

	public static void setDriveMotorDataFromConstants() {
		leftDriveData.slotIDx = 0;
		leftDriveData.pidLoopIDx = 0;
		leftDriveData.timeoutMs = 10;
		rightDriveData.slotIDx = 0;
		rightDriveData.pidLoopIDx = 0;
		rightDriveData.timeoutMs = 10;
	}

	// Default Values (in case something fails)
	public static int groundElevatorEncoderPosition = 0;
	public static int switchElevatorEncoderPosition = 9778;
	public static int scaleLowElevatorEncoderPosition = 25033;
	public static int scaleMidElevatorEncoderPosition = 31291;
	public static int scaleHighElevatorEncoderPosition = 37549;
	public static int hangElevatorEncoderPosition = 37549;

	public static void calculateMacros() {
		groundElevatorEncoderPosition = 0;
		switchElevatorEncoderPosition = Converter.winchInchesToTicks(switchElevatorEncoderInches,
				elevatorWinchDiameter);
		scaleLowElevatorEncoderPosition = Converter.winchInchesToTicks(scaleLowElevatorEncoderInches,
				elevatorWinchDiameter);
		scaleMidElevatorEncoderPosition = Converter.winchInchesToTicks(scaleMidElevatorEncoderInches,
				elevatorWinchDiameter);
		scaleHighElevatorEncoderPosition = Converter.winchInchesToTicks(scaleHighElevatorEncoderInches,
				elevatorWinchDiameter);
		hangElevatorEncoderPosition = Converter.winchInchesToTicks(hangElevatorEncoderInches, elevatorWinchDiameter);
	}

	// Robot Contants go here
	public static final double wheelBaseWidth = 26;
	public static final double wheelDiameter = 6;
	public static final int ticksPerRotation = 4096;
	public static final double maxElevatorHeight = 37549;

	// SolenoidStates
	public static final DoubleSolenoid.Value highGear = DoubleSolenoid.Value.kForward;
	public static final DoubleSolenoid.Value lowGear = DoubleSolenoid.Value.kReverse;

	// Current Limits
	public static final int maxDriveTrainCurrent = 40;

	// Current Limit Timeouts
	public static final int driveTrainCurrentTimeout = 5;
	
	public static final int elevatorUpperLimit = 0;
	public static final int elevatorLowerLimit = 0;
	
	// Pathfinder Zone

	// Pathfinder Numbers to care about

	// Set Maximum Velocity in Units/Second
	public static double maxVelocity = 0.5;
	// Modify if robot isn't following path nicely
	public static double pathfinderD = 0;
	// How quickly/slowly do you want to reach full speed
	public static double accelerationGain = 0;

	// Other Pathfinder PID Configuration
	public static double pathfinderP = 0.001;
	public static double pathfinderI = 0;

	// Path Generation Only
	public static double timeStep = 0.15; // Seconds
	public static double maxAcceleration = 0.05; // m/s/s
	public static double maxJerk = 0.05; // m/s/s/s

}
