package org.usfirst.frc.team2169.robot;

public class RobotStates {
	
	
	//Put Autonomous States in here
	public static enum runningMode{
		
		IDLE, AUTO, TELEOP, LIMP
		
	}

	public static runningMode runningMode;
	
	public static boolean isFMSConnected;
	
}
