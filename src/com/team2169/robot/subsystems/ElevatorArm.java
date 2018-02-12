package com.team2169.robot.subsystems;

import com.team2169.robot.Constants;
import com.team2169.robot.ControlMap;
import com.team2169.robot.RobotStates;
import com.team2169.robot.subsystems.elevatorArm.Arm;
import com.team2169.robot.subsystems.elevatorArm.Elevator;

import edu.wpi.first.wpilibj.DriverStation;

public class ElevatorArm extends Subsystem{
	
	private Arm arm;
	private Elevator elevator;
	
	public ElevatorArm() {
		
		arm = new Arm();
		elevator = new Elevator();
		
		RobotStates.elevatorInPosition = false;
		
		//Calulcate Latest Macro Positions
		Constants.calculateMacros();
		
	}
	
	public void elevatorArmHandler() {
		
		ControlMap.getElevatorArmControls();
		
		//Arm Handler
		if(RobotStates.armOverideMode) {
			if(RobotStates.debugMode) {
				DriverStation.reportWarning("Arm Override Active", false);
			}				
			arm.armOverrideLooper(ControlMap.armOverrideValue());

		}
		else {
		
			arm.armMacroLooper();
			
		}

		//Elevator Handler		
		if(RobotStates.elevatorOverideMode) {
			if(RobotStates.debugMode) {
				DriverStation.reportWarning("Elevator Override Active", false);
			}	
			elevator.elevatorOverrideLooper(ControlMap.elevatorOverrideValue());
		}		
		else {
		
			elevator.elevatorMacroLooper();
			
		}

	}
	
	@Override
	public void pushToDashboard() {
		
	}

	@Override
	public void zeroSensors() {
		
		elevator.zeroSensors();
		arm.zeroSensors();
		
	}

	@Override
	public void stop() {
		
		elevator.stop();
		arm.stop();
		
	}
	
}
