package org.usfirst.frc.team2169.robot.subsystems;

import org.usfirst.frc.team2169.robot.ActuatorMap;
import org.usfirst.frc.team2169.robot.Constants;
import org.usfirst.frc.team2169.robot.subsystems.Subsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class DriveTrain extends Subsystem{
	
	//Define null objects up here
	//Public because autonomous needs to access actuators
	//Static because there is only one of each subsystem
	public static TalonSRX left;
	public static TalonSRX leftSlave1;
	public static TalonSRX leftSlave2;
	public static TalonSRX right;
	public static TalonSRX rightSlave1;
	public static TalonSRX rightSlave2;
	
	public DriveTrain(){
		
		//Create the objects and set properties
		left = new TalonSRX(ActuatorMap.leftMasterDriveTalon);
		leftSlave1 = new TalonSRX(ActuatorMap.leftSlave1DriveTalon);
		leftSlave2 = new TalonSRX(ActuatorMap.leftSlave2DriveTalon);
		right = new TalonSRX(ActuatorMap.rightMasterDriveTalon);
		rightSlave1 = new TalonSRX(ActuatorMap.rightSlave1DriveTalon);
		rightSlave2 = new TalonSRX(ActuatorMap.rightSlave2DriveTalon);
		
		//Left Slave Talons
		leftSlave1.set(ControlMode.Follower, left.getDeviceID());
		leftSlave2.set(ControlMode.Follower, left.getDeviceID());

		//Right Slave Talons
		rightSlave1.set(ControlMode.Follower, right.getDeviceID());
		rightSlave2.set(ControlMode.Follower, right.getDeviceID());

		//Set Current Limits
		left.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		leftSlave1.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		leftSlave2.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		right.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		rightSlave1.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		rightSlave2.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		
		
	}
	
	
	public static void drive(boolean handleAcceleration, double left_, double right_) {
		if(!handleAcceleration) {

			//If you get here, override is active.
			left.set(ControlMode.PercentOutput, left_);
			right.set(ControlMode.PercentOutput, right_);
	
		}	
		
		else {
			
			//Drive code with acceleration handler
			
		}
		
	}
	
	@Override
	public void pushToDashboard() {
		
		//Put any SmartDash info here.
		
	}

	@Override
	public void zeroSensors() {
		
		//Zero sensors method
		
	}

	@Override
	public void stop() {
		
		//Code to make all moving parts halt goes here.
		
	}

}
