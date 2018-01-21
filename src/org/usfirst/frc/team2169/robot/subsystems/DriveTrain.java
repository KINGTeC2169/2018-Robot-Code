package org.usfirst.frc.team2169.robot.subsystems;

import org.usfirst.frc.team2169.robot.ActuatorMap;
import org.usfirst.frc.team2169.robot.Constants;
import org.usfirst.frc.team2169.robot.ControlMap;
import org.usfirst.frc.team2169.robot.RobotStates;
import org.usfirst.frc.team2169.robot.RobotStates.driveMode;
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
		left.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		leftSlave1.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		leftSlave2.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		right.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		rightSlave1.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		rightSlave2.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		
		
	}
	
	
	public void drive() {
		if(ControlMap.primaryDriverOverride()) {

			//If you get here, override is active.
			left.set(ControlMode.PercentOutput, ControlMap.leftTankStick());
			right.set(ControlMode.PercentOutput, ControlMap.rightTankStick());
			shift(true);
	
		}	
		
		else {
			
			//TODO Drive code with acceleration handler
			
			shift(false);
			
		}
		
	}
	
	
	void shift(boolean override) {
		//If the override is active, switch to the requested Drive Mode
		
		ControlMap.getWantedShift();
		
		if(override) {
			
			DriverStation.reportWarning("Shifting Override Active", false);
			
			if(RobotWantedStates.wantedDriveMode == WantedDriveMode.SHIFT_TO_HIGH) {
				
				shifter.set(Constants.highGear);
				DriverStation.reportWarning("High Gear (override)", false);
				RobotStates.driveMode = driveMode.HIGH;
				RobotWantedStates.wantedDriveMode = WantedDriveMode.HIGH;
				
			}
			
			if(RobotWantedStates.wantedDriveMode == WantedDriveMode.SHIFT_TO_LOW) {
				
				shifter.set(Constants.lowGear);
				DriverStation.reportWarning("Low Gear (override)", false);
				RobotStates.driveMode = driveMode.LOW;
				RobotWantedStates.wantedDriveMode = WantedDriveMode.LOW;
				
			}
		}
		else {
			
			if(RobotWantedStates.wantedDriveMode == WantedDriveMode.SHIFT_TO_HIGH && 
					FlyByWireHandler.determineSafety(RobotWantedStates.wantedDriveMode)) {
				
				shifter.set(Constants.highGear);
				DriverStation.reportWarning("High Gear (no override)", false);
				RobotStates.driveMode = driveMode.HIGH;
				RobotWantedStates.wantedDriveMode = WantedDriveMode.HIGH;
				
			}
			
			if(RobotWantedStates.wantedDriveMode == WantedDriveMode.SHIFT_TO_LOW && 
					FlyByWireHandler.determineSafety(RobotWantedStates.wantedDriveMode)) {
				
				shifter.set(Constants.lowGear);
				DriverStation.reportWarning("Low Gear (no override)", false);
				RobotStates.driveMode = driveMode.LOW;
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
