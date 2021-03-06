
package com.team2169.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.team2169.util.TalonMaker.TalonConfig;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class Constants {

	//Global Constants
	
	 // Auto Names
		public static final String preferenceOneName = "Switch";
		public static final String preferenceTwoName = "Scale";

		// USBCamera Specs

			// Camera1
			public static final String camera1Name = "Intake Cam";
			public static final int camera1ID = 0;
			public static final int camera1FPS = 15;
			public static final int camera1Width = 480;
			public static final int camera1Height = 320;

			// Camera2
			public static final String camera2Name = "Scale Cam";
			public static final int camera2ID = 1;
			public static final int camera2FPS = 0;
			public static final int camera2Width = 489;
			public static final int camera2Height = 320;

			// Camera3
			public static final String camera3Name = "Camera 3 Name";
			public static final int camera3ID = 2;
			public static final int camera3FPS = 0;
			public static final int camera3Width = 640;
			public static final int camera3Height = 480;
		
		//CTRE Mag Encoder Data
		public static final int ticksPerRotation = 4096;
		public static final double kNeutralDeadband = 0;
		public static final int kTimeoutMs = 10;
		public static final int kBaseTrajPeriodMs = 0;
		public static final double minimumEncoderVoltage = 0;
		

	//Drive Train

		public static TalonConfig leftDriveData = new TalonConfig();
		public static TalonConfig rightDriveData = new TalonConfig();

		//Measurements
		public static final double wheelDiameter = 6;

		// Drivetrain Shifting States
		public static final Value highGear = Value.kForward;
		public static final Value lowGear = Value.kReverse;
		
		// Current Limits
		public static final double driveTrainRampRate = .25;
		public static final int maxDriveTrainCurrent = 40;
		public static final int driveTrainCurrentTimeout = 5;
		
		//Turn-In-Place data
		public static double turnMaxError = 30;
		public static double turnMinError = 10;
		public static double turnZeroError = 2;
		public static double turnMaxSpeed = .4;
		public static double turnMinSpeed = .075;
		public static double TurnTestAngle = 90;
		
		//Turn-In-Place PID
		public static final double driveTrainTurnAllowedError = 2;
		public static final double driveTrainAngleCorrectionP = .025;
		public static final double driveTurnP = 0.00775;
		public static final double driveTurnI = 0.00000025;
		public static final double driveTurnD = 250;
		public static final double driveTurnF = 0.0000001;
		
		//Drive Straight data
		public static final double driveStraightP = .00014;
		
		//Autonomous Ramp Time (seconds)
		public static double autoRampTime = .75;
		
		
	//Elevator

		//ElevatorData TalonConfig
		public static TalonConfig elevatorUpData = new TalonConfig();
		public static TalonConfig elevatorDownData = new TalonConfig();
		public static void setElevatorDataFromConstants() {
			elevatorUpData.slotIDx = 0;
			elevatorUpData.pidLoopIDx = 0;
			elevatorUpData.timeoutMs = 10;
			elevatorUpData.allowedError = 10;
			elevatorUpData.zero = false;
			elevatorUpData.encoderType = FeedbackDevice.CTRE_MagEncoder_Relative;
			elevatorUpData.sensorPhase = false;
			
			elevatorUpData.p = .15;
			elevatorUpData.i = 0;
			elevatorUpData.d = .25;
			elevatorUpData.f = .005;
			
			elevatorDownData.p = .35;
			elevatorDownData.i = 0;
			elevatorDownData.d = .15;
			elevatorDownData.f = .015;

		}

		
		//Elevator Encoder Heights
		public static int groundElevatorEncoderPosition = -300;
		public static int switchElevatorEncoderPosition = 7750;
		public static int scaleLowElevatorEncoderPosition = 9000;
		public static int scaleMidElevatorEncoderPosition = 14000;
		public static int scaleHighElevatorEncoderPosition = 19500;
		
		//Ramp Rate
		public static final double elevatorRampRate = .3;

		
	//Arm

		public static int extendedArmEncoderPosition = 3934 - 53;
		public static int retractedArmEncoderPosition = extendedArmEncoderPosition - 600;
		public static int stowArmEncoderPosition = 215;
		
		public static final double armLowerBound = -1250;
		public static final double armUpperBound = 0;
		
		
		//Arm TalonConfig Data	
		public static TalonConfig armData = new TalonConfig();
		public static void setArmDataFromConstants() {
			armData.slotIDx = 0;
			armData.pidLoopIDx = 0;
			armData.timeoutMs = 10;
			armData.allowedError = 10;
			armData.p = 0.01;
			armData.i = 0;
			armData.d = 0;
			armData.f = 0;
			armData.zero = false;
			armData.encoderType = FeedbackDevice.None;
			armData.sensorPhase = false;

		}

	//Intake
		
		public static final double intakeHoldVoltage = 3;
		public static final double intakeReleaseVoltage = 3;
		public static double intakeSpeed = .5;
		
		
	//Climber
		
		public static final double hookDeploySpeed = .5;

		
		

}
