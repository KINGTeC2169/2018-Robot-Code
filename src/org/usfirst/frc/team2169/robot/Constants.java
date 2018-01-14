package org.usfirst.frc.team2169.robot;

public class Constants {

	//Auto Names
	public static final String defaultAutoName = "Default Auto";
	public static final String secondAutoName = "Auto 2";
	public static final String thirdAutoName  = "Auto 3";
	
	
	//Robot Contants go here
	public static final double wheelBaseWidth = 5;
	public static final double wheelDiameter = 5;
	public static final int ticksPerRotation = 1250;

	
	//Pathfinder
	
	public static final double timeStep= 0.05; //Seconds
	public static final double maxVelocity = 1.7; //m/s
	public static final double maxAcceleration = 2.0;  //m/s/s
	public static final double maxJerk = 60.0; //m/s/s/s
	
	//Pathfinder PID
	

	//PID Configuration
	// The first argument is the proportional gain. Usually this will be quite high
	// The second argument is the integral gain. This is unused for motion profiling
	// The third argument is the derivative gain. Tweak this if you are unhappy with the tracking of the trajectory
	// The fourth argument is the velocity ratio. This is 1 over the maximum velocity you provided in the 
	//   trajectory configuration (it translates m/s to a -1 to 1 scale that your motors can read)
	// The fifth argument is your acceleration gain. Tweak this if you want to get to a higher or lower speed quicker

	public static final double accelerationGain = 0;
	public static final double pathfinderP = 0;
	public static final double pathfinderI = 0;
	public static final double pathfinderD = 0;
	public static final double pathfinderVR = 0;
	
	
}
