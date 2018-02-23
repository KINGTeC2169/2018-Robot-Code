package com.team2169.robot.subsystems.elevatorArm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team2169.robot.ActuatorMap;
import com.team2169.robot.Constants;
import com.team2169.robot.ControlMap;
import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotWantedStates;
import com.team2169.util.TalonMaker;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.team2169.robot.RobotStates.ElevatorPos;

public class Elevator {

	// Create Talons
	private TalonSRX elevator;
	private TalonSRX elevatorSlave;
	//private AnalogInput topLimit;
	//private AnalogInput bottomLimit;
	
	public Elevator() {

		// Define Lift Talons
		elevator = new TalonSRX(ActuatorMap.elevatorMasterID);
		elevatorSlave = new TalonSRX(ActuatorMap.elevatorSlaveID);
		elevatorSlave.set(ControlMode.Follower, ActuatorMap.elevatorMasterID);

		//topLimit = new AnalogInput(ActuatorMap.elevatorTopLimitID);
		//bottomLimit = new AnalogInput(ActuatorMap.elevatorBottomLimitID);
		
		// Pull Constants Data for Elevator
		Constants.setElevatorDataFromConstants();
		

		// Apply Talon Settings
		elevator = TalonMaker.prepTalonForMotionProfiling(elevator, Constants.elevatorData);
	}

	public void elevatorMacroLooper() {

		//getLimits();
		// set robot's actual state to WantedState's value
		switch (RobotWantedStates.wantedElevatorPos) {

		case OVERRIDE:
		default:
			elevatorManual(ControlMap.getOperatorStickValue());

			// Set RobotStates
			RobotStates.elevatorPos = ElevatorPos.OVERRIDE;
			break;

		case HOLD_POSITION:

			elevatorOverrideLooper(0);

			// Set RobotStates
			RobotStates.elevatorPos = ElevatorPos.HOLD_POSITION;
			break;

		case GROUND:

			// CANCycle for Ground Position

			// Actuate the Motor
			elevatorToPos(Constants.groundElevatorEncoderPosition);

			// Set RobotStates
			RobotStates.elevatorPos = ElevatorPos.GROUND;
			break;

		case HANG:

			// CANCycle Hang Position

			// Actuate the Motor
			elevatorToPos(Constants.hangElevatorEncoderPosition);

			// Set RobotStates
			RobotStates.elevatorPos = ElevatorPos.HANG;
			break;

		case SCALE_HIGH:

			// CANCycle for (High) Position

			// Actuate the Motor
			elevatorToPos(Constants.scaleHighElevatorEncoderPosition);

			// Set Robot States
			RobotStates.elevatorPos = ElevatorPos.SCALE_HIGH;
			break;

		case SCALE_MID:

			// CANCycle for Scale (Mid) Position

			// Actuate the Motor
			elevatorToPos(Constants.scaleMidElevatorEncoderPosition);

			// Set Robot States
			RobotStates.elevatorPos = ElevatorPos.SCALE_MID;
			break;

		case SCALE_LOW:

			// CANCycle for Scale (Low) Position

			// Actuate the Motor
			elevatorToPos(Constants.scaleLowElevatorEncoderPosition);

			// Set Robot States
			RobotStates.elevatorPos = ElevatorPos.SCALE_LOW;
			break;

		case SWITCH:

			// CANCycle for Switch

			// Actuate the Motor
			elevatorToPos(10000/*Constants.scaleMidElevatorEncoderPosition*/);

			// Set Robot States
			RobotStates.elevatorPos = ElevatorPos.SWITCH;
			break;

		}
		
		SmartDashboard.putNumber("Elevator Setpoint", elevator.getSelectedSensorPosition(Constants.elevatorData.slotIDx));

	}

	private void elevatorManual(double power) {
		elevator.set(ControlMode.PercentOutput, power);
	}
	
	private void elevatorToPos(int pos) {
		SmartDashboard.putNumber("Elevator Setpoint", elevator.getSelectedSensorPosition(Constants.elevatorData.slotIDx));
		elevator.set(ControlMode.Position, pos);
		getElevatorFinishedState();
	}

	public void elevatorOverrideLooper(double joystickValue) {
			
		elevator.set(ControlMode.PercentOutput, joystickValue);
		
	}
	
	/*void getLimits() {
		
		//Upper Limit Switch Active
		if (topLimit.getValue() > Constants.elevatorUpperLimit) {
			elevator.configPeakOutputReverse(0, Constants.elevatorData.timeoutMs);
		}
		else {
			elevator.configPeakOutputReverse(-1, Constants.elevatorData.timeoutMs);
		}
		
		//Lower Limit Switch Active
		if(bottomLimit.getValue() > Constants.elevatorLowerLimit) {
			elevator.configPeakOutputForward(0, Constants.elevatorData.timeoutMs);
		}
		
		else {
			elevator.configPeakOutputReverse(1, Constants.elevatorData.timeoutMs);
		}
	}
*/
	public void getElevatorFinishedState() {

		if (elevator.getClosedLoopError(Constants.elevatorData.pidLoopIDx) < Constants.elevatorData.allowedError
				|| elevator
						.getClosedLoopError(Constants.elevatorData.pidLoopIDx) > -Constants.elevatorData.allowedError) {
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
