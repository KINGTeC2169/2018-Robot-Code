package com.team2169.robot.subsystems.elevatorArm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team2169.robot.ActuatorMap;
import com.team2169.robot.Constants;
import com.team2169.robot.ControlMap;
import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.RobotStates.ArmPos;
import com.team2169.util.Converter;
import com.team2169.util.TalonMaker;

import edu.wpi.first.wpilibj.DriverStation;

public class Arm {
	
	//Create Talons
	private TalonSRX arm;
	private int setpoint;
	
	public Arm() {
		
		//Define Lift Talons
		arm = new TalonSRX(ActuatorMap.armID);
		
		//Pull Constants Data for Arm
		Constants.setArmDataFromConstants();
		
		//Apply Talon Settings
		arm = TalonMaker.prepTalonForMotionProfiling(arm, Constants.armData);
		
	}

	//
	private void armToPos(int pos) {
		arm.set(ControlMode.MotionProfile, pos);
	}
	
	public void armOverrideLooper(double joystickValue) {
		setpoint = arm.getSelectedSensorPosition(Constants.armData.slotIDx);
		setpoint += Converter.inchesToTicks(ControlMap.armOverrideSetpointMovement * joystickValue);
		armToPos(setpoint);
	}
	
	public void armMacroLooper() {

		
		//set robot's actual state to WantedState's value
		switch(RobotWantedStates.wantedElevatorPos){
		case GROUND:
			
			//CANCycle for Ground Position
			
			//If DebugMode, print info
			if(RobotStates.debugMode) {
				DriverStation.reportWarning("Running Arm Macro: Ground", false);
			}
			
			//Actuate the Motor
			armToPos(Constants.extendedArmEncoderPosition);
			
			//Set RobotStates
			RobotStates.armPos = ArmPos.EXTENDED;
			break;
			
		case HANG:
		
			//CANCycle Hang Position
			
			//If DebugMode, print info
			if(RobotStates.debugMode) {
				DriverStation.reportWarning("Running Arm Macro: Hang", false);
			}
			
			//Actuate the Motor
			armToPos(Constants.retractedArmEncoderPosition);
			
			//Set RobotStates
			RobotStates.armPos = ArmPos.RETRACTED;
			break;
		
		case SCALE_HIGH:
			
			//CANCycle for (High) Position
			
			//If DebugMode, print info
			if(RobotStates.debugMode) {
				DriverStation.reportWarning("Running Arm Macro: Scale High", false);
			}
			
			//Actuate the Motor
			armToPos(Constants.retractedArmEncoderPosition);
			
			//Set RobotStates
			RobotStates.armPos = ArmPos.RETRACTED;
			break;
			
		case SCALE_MID:
			
			//CANCycle for Scale (Mid) Position

			//If DebugMode, print info
			if(RobotStates.debugMode) {
				DriverStation.reportWarning("Running Arm Macro: Scale Mid", false);
			}
			
			//Actuate the Motor
			armToPos(Constants.retractedArmEncoderPosition);
			
			//Set RobotStates
			RobotStates.armPos = ArmPos.RETRACTED;
			break;
			
		case SCALE_LOW:
			
			//CANCycle for Scale (Low) Position
			
			//If DebugMode, print info
			if(RobotStates.debugMode) {
				DriverStation.reportWarning("Running Arm Macro: Scale Low", false);
			}
			
			//Actuate the Motor
			armToPos(Constants.retractedArmEncoderPosition);
			
			//Set RobotStates
			RobotStates.armPos = ArmPos.RETRACTED;
			break;

		case SWITCH:
	
			//If DebugMode, print info
			if(RobotStates.debugMode) {
				DriverStation.reportWarning("Running Arm Macro: Switch", false);
			}
			
			//Actuate the Motor
			armToPos(Constants.retractedArmEncoderPosition);
			
			//Set RobotStates
			RobotStates.armPos = ArmPos.RETRACTED;
			break;
			
		default:
			break;

		}
	
	}
	
	public void getFinishedState() {
		
		if(arm.getClosedLoopError(Constants.armData.pidLoopIDx) < Constants.armData.allowedError || 
				arm.getClosedLoopError(Constants.armData.pidLoopIDx) > -Constants.armData.allowedError) {
			RobotStates.armInPosition = true;
		}
		
		RobotStates.armInPosition = false;
	
	}
	
	public void zeroSensors() {
		arm.setSelectedSensorPosition(0, Constants.armData.slotIDx, Constants.armData.timeoutMs);
	}
	
	public void stop() {
		arm.set(ControlMode.PercentOutput, 0);
	}

}
