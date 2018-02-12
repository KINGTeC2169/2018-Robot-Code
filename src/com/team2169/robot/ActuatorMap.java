package com.team2169.robot;

public class ActuatorMap {

	//Put Actuator Ports here
		
		//TalonSRX Ports
			//DriveTrain TalonSRX Ports
			public static final int leftMasterDriveTalon = 0;
			public static final int leftTop = 1;
			public static final int leftFront = 2;
			public static final int rightFront = 3;
			public static final int rightTop = 4;
			public static final int rightMasterDriveTalon = 5;
	
			//Elevator TalonSRX IDs
			public static final int elevatorMasterID = 9;
			public static final int elevatorSlaveID = 10;
			public static final int armID = 8;
			
			//Intake TalonSRX IDs
			public static final int leftIntakeID = 6;
			public static final int rightIntakeID = 7;
		
		//Pnuematics
			//Compressor
			public static final int compressorPCMPort = 12;
			
			//Intake Piston
			public static final int clampPortForward = 1;
			public static final int clampPortReverse = 2;
			
			//Drive Train Shifting
			public static final int dtSpeedShiftForward = 3;
			public static final int dtSpeedShiftReverse = 4;
			
			//PTO Ports
			public static final int ptoShiftForward = 5;
			public static final int ptoShiftReverse = 6;
			
			//Hanger Ports
			public static final int platformReleasePiston = 0;
			
		//Analog Inputs
			public static final int elevatorHeightSensorPort = 0;
			


}

