package com.team2169.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team2169.robot.ActuatorMap;
import com.team2169.robot.Constants;
import com.team2169.robot.ControlMap;
import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.RobotStates.ArmPos;
import com.team2169.robot.RobotStates.ElevatorPos;
import com.team2169.robot.canCycles.CANCycleHandler;
import com.team2169.util.TalonMaker;

import edu.wpi.first.wpilibj.DriverStation;

public class ElevatorArm extends Subsystem{
	
    private static ElevatorArm eInstance = null;

    public static ElevatorArm getInstance() {
        if (eInstance == null) {
            eInstance = new ElevatorArm();
        }
        return eInstance;
    }
	
	TalonSRX elevator;
	TalonSRX elevatorSlave;
	TalonSRX arm;
	TalonSRX armSlave;
	
	public ElevatorArm() {
		
		//Lift Motors Setup
		
		RobotStates.elevatorInPosition = false;
		
		//Define Lift Talons
		elevator = new TalonSRX(ActuatorMap.elevatorMasterID);
		elevatorSlave = new TalonSRX(ActuatorMap.elevatorSlaveID);
		elevatorSlave.set(ControlMode.Follower, ActuatorMap.elevatorMasterID);
		
		//Define Arm Talon
		arm = new TalonSRX(ActuatorMap.armID);
		
		//Set Data from Constants
		Constants.setElevatorDataFromConstants();
		Constants.setArmDataFromConstants();
		Constants.calculateMacros();
		
		//Prep Lift for Motion Profiling
		elevator = TalonMaker.prepTalonForMotionProfiling(elevator, Constants.elevatorData);
		
		//Prep Lift for Motion Profiling
		arm = TalonMaker.prepTalonForMotionProfiling(arm, Constants.armData);
		
		
	}
	
	void elevatorToPos(double pos) {
		elevator.set(ControlMode.MotionMagic, pos);
	}
	
	void armToPos(double pos) {
		arm.set(ControlMode.MotionMagic, pos);
	}
	
	public void getElevatorFinishedState() {
	
		if(elevator.getClosedLoopError(Constants.elevatorData.pidLoopIDx) < Constants.elevatorData.allowedError || 
				elevator.getClosedLoopError(Constants.elevatorData.pidLoopIDx) > -Constants.elevatorData.allowedError) {
			RobotStates.elevatorInPosition = true;
		}
		
		RobotStates.elevatorInPosition = false;
	
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
				elevator.set(ControlMode.PercentOutput, ControlMap.elevatorOverrideValue());	
			}
			
		}
		
		else {
			
			//Pull WantedState from ControlMap
			ControlMap.getWantedElevatorPos();
			
			//set robot's actual state to WantedState's value
			switch(RobotWantedStates.wantedElevatorPos){
			case GROUND:
				
				//CANCycle for Ground Position
				
				//If DebugMode, print info
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("Running Macro: Ground", false);
				}
				
				//Actuate the Motor
				elevatorToPos(Constants.groundElevatorEncoderPosition);
				
				//Set RobotStates
				RobotStates.armPos = ArmPos.EXTENDED;
				RobotStates.elevatorPos = ElevatorPos.GROUND;
				break;
				
			case HANG:
			
				//CANCycle Hang Position
				
				//If DebugMode, print info
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("Running Macro: Hang", false);
				}
				
				//Actuate the Motor
				elevatorToPos(Constants.hangElevatorEncoderPosition);
				
				//Set RobotStates
				RobotStates.armPos = ArmPos.FULLY_RETRACTED;
				RobotStates.elevatorPos = ElevatorPos.HANG;
				break;
			
			case SCALE_HIGH:
				
				//CANCycle for (High) Position
				
				//If DebugMode, print info
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("Running Macro: Scale High", false);
				}
				
				//Actuate the Motor
				elevatorToPos(Constants.scaleHighElevatorEncoderPosition);
				
				//Set Robot States
				RobotStates.armPos = ArmPos.EXTENDED;
				RobotStates.elevatorPos = ElevatorPos.SCALE_HIGH;
				break;
				
			case SCALE_LOW:
				
				//CANCycle for Scale (Low) Position
				
				//If DebugMode, print info
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("Running Macro: Scale Low", false);
				}
				
				//Actuate the Motor
				elevatorToPos(Constants.scaleLowElevatorEncoderPosition);

				//Set Robot States
				RobotStates.armPos = ArmPos.EXTENDED;
				RobotStates.elevatorPos = ElevatorPos.SCALE_LOW;
				break;
				
			case SCALE_MID:
				
				//CANCycle for Scale (Mid) Position

				//If DebugMode, print info
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("Running Macro: Scale Mid", false);
				}
				
				//Actuate the Motor
				elevatorToPos(Constants.scaleMidElevatorEncoderPosition);
				
				//Set Robot States
				RobotStates.armPos = ArmPos.EXTENDED;
				RobotStates.elevatorPos = ElevatorPos.SCALE_MID;
				break;
				
			case SWITCH:
		
				//If DebugMode, print info
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("Running Macro: Switch", false);
				}
				
				//Actuate the Motor
				elevatorToPos(Constants.scaleMidElevatorEncoderPosition);
				
				//Set Robot States
				RobotStates.armPos = ArmPos.EXTENDED;
				RobotStates.elevatorPos = ElevatorPos.SWITCH;
				break;
				
			default:
				break;

			}
			
		}

			//Return Elevator Height from yo-yo sensor
			RobotStates.elevatorHeight = elevator.getSelectedSensorPosition(Constants.elevatorData.slotIDx);
			getElevatorFinishedState();
			
	}

	@Override
	public void pushToDashboard() {
		
	}

	@Override
	public void zeroSensors() {
		
		elevator.setSelectedSensorPosition(0, Constants.elevatorData.slotIDx, Constants.elevatorData.timeoutMs);
		arm.setSelectedSensorPosition(0, Constants.armData.slotIDx, Constants.armData.timeoutMs);
		
	}

	@Override
	public void stop() {
		
	}
	
}
