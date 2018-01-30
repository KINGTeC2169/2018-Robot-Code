package org.usfirst.frc.team2169.robot;

public class ActuatorMap {

	//Put Actuator Ports here
		
		//TalonSRX Ports
			//DriveTrain TalonSRX Ports
			public static final int leftMasterDriveTalon = 0;
			public static final int leftSlave1DriveTalon = 1;
			public static final int leftSlave2DriveTalon = 2;
			public static final int rightMasterDriveTalon = 3;
			public static final int rightSlave1DriveTalon = 4;
			public static final int rightSlave2DriveTalon = 5;
	
			//Lift TalonSRX IDs
			public static final int liftMasterID = 6;
			public static final int liftSlaveID = 7;
			public static final int armMasterID = 8;
			public static final int armSlaveID = 9;
			
			//Intake TalonSRX IDs
			public static final int leftIntakeID = 10;
			public static final int rightIntakeID = 11;
		
		//Pnuematics
			//Compressor
			public static final int compressorPCMPort = 0;
			
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

