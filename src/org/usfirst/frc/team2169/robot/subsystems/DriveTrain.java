package org.usfirst.frc.team2169.robot.subsystems;

import org.usfirst.frc.team2169.robot.ActuatorMap;
import org.usfirst.frc.team2169.robot.ControlMap;
import org.usfirst.frc.team2169.robot.RobotStates;
import org.usfirst.frc.team2169.robot.RobotStates.DriveMode;
import org.usfirst.frc.team2169.robot.RobotWantedStates;
import org.usfirst.frc.team2169.robot.RobotWantedStates.WantedDriveMode;
import org.usfirst.frc.team2169.robot.subsystems.Subsystem;
import org.usfirst.frc.team2169.util.FlyByWireHandler;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;

public class DriveTrain extends Subsystem{
	
	//Define null objects up here
	//Public because autonomous needs to access actuators
	//Static because there is only one of each subsystem
	public TalonSRX left;
	public TalonSRX leftSlave1;
	public TalonSRX leftSlave2;
	public TalonSRX right;
	public TalonSRX rightSlave1;
	public TalonSRX rightSlave2;
	public DoubleSolenoid shifter;
	
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

		/* Commented out because they throw errors if there aren't feedback devices plugged in.
		left.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		leftSlave1.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		leftSlave2.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		right.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		rightSlave1.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		rightSlave2.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		*/
		
	}
	
	
	public void drive() {
		
		//Override Active
		if(ControlMap.primaryDriverOverride()) {

			if(RobotStates.debugMode) {
				DriverStation.reportError("DriveTrain Class: Master Override Active!", false);	
			}
			
			//Raw stick output with Squared controls
			left.set(ControlMode.PercentOutput, ControlMap.leftTankStick(true));
			right.set(ControlMode.PercentOutput, ControlMap.rightTankStick(true));
			
			//Shift with override
			shift(true);
	
		}	

		//Override Not Active
		else {
			
			//Acceleration Handler with Squared controls
			left.set(ControlMode.PercentOutput, ControlMap.leftTankStick(true) * FlyByWireHandler.getSafeSpeed());
			right.set(ControlMode.PercentOutput, ControlMap.rightTankStick(true) * FlyByWireHandler.getSafeSpeed());
			
			//Shift without override
			shift(false);
			
		}
		
	}
	
	
	void shift(boolean override) {
		//If the override is active, switch to the requested Drive Mode
		
		ControlMap.getWantedShift();
		
		//If Master Override is Active
		if(override) {
			
			if(RobotStates.debugMode) {
				DriverStation.reportWarning("DriveTrain Class: Shifting Override Active", false);
			}
			
			//If wanting to shift high
			if(RobotWantedStates.wantedDriveMode == WantedDriveMode.SHIFT_TO_HIGH) {
				
				//shifter.set(Constants.highGear);
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("High Gear (override)", false);
				}
				
				//Set Robot States
				RobotStates.driveMode = DriveMode.HIGH;
				RobotWantedStates.wantedDriveMode = WantedDriveMode.HIGH;
				
			}
			
			//If wanting to shift low
			if(RobotWantedStates.wantedDriveMode == WantedDriveMode.SHIFT_TO_LOW) {
				
				//shifter.set(Constants.lowGear);
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("Low Gear (override)", false);
				}
				
				//Set Robot States
				RobotStates.driveMode = DriveMode.LOW;
				RobotWantedStates.wantedDriveMode = WantedDriveMode.LOW;
				
			}
		}
		
		//If Master Override is not Active
		else {
			
			
			//If wanting to shift shift high, and it is safe
			if(RobotWantedStates.wantedDriveMode == WantedDriveMode.SHIFT_TO_HIGH && 
					FlyByWireHandler.determineShiftSafety(RobotWantedStates.wantedDriveMode)) {
				
				//shifter.set(Constants.highGear);
				
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("High Gear (safe)", false);
				}
				
				//Set Robot States
				RobotStates.driveMode = DriveMode.HIGH;
				RobotWantedStates.wantedDriveMode = WantedDriveMode.HIGH;
				
			}
			
			//If wanting to shift shift low, and it is safe
			else if(RobotWantedStates.wantedDriveMode == WantedDriveMode.SHIFT_TO_LOW && 
					FlyByWireHandler.determineShiftSafety(RobotWantedStates.wantedDriveMode)) {
				
				//shifter.set(Constants.lowGear);

				if(RobotStates.debugMode) {
					DriverStation.reportWarning("Low Gear (safe)", false);
				}

				//Set Robot States
				RobotStates.driveMode = DriveMode.LOW;
				RobotWantedStates.wantedDriveMode = WantedDriveMode.LOW;
				
			}
		
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
