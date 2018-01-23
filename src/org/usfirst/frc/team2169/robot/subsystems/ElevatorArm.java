package org.usfirst.frc.team2169.robot.subsystems;

import org.usfirst.frc.team2169.robot.ActuatorMap;
import org.usfirst.frc.team2169.robot.ControlMap;
import org.usfirst.frc.team2169.robot.RobotStates;
import org.usfirst.frc.team2169.robot.RobotWantedStates;
import org.usfirst.frc.team2169.robot.auto.canCycles.CANCycleHandler;
import org.usfirst.frc.team2169.robot.RobotStates.ArmPos;
import org.usfirst.frc.team2169.robot.RobotStates.ElevatorPos;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DriverStation;

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
			
			//Cancel all CANCycles related to Elevator/Arm
			CANCycleHandler.cancelArmElevatorCycles();
			
			
			if(RobotStates.debugMode) {
				DriverStation.reportWarning("Operator Override Active", false);
			}

			//Set power to arm based directly on operator input
			if(ControlMap.isArmOverrideActive()) {
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("Arm Override Active", false);
				}				
				arm.set(ControlMode.PercentOutput, ControlMap.armOverrideValue());
			}

			//Set power to elevator based directly on operator input			
			if(ControlMap.isElevatorOverrideActive()) {
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("Elevator Override Active", false);
				}	
				lift.set(ControlMode.PercentOutput, ControlMap.elevatorOverrideValue());	
			}
			
		}
		
		else {
			
			//Pull WantedState from ControlMap
			ControlMap.getWantedElevatorPos();
			
			//If safe, set robot's actual state to WantedState's value
			switch(RobotWantedStates.wantedElevatorPos){
			case GROUND:
				
				//Check if safe
				//CANCycle for Ground Position
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("Running Macro: Ground", false);
				}
				RobotStates.armPos = ArmPos.EXTENDED;
				RobotStates.elevatorPos = ElevatorPos.GROUND;
				break;
				
			case HANG:
			
				//Check if safe
				//CANCycle Hang Position
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("Running Macro: Hang", false);
				}
				RobotStates.armPos = ArmPos.FULLY_RETRACTED;
				RobotStates.elevatorPos = ElevatorPos.HANG;
				break;
			
			case SCALE_HIGH:
				
				//Check if safe
				//CANCycle for (High) Position
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("Running Macro: Scale High", false);
				}
				RobotStates.armPos = ArmPos.EXTENDED;
				RobotStates.elevatorPos = ElevatorPos.SCALE_HIGH;
				break;
				
			case SCALE_LOW:
				
				//Check if safe
				//CANCycle for Scale (Low) Position
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("Running Macro: Scale Low", false);
				}
				RobotStates.armPos = ArmPos.EXTENDED;
				RobotStates.elevatorPos = ElevatorPos.SCALE_LOW;
				break;
				
			case SCALE_MID:
				
				//Check if safe
				//CANCycle for Scale (Mid) Position

				if(RobotStates.debugMode) {
					DriverStation.reportWarning("Running Macro: Scale Mid", false);
				}
				RobotStates.armPos = ArmPos.EXTENDED;
				RobotStates.elevatorPos = ElevatorPos.SCALE_MID;
				break;
				
			case SWITCH:
				
				//Check if safe
				//CANCycle for Switch Position
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("Running Macro: Switch", false);
				}
				RobotStates.armPos = ArmPos.EXTENDED;
				RobotStates.elevatorPos = ElevatorPos.SWITCH;
				break;
				
			default:
				break;

			}
			
		}

			//Return Elevator Height from yo-yo sensor
			RobotStates.elevatorHeight = elevatorHeightSensor.getValue();
	}

	@Override
	public void pushToDashboard() {
		
	}

	@Override
	public void zeroSensors() {
		
	}

	@Override
	public void stop() {
		
	}
	
}
