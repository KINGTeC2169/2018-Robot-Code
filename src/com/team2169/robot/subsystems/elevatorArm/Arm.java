package com.team2169.robot.subsystems.elevatorArm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team2169.robot.ActuatorMap;
import com.team2169.robot.Constants;
import com.team2169.robot.ControlMap;
import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.RobotStates.ArmPos;
import com.team2169.util.TalonMaker;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Arm {

	// Create Talons
	private TalonSRX arm;
	int oldArmPos = 0;
	private int position = Constants.retractedArmEncoderPosition;

	public Arm() {

		// Define Lift Talons
		arm = new TalonSRX(ActuatorMap.armID);

		// Pull Constants Data for Arm
		Constants.setArmDataFromConstants();
		/*arm.configContinuousCurrentLimit(40, 10);
		arm.configPeakCurrentLimit(30, Constants.armData.timeoutMs);
		arm.configAllowableClosedloopError(Constants.armData.slotIDx, Constants.armData.allowedError, Constants.armData.timeoutMs);
		arm.configPeakCurrentLimit(35, Constants.armData.timeoutMs);
		arm.configPeakCurrentDuration(500, Constants.armData.timeoutMs);
		*/

		// Apply Talon Settings
		arm = TalonMaker.prepTalonForMotionProfiling(arm, Constants.armData);

		
	}

	public void pushToDashboard() {
		SmartDashboard.putNumber("Arm Enc", arm.getSelectedSensorPosition(Constants.armData.slotIDx));
		SmartDashboard.putNumber("Arm_Current", arm.getOutputCurrent());
		SmartDashboard.putNumber("pulseWidth Pos", arm.getSensorCollection().getPulseWidthPosition());
	}

	private void armToPos(int pos) {
		arm.set(ControlMode.Position, pos);
		position = arm.getSelectedSensorPosition(Constants.armData.slotIDx);
		getFinishedState();
	}

	private void armSetOverrideLooper(double joystickValue) {
		arm.set(ControlMode.PercentOutput, joystickValue);
		position = arm.getSelectedSensorPosition(Constants.armData.slotIDx);
	}

	private void holdInPosition() {
		arm.set(ControlMode.Position, position);
	}

	public void armMacroLooper() {
	
		//enc.inputValue(arm.getSensorCollection().getPulseWidthPosition());
		System.out.println(arm.getSensorCollection().getPulseWidthPosition());
		//SmartDashboard.putNumber("Arm Absolute Encoder: ", enc.getLatestValue());
		
		if(ControlMap.getArmZero()) {
			RobotWantedStates.wantedArmPos = ArmPos.EXTENDED;
			arm.setSelectedSensorPosition(0, 0, 10);
		}
		
		if(arm.getSelectedSensorPosition(0) + 200 < oldArmPos || arm.getSelectedSensorPosition(0) - 200 > oldArmPos) {
			arm.setSelectedSensorPosition(oldArmPos, 0, 10);
		}
		oldArmPos = arm.getSelectedSensorPosition(0);
		
		// set robot's actual state to WantedState's value
		switch (RobotWantedStates.wantedArmPos) {
		case EXTENDED:
			armToPos(Constants.extendedArmEncoderPosition);
			RobotStates.armPos = ArmPos.EXTENDED;
			break;
		case HOLD_POSITION:
			holdInPosition();
			RobotStates.armPos = ArmPos.HOLD_POSITION;
			break;
		case OVERRIDE:
		default:
			armSetOverrideLooper(ControlMap.getOperatorStickValue());

			// Set RobotStates
			RobotStates.armPos = ArmPos.OVERRIDE;
			break;
		case RETRACTED:
			armToPos(Constants.retractedArmEncoderPosition);
			RobotStates.armPos = ArmPos.RETRACTED;
			break;
		case CONFIG:
			armSetOverrideLooper(-0.3);
			if(arm.getOutputCurrent() >= 5){
				arm.setSelectedSensorPosition(0, Constants.armData.pidLoopIDx, Constants.armData.timeoutMs);
				RobotWantedStates.wantedArmPos = ArmPos.HOLD_POSITION;
			}
			RobotStates.armPos = ArmPos.CONFIG;
			break;

		}

	}

	public void getFinishedState() {

		if (arm.getClosedLoopError(Constants.armData.pidLoopIDx) < Constants.armData.allowedError
				|| arm.getClosedLoopError(Constants.armData.pidLoopIDx) > -Constants.armData.allowedError) {
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
