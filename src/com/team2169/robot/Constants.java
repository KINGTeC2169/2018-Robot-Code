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
	
	public static TalonConfig liftData = new TalonConfig();
	public static void setLiftDataFromConstants() {
		liftData.slotIDx = 0;
		liftData.pidLoopIDx = 0;
		liftData.timeoutMs = 10;
		liftData.allowedError = 2;
		liftData.p = .2;
		liftData.i = 0;
		liftData.d = 0;
		liftData.f = .2;
		
	}
	
	
	//Macro Encoder Positions go here
	public static final double groundElevatorEncoderPosition = 0;
	public static final double switchElevatorEncoderPosition = 50;
	public static final double scaleLowElevatorEncoderPosition = 100;
	public static final double scaleMidElevatorEncoderPosition = 150;
	public static final double scaleHighElevatorEncoderPosition = 200;
	public static final double hangElevatorEncoderPosition = 175;
		
	//Elevator PID
	
	
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
	
	
	//Pathfinder
	
	public static final double timeStep= 0.1; //Seconds
	public static final double maxVelocity = 5; //m/s
	public static final double maxAcceleration = 0.12;  //m/s/s
	public static final double maxJerk = 0.006; //m/s/s/s
	
	//Pathfinder PID
	

	//PID Configuration
	// The first argument is the proportional gain. Usually this will be quite high
	// The second argument is the integral gain. This is unused for motion profiling
	// The third argument is the derivative gain. Tweak this if you are unhappy with the tracking of the trajectory
	// The fourth argument is the velocity ratio. This is 1 over the maximum velocity you provided in the 
	//   trajectory configuration (it translates m/s to a -1 to 1 scale that your motors can read)
	// The fifth argument is your acceleration gain. Tweak this if you want to get to a higher or lower speed quicker

	public static final double accelerationGain = 0.05;
	public static final double pathfinderP = 0.1;
	public static final double pathfinderI = 0;
	public static final double pathfinderD = 0;
	public static final double pathfinderVR = 1;
	
	
}
