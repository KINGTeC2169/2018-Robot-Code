package org.usfirst.frc.team2169.robot.subsystems;

import org.usfirst.frc.team2169.robot.ActuatorMap;
import org.usfirst.frc.team2169.robot.ControlMap;
import org.usfirst.frc.team2169.robot.RobotStates;
import org.usfirst.frc.team2169.robot.RobotWantedStates;
import org.usfirst.frc.team2169.robot.auto.canCycles.CANCycleHandler;
import org.usfirst.frc.team2169.robot.RobotStates.armPos;
import org.usfirst.frc.team2169.robot.RobotStates.elevatorPos;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;

public class ElevatorArm extends Subsystem{

	//TODO Add support for Arm and Elevator Lift
	//Needs support for:
	//Macros (preset positions) that can be configured from Constants.java
	//Manual Macro override for Operator 
	//Public getter for exact arm position
	//Support for arm position and elevator positions enums
		//These macros should be in ranges because they will be used for rough CG calculations 
		//by the DriveTrain class to determine acceleration limits to prevent tipping.
	
	
	TalonSRX lift;
	TalonSRX liftSlave;
	TalonSRX arm;
	TalonSRX armSlave;
	AnalogInput elevatorHeightSensor;
	
	public ElevatorArm() {
		
		elevatorHeightSensor = new AnalogInput(ActuatorMap.elevatorHeightSensorPort);
		
		//Lift Motors Setup
		lift = new TalonSRX(ActuatorMap.liftMasterID);
		liftSlave = new TalonSRX(ActuatorMap.liftSlaveID);
		liftSlave.set(ControlMode.Follower, ActuatorMap.liftMasterID);
		
		//Arm Motors Setup
		arm = new TalonSRX(ActuatorMap.armMasterID);
		armSlave = new TalonSRX(ActuatorMap.armSlaveID);
		armSlave.set(ControlMode.Follower, ActuatorMap.armMasterID);
	}
	
	public void elevatorHandler() {
		
		//Make sure these enums are actively updated or depended on.
		//This method can be deleted
		
		if(ControlMap.operatorOverrideActive()) {

			if(ControlMap.isArmOverrideActive()) {
				arm.set(ControlMode.PercentOutput, ControlMap.armOverrideValue());
			}
			
			if(ControlMap.isElevatorOverrideActive()) {
				lift.set(ControlMode.PercentOutput, ControlMap.elevatorOverrideValue());	
			}
			
			CANCycleHandler.cancelArmElevatorCycles();
			
		}
		
		else {
			
			switch(RobotWantedStates.elevatorPos){
			case GROUND:
				
				//Check if safe
				//CANCycle for Ground Position
				RobotStates.armPos = armPos.GROUND;
				RobotStates.elevatorPos = elevatorPos.GROUND;
				break;
				
			case HANG:
			
				//Check if safe
				//CANCycle Hang Position
				RobotStates.armPos = armPos.FULLY_RETRACTED;
				RobotStates.elevatorPos = elevatorPos.HANG;
				break;
			
			case SCALE_HIGH:
				
				//Check if safe
				//CANCycle for (High) Position
				RobotStates.armPos = armPos.PARTIALLY_RETRACTED;
				RobotStates.elevatorPos = elevatorPos.SCALE_HIGH;
				break;
				
			case SCALE_LOW:
				
				//Check if safe
				//CANCycle for Scale (Low) Position
				RobotStates.armPos = armPos.PARTIALLY_RETRACTED;
				RobotStates.elevatorPos = elevatorPos.SCALE_LOW;
				break;
				
			case SCALE_MID:
				
				//Check if safe
				//CANCycle for Scale (Mid) Position
				RobotStates.armPos = armPos.PARTIALLY_RETRACTED;
				RobotStates.elevatorPos = elevatorPos.SCALE_MID;
				break;
				
			case SWITCH:
				
				//Check if safe
				//CANCycle for Switch Position
				RobotStates.armPos = armPos.PARTIALLY_RETRACTED;
				RobotStates.elevatorPos = elevatorPos.SWITCH;
				break;
				
			default:
				break;

			}
			
		}

			RobotStates.elevatorHeight = elevatorHeightSensor.getValue();
	}

	@Override
	public void pushToDashboard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void zeroSensors() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	
}
