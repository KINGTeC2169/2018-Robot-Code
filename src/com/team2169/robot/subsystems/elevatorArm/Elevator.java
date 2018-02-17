package com.team2169.robot.subsystems.elevatorArm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team2169.robot.ActuatorMap;
import com.team2169.robot.Constants;
import com.team2169.robot.ControlMap;
import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotWantedStates;
import com.team2169.util.Converter;
import com.team2169.util.TalonMaker;
import com.team2169.robot.RobotStates.ElevatorPos;

public class Elevator {

	//Create Talons
	private TalonSRX elevator;
	private TalonSRX elevatorSlave;
	private int setpoint;
	
	public Elevator() {
		
		//Define Lift Talons
		elevator = new TalonSRX(ActuatorMap.elevatorMasterID);
		elevatorSlave = new TalonSRX(ActuatorMap.elevatorSlaveID);
		elevatorSlave.set(ControlMode.Follower, ActuatorMap.elevatorMasterID);
		
		//Pull Constants Data for Elevator
		Constants.setElevatorDataFromConstants();
		
		//Apply Talon Settings
		elevator = TalonMaker.prepTalonForMotionProfiling(elevator, Constants.elevatorData);
	}
	
	public void elevatorMacroLooper() {

		
		//set robot's actual state to WantedState's value
		switch(RobotWantedStates.wantedElevatorPos){
		
			case OVERRIDE: default:
				elevatorOverrideLooper(ControlMap.getOperatorStickValue());
				
				//Set RobotStates
				RobotStates.elevatorPos = ElevatorPos.OVERRIDE;
				break;
			
				
			case HOLD_POSITION:
				
				elevatorOverrideLooper(0);
				
				//Set RobotStates
				RobotStates.elevatorPos = ElevatorPos.HOLD_POSITION;
				break;
				
			case GROUND:
				
				//CANCycle for Ground Position
			
				//Actuate the Motor
				elevatorToPos(Constants.groundElevatorEncoderPosition);
				
				//Set RobotStates
				RobotStates.elevatorPos = ElevatorPos.GROUND;
				break;
				
			case HANG:
			
				//CANCycle Hang Position
				
				//Actuate the Motor
				elevatorToPos(Constants.hangElevatorEncoderPosition);
				
				//Set RobotStates
				RobotStates.elevatorPos = ElevatorPos.HANG;
				break;
			
			case SCALE_HIGH:
				
				//CANCycle for (High) Position
				
				//Actuate the Motor
				elevatorToPos(Constants.scaleHighElevatorEncoderPosition);
				
				//Set Robot States
				RobotStates.elevatorPos = ElevatorPos.SCALE_HIGH;
				break;
			
			case SCALE_MID:
				
				//CANCycle for Scale (Mid) Position
	
				//Actuate the Motor
				elevatorToPos(Constants.scaleMidElevatorEncoderPosition);
				
				//Set Robot States
				RobotStates.elevatorPos = ElevatorPos.SCALE_MID;
				break;
				
			case SCALE_LOW:
				
				//CANCycle for Scale (Low) Position
				
				//Actuate the Motor
				elevatorToPos(Constants.scaleLowElevatorEncoderPosition);
	
				//Set Robot States
				RobotStates.elevatorPos = ElevatorPos.SCALE_LOW;
				break;
				
			case SWITCH:
		
				//CANCycle for Switch			
				
				//Actuate the Motor
				elevatorToPos(Constants.scaleMidElevatorEncoderPosition);
				
				//Set Robot States
				RobotStates.elevatorPos = ElevatorPos.SWITCH;
				break;

		}
	
	}

	private void elevatorToPos(int pos) {
		elevator.set(ControlMode.Position, pos);
		RobotStates.elevatorHeight = elevator.getSelectedSensorPosition(Constants.elevatorData.slotIDx);
		getElevatorFinishedState();
	}
	
	public void elevatorOverrideLooper(double joystickValue) {
		setpoint = elevator.getSelectedSensorPosition(Constants.elevatorData.slotIDx);
		setpoint += Converter.inchesToTicks(ControlMap.elevatorOverrideSetpointMovement * joystickValue * Constants.elevatorDrumReduction);
		elevatorToPos(setpoint);
	}
	
	public void getElevatorFinishedState() {
		
		if(elevator.getClosedLoopError(Constants.elevatorData.pidLoopIDx) < Constants.elevatorData.allowedError || 
				elevator.getClosedLoopError(Constants.elevatorData.pidLoopIDx) > -Constants.elevatorData.allowedError) {
			RobotStates.elevatorInPosition = true;
		}
		
		RobotStates.elevatorInPosition = false;
	
	}
	
	public void zeroSensors() {
		elevator.setSelectedSensorPosition(0, Constants.elevatorData.slotIDx, Constants.elevatorData.timeoutMs);
	}
	
	public void stop() {
		elevator.set(ControlMode.PercentOutput, 0);
	}
	
	
}
