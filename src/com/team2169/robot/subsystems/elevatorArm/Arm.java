package com.team2169.robot.subsystems.elevatorArm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team2169.robot.ActuatorMap;
import com.team2169.robot.Constants;
import com.team2169.robot.ControlMap;
import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.RobotStates.ArmPos;
import com.team2169.robot.RobotWantedStates.WantedArmPos;
import com.team2169.util.Converter;
import com.team2169.util.TalonMaker;

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

		if(RobotWantedStates.wantedArmPos == WantedArmPos.OVERRIDE) {
			armOverrideLooper(ControlMap.getOperatorStickValue());
			
			//Set RobotStates
			RobotStates.armPos = ArmPos.OVERRIDE;
		
		}
		
		else if(RobotWantedStates.wantedArmPos == WantedArmPos.HOLD_POSITION) {
		
			armOverrideLooper(0);
			
			RobotStates.armPos = ArmPos.HOLD_POSITION;
			
		}
		
		else {

			//set robot's actual state to WantedState's value
			switch(RobotWantedStates.wantedElevatorPos){

				case GROUND: default:
					
					//CANCycle for Ground Position
					
					//Actuate the Motor
					armToPos(Constants.extendedArmEncoderPosition);
					
					//Set RobotStates
					RobotStates.armPos = ArmPos.EXTENDED;
					break;
					
				case HANG:
				
					//CANCycle Hang Position
					
					//Actuate the Motor
					armToPos(Constants.retractedArmEncoderPosition);
					
					//Set RobotStates
					RobotStates.armPos = ArmPos.RETRACTED;
					break;
				
				case SCALE_HIGH:
					
					//CANCycle for (High) Position
					
					//Actuate the Motor
					armToPos(Constants.retractedArmEncoderPosition);
					
					//Set RobotStates
					RobotStates.armPos = ArmPos.RETRACTED;
					break;
					
				case SCALE_MID:
					
					//CANCycle for Scale (Mid) Position
		
					//Actuate the Motor
					armToPos(Constants.retractedArmEncoderPosition);
					
					//Set RobotStates
					RobotStates.armPos = ArmPos.RETRACTED;
					break;
					
				case SCALE_LOW:
					
					//CANCycle for Scale (Low) Position
					
					//Actuate the Motor
					armToPos(Constants.retractedArmEncoderPosition);
					
					//Set RobotStates
					RobotStates.armPos = ArmPos.RETRACTED;
					break;
		
				case SWITCH:
					
					//CANCycle for Switch Position
					
					//Actuate the Motor
					armToPos(Constants.retractedArmEncoderPosition);
					
					//Set RobotStates
					RobotStates.armPos = ArmPos.RETRACTED;
					break;
				}
	
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
