package com.team2169.robot;

import com.team2169.util.TalonMaker.TalonConfig;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Constants {
	
	//Auto Names
	public static final String defaultAutoName = "Default Auto";
	public static final String secondAutoName = "Auto 2";
	public static final String thirdAutoName  = "Auto 3";
	
	//USBCamera Specs
		
		//Camera1

		public static final String camera1Name = "Test Camera";
		public static final int camera1ID = 0;
		public static final int camera1FPS = 8;
		public static final int camera1Width = 640;
		public static final int camera1Height = 480;
	
		//Camera2
		public static final String camera2Name = "Camera 2 Name";
		public static final int camera2ID = 1;
		public static final int camera2FPS = 0;
		public static final int camera2Width = 640;
		public static final int camera2Height = 480;
	
		//Camera3
		public static final String camera3Name = "Camera 1 Name";
		public static final int camera3ID = 2;
		public static final int camera3FPS = 0;
		public static final int camera3Width = 640;
		public static final int camera3Height = 480;	
		
	//Climb Speed
	public static final double climbPower = .75;
	
	public static TalonConfig elevatorData = new TalonConfig();
	public static void setElevatorDataFromConstants() {
		elevatorData.slotIDx = 0;
		elevatorData.pidLoopIDx = 0;
		elevatorData.timeoutMs = 10;
		elevatorData.allowedError = 2;
		elevatorData.p = .2;
		elevatorData.i = 0;
		elevatorData.d = 0;
		elevatorData.f = .2;
		
	}
	
	public static TalonConfig armData = new TalonConfig();
	public static void setArmDataFromConstants() {
		armData.slotIDx = 0;
		armData.pidLoopIDx = 0;
		armData.timeoutMs = 10;
		armData.allowedError = 2;
		armData.p = .2;
		armData.i = 0;
		armData.d = 0;
		armData.f = .2;
		
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
	
	
	//Macro Encoder Positions go here
	public static final double groundElevatorEncoderPosition = 0;
	public static final double switchElevatorEncoderPosition = 50;
	public static final double scaleLowElevatorEncoderPosition = 100;
	public static final double scaleMidElevatorEncoderPosition = 150;
	public static final double scaleHighElevatorEncoderPosition = 200;
	public static final double hangElevatorEncoderPosition = 175;
		
	//Robot Contants go here
	public static final double wheelBaseWidth = 25;
	public static final double wheelDiameter = 6;
	public static final int ticksPerRotation = 4096;
	public static final double maxElevatorHeight = 1250;

	//SolenoidStates
	public static final DoubleSolenoid.Value highGear = DoubleSolenoid.Value.kForward;
	public static final DoubleSolenoid.Value lowGear = DoubleSolenoid.Value.kReverse;
	
	//Current Limits
	public static final int maxDriveTrainCurrent = 30;
	
	//Current Limit Timeouts
	public static final int driveTrainCurrentTimeout = 50;
	
	//Pathfinder Zone
		
		//Pathfinder Numbers to care about
			
			//Set Maximum Velocity in Units/Second
			public static final double maxVelocity = 1.7; 
			//Modify if robot isn't following path nicely
			public static final double pathfinderD = 0;
			//How quickly/slowly do you want to reach full speed
			public static final double accelerationGain = 0.05;
	
		//Other Pathfinder PID Configuration
		public static final double pathfinderP = 1;
		public static final double pathfinderI = 0;
		
		//Path Generation Only
		public static final double timeStep= 0.05; //Seconds
		public static final double maxAcceleration = 2.0;  //m/s/s
		public static final double maxJerk = 60; //m/s/s/s
		
	
}
