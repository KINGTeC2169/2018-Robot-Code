
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
		
		//CTRE Mag Encoder Data
		public static final int ticksPerRotation = 4096;
		public static final double kNeutralDeadband = 0;
		public static final int kTimeoutMs = 10;
		public static final int kBaseTrajPeriodMs = 0;
		

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
		public static double driveTrainP = .025;
		
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
		public static int groundElevatorEncoderPosition = 0;
		public static int switchElevatorEncoderPosition = 6000;
		public static int scaleLowElevatorEncoderPosition = 9000;
		public static int scaleMidElevatorEncoderPosition = 17000;
		public static int scaleHighElevatorEncoderPosition = 18000;
		
		//Ramp Rate
		public static final double elevatorRampRate = .75;

		
	//Arm

		public static int extendedArmEncoderPosition = 504;
		public static int retractedArmEncoderPosition = 335;
		public static int stowArmEncoderPosition = 280;
		
		//Arm TalonConfig Data	
		public static TalonConfig armData = new TalonConfig();
		public static void setArmDataFromConstants() {
			armData.slotIDx = 0;
			armData.pidLoopIDx = 0;
			armData.timeoutMs = 10;
			armData.allowedError = 10;
			armData.p = 10;
			armData.i = 0;
			armData.d = 0;
			armData.f = .3;
			armData.zero = false;
			armData.encoderType = FeedbackDevice.Analog;
			armData.sensorPhase = false;

		}

	//Intake
		
		public static final double intakeHoldVoltage = 2;
		public static double intakeSpeed = .5;
		
		
	//Climber
		
		public static final double hookDeploySpeed = .5;
		

}
