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
	
	TalonSRX lift;
	TalonSRX liftSlave;
	TalonSRX arm;
	TalonSRX armSlave;
	
	public ElevatorArm() {
		
		//Lift Motors Setup
		RobotStates.elevatorInPosition = false;
		lift = new TalonSRX(ActuatorMap.liftMasterID);
		liftSlave = new TalonSRX(ActuatorMap.liftSlaveID);
		liftSlave.set(ControlMode.Follower, ActuatorMap.liftMasterID);
		Constants.setLiftDataFromConstants();

		//Prep Lift for Motion Profiling
		lift = TalonMaker.prepTalonForMotionProfiling(lift, Constants.liftData);
		
		//Arm Motors Setup
		arm = new TalonSRX(ActuatorMap.armID);
		
	}
	
	void elevatorToPos(double pos) {
		lift.set(ControlMode.MotionMagic, pos);
	}
	
	public void getFinishedState() {
	
		if(lift.getClosedLoopError(Constants.liftData.pidLoopIDx) < Constants.liftData.allowedError || 
				lift.getClosedLoopError(Constants.liftData.pidLoopIDx) > -Constants.liftData.allowedError) {
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
				lift.set(ControlMode.PercentOutput, ControlMap.elevatorOverrideValue());	
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
			RobotStates.elevatorHeight = lift.getSelectedSensorPosition(Constants.liftData.slotIDx);
			getFinishedState();
			
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
