package com.team2169.robot.subsystems.elevatorArm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team2169.robot.ActuatorMap;
import com.team2169.robot.Constants;
import com.team2169.robot.ControlMap;
import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotWantedStates;
import com.team2169.util.TalonMaker;

import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.team2169.robot.RobotStates.Macro;

@SuppressWarnings("deprecation")
public class Elevator {

	// Create Talons
	private TalonSRX elevator;
	private TalonSRX elevatorSlave;
	private int position;
	//private AnalogInput topLimit;
	//private AnalogInput bottomLimit;

	NetworkTable table = NetworkTable.getTable("SmartDashboard");

	public Elevator() {

		// Define Lift Talons
		elevator = new TalonSRX(ActuatorMap.elevatorMasterID);
		elevatorSlave = new TalonSRX(ActuatorMap.elevatorSlaveID);
		elevatorSlave.set(ControlMode.Follower, ActuatorMap.elevatorMasterID);
		elevator.configPeakCurrentLimit(30, Constants.elevatorData.timeoutMs);
		elevatorSlave.configPeakCurrentLimit(30, Constants.elevatorData.timeoutMs);
		elevator.configAllowableClosedloopError(Constants.elevatorData.slotIDx, Constants.elevatorData.allowedError, Constants.elevatorData.timeoutMs);
		elevatorSlave.configAllowableClosedloopError(Constants.elevatorData.slotIDx, Constants.elevatorData.allowedError, Constants.elevatorData.timeoutMs);
		elevator.configPeakCurrentLimit(35, Constants.elevatorData.timeoutMs);
		elevatorSlave.configPeakCurrentLimit(35, Constants.elevatorData.timeoutMs);
		elevator.configPeakCurrentDuration(500, Constants.elevatorData.timeoutMs);
		elevatorSlave.configPeakCurrentDuration(500, Constants.elevatorData.timeoutMs);
		
		elevator.configClosedloopRamp(.25, 10);
		elevatorSlave.configClosedloopRamp(.25, 10);
		
		//topLimit = new AnalogInput(ActuatorMap.elevatorTopLimitID);
		//bottomLimit = new AnalogInput(ActuatorMap.elevatorBottomLimitID);

		// Pull Constants Data for Elevator
		Constants.setElevatorDataFromConstants();

		// Apply Talon Settings
		elevator = TalonMaker.prepTalonForMotionProfiling(elevator, Constants.elevatorData);
	}

	public void elevatorMacroLooper() {
		
		
		//SmartDashboard.putNumber("topLimit", topLimit.getValue());
		//SmartDashboard.putNumber("bottomLimit", bottomLimit.getValue());
		// getLimits();
		// set robot's actual state to WantedState's value
		switch (RobotWantedStates.wantedElevatorPos) {

		case OVERRIDE:
		default:
			elevatorManual(ControlMap.getOperatorStickValue());

			// Set RobotStates
			RobotStates.elevatorPos = Macro.OVERRIDE;
			break;

		case HOLD_POSITION:

			holdInPosition();

			// Set RobotStates
			RobotStates.elevatorPos = Macro.HOLD_POSITION;
			break;

		case GROUND:

			// CANCycle for Ground Position

			// Actuate the Motor
			elevatorToPos(Constants.groundElevatorEncoderPosition);

			// Set RobotStates
			RobotStates.elevatorPos = Macro.GROUND;
			break;

		case HANG:

			// CANCycle Hang Position

			// Actuate the Motor
			elevatorToPos(Constants.hangElevatorEncoderPosition);

			// Set RobotStates
			RobotStates.elevatorPos = Macro.HANG;
			break;

		case SCALE_HIGH:

			// CANCycle for (High) Position

			// Actuate the Motor
			elevatorToPos(Constants.scaleHighElevatorEncoderPosition);

			// Set Robot States
			RobotStates.elevatorPos = Macro.SCALE_HIGH;
			break;

		case SCALE_MID:

			// CANCycle for Scale (Mid) Position

			// Actuate the Motor
			elevatorToPos(Constants.scaleMidElevatorEncoderPosition);

			// Set Robot States
			RobotStates.elevatorPos = Macro.SCALE_MID;
			break;

		case SCALE_LOW:

			// CANCycle for Scale (Low) Position

			// Actuate the Motor
			elevatorToPos(Constants.scaleLowElevatorEncoderPosition);

			// Set Robot States
			RobotStates.elevatorPos = Macro.SCALE_LOW;
			break;

		case SWITCH:

			// CANCycle for Switch

			// Actuate the Motor
			elevatorToPos(Constants.switchElevatorEncoderPosition);

			// Set Robot States
			RobotStates.elevatorPos = Macro.SWITCH;
			break;

		}
		SmartDashboard.putNumber("output to motor", elevator.getMotorOutputPercent());
		SmartDashboard.putNumber("Elevator Error", elevator.getClosedLoopError(0));

		SmartDashboard.putNumber("Elevator Setpoint",
				elevator.getSelectedSensorPosition(Constants.elevatorData.slotIDx));

	}

	private void elevatorManual(double power) {
		elevator.set(ControlMode.PercentOutput, power);
	}

	private void elevatorToPos(int pos) {
		elevator.set(ControlMode.Position, pos);
		position = elevator.getSelectedSensorPosition(Constants.elevatorData.slotIDx);
		SmartDashboard.putNumber("Elevator Setpoint", position);
		getElevatorFinishedState();
	}

	public void holdInPosition() {
		//elevator.set(ControlMode.Position, position);
		elevator.set(ControlMode.PercentOutput, 0);
	
	}

	/*
	 * void getLimits() {
	 * 
	 * //Upper Limit Switch Active if (topLimit.getValue() >
	 * Constants.elevatorUpperLimit) { elevator.configPeakOutputReverse(0,
	 * Constants.elevatorData.timeoutMs); } else {
	 * elevator.configPeakOutputReverse(-1, Constants.elevatorData.timeoutMs); }
	 * 
	 * //Lower Limit Switch Active if(bottomLimit.getValue() >
	 * Constants.elevatorLowerLimit) { elevator.configPeakOutputForward(0,
	 * Constants.elevatorData.timeoutMs); }
	 * 
	 * else { elevator.configPeakOutputReverse(1, Constants.elevatorData.timeoutMs);
	 * } }
	 */
	
	void setPID() {
		elevator.config_kP(0, table.getNumber("p", 0), 10);
		elevator.config_kI(0, table.getNumber("i", 0), 10);
		elevator.config_kD(0, table.getNumber("f", 0), 10);
		elevator.config_kF(0, table.getNumber("p", 0), 10);
	}
	
	private void getElevatorFinishedState() {

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
