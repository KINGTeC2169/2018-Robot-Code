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
	private int position = Constants.retractedArmEncoderPosition;
	
	public Arm() {

		// Define Lift Talons
		arm = new TalonSRX(ActuatorMap.armID);

		// Pull Constants Data for Arm
		Constants.setArmDataFromConstants();

		// Apply Talon Settings
		arm = TalonMaker.prepTalonForMotionProfiling(arm, Constants.armData);

	}

	public void pushToDashboard() {
		SmartDashboard.putNumber("Arm Enc", arm.getSelectedSensorPosition(Constants.armData.slotIDx));
		SmartDashboard.putNumber("ARM_CURRENT", arm.getOutputCurrent());
	}
	
	private void armToPos(int pos) {
		arm.set(ControlMode.Position, pos);
		position = arm.getSelectedSensorPosition(Constants.armData.slotIDx);
		getFinishedState();
	}
	
	private void armSetOverrideLooper(double joystickValue){
		arm.set(ControlMode.PercentOutput, joystickValue);
		position = arm.getSelectedSensorPosition(Constants.armData.slotIDx);
	}
	
	private void holdInPosition() {
		arm.set(ControlMode.Position, position);
	}

	public void armMacroLooper() {
		
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
