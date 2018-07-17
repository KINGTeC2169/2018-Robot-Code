package com.team2169.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team2169.robot.*;
import com.team2169.robot.RobotStates.ElevatorDirection;
import com.team2169.robot.RobotStates.Macro;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.util.Converter;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator {

	// Create Talons
	private TalonSRX elevator;
	private DigitalInput topLimit;
	private DigitalInput bottomLimit;
	private int heightPos;

	public Elevator() {

		// Define Lift Talons
		elevator = new TalonSRX(ActuatorMap.elevatorMasterID);
		TalonSRX elevatorSlave = new TalonSRX(ActuatorMap.elevatorSlaveID);
		
		//Configure Talons
		elevator = prepElevatorTalon(elevator);
		elevatorSlave = prepElevatorTalon(elevatorSlave);
		elevatorSlave.set(ControlMode.Follower, ActuatorMap.elevatorMasterID);

		//Create Limit Switches
		topLimit = new DigitalInput(ActuatorMap.elevatorTopLimitID);
		bottomLimit = new DigitalInput(ActuatorMap.elevatorBottomLimitID);
		
		//Set Initial States
		RobotStates.elevatorDirection = ElevatorDirection.UP;
		setPID(ElevatorDirection.UP);
		

	}

	public void elevatorMacroLooper() {
		
		getLimits();
		
		switch (RobotWantedStates.wantedElevatorPos) {
		case OVERRIDE:
		default:
			//Follow Driver's Manual Controls
			elevatorManual(ControlMap.getOperatorStickValue());
			// Set RobotStates
			RobotStates.elevatorPos = Macro.OVERRIDE;
			break;
		case HOLD_POSITION:
			// Hold Elevator In Position
			holdInPosition();
			// Set RobotStates
			RobotStates.elevatorPos = Macro.HOLD_POSITION;
			break;
		case GROUND:
			// Move Elevator To Position
			setPIDPos(Macro.GROUND);
			elevatorToPos(Constants.groundElevatorEncoderPosition);
			// Set RobotStates
			RobotStates.elevatorPos = Macro.GROUND;
			break;
		case SCALE_HIGH:
			// Move Elevator To Position
			setPIDPos(Macro.SCALE_HIGH);
			elevatorToPos(Constants.scaleHighElevatorEncoderPosition);
			// Set Robot States
			RobotStates.elevatorPos = Macro.SCALE_HIGH;
			break;
		case SCALE_MID:
			// Move Elevator To Position
			setPIDPos(Macro.SCALE_MID);
			elevatorToPos(Constants.scaleMidElevatorEncoderPosition);
			// Set Robot States
			RobotStates.elevatorPos = Macro.SCALE_MID;
			break;
		case SCALE_LOW:
			// Move Elevator To Position
			setPIDPos(Macro.SCALE_LOW);
			elevatorToPos(Constants.scaleLowElevatorEncoderPosition);
			// Set Robot States
			RobotStates.elevatorPos = Macro.SCALE_LOW;
			break;
		case SWITCH:
			// Move Elevator To Position
			setPIDPos(Macro.SWITCH);
			elevatorToPos(Constants.switchElevatorEncoderPosition);
			// Set Robot States
			RobotStates.elevatorPos = Macro.SWITCH;
			break;
		case PASS:
			break;

		}
	}

	//Variable PID Setter
	private void setPIDPos(Macro macro) {
		// Going Down
		
		int pos = Converter.macroToInt(macro);
		
		if (heightPos > pos) {
			Constants.setElevatorDataFromConstants();
			setPID(ElevatorDirection.DOWN);
		}
		// Going Up
		else if (heightPos < pos) {
			Constants.setElevatorDataFromConstants();
			setPID(ElevatorDirection.UP);
		}
		
		heightPos = pos;
	}
	
	//Manual Elevator Movement Handler
	private void elevatorManual(double power) {
		elevator.set(ControlMode.PercentOutput, -power);

	}

	//Elevator Encoder Reset
	private void resetElevatorPosition() {
		elevator.setSelectedSensorPosition(0, 0, Constants.elevatorUpData.timeoutMs);
	}

	//Elevator Position Setter
	private void elevatorToPos(int pos) {
		elevator.set(ControlMode.Position, pos);
	}

	//Elevator Position Holder
	private void holdInPosition() {
		elevator.set(ControlMode.PercentOutput, 0);
	}

	//Elevator Limit Handler
	private void getLimits() {

		if (ControlMap.resetElevator()) {
			resetElevatorPosition();
		}
		
		// Upper Limit Switch Active
		if (!bottomLimit.get()) {
			resetElevatorPosition();
		} else if (RobotStates.runningMode == RunningMode.AUTO) {
			elevator.configPeakOutputReverse(-.7, Constants.elevatorUpData.timeoutMs);
		} else {
			elevator.configPeakOutputReverse(-1, Constants.elevatorUpData.timeoutMs);
		}

		// Lower Limit Switch Active
		if (!topLimit.get()) {
		} else if (RobotStates.runningMode == RunningMode.AUTO) {
			elevator.configPeakOutputForward(1, Constants.elevatorUpData.timeoutMs);
		} else {
			elevator.configPeakOutputForward(1, Constants.elevatorUpData.timeoutMs);
		}
	}

	//Elevator PID Setter
	private void setPID(ElevatorDirection direction) {

		// Going Down
		if (direction == ElevatorDirection.DOWN) {
			elevator.config_kP(0, Constants.elevatorDownData.p, 10);
			elevator.config_kI(0, Constants.elevatorDownData.i, 10);
			elevator.config_kD(0, Constants.elevatorDownData.d, 10);
			elevator.config_kF(0, Constants.elevatorDownData.f, 10);
			RobotStates.elevatorDirection = ElevatorDirection.DOWN;
		}

		// Going Up
		else if (direction == ElevatorDirection.UP) {
			elevator.config_kP(0, Constants.elevatorUpData.p, 10);
			elevator.config_kI(0, Constants.elevatorUpData.i, 10);
			elevator.config_kD(0, Constants.elevatorUpData.d, 10);
			elevator.config_kF(0, Constants.elevatorUpData.f, 10);
			RobotStates.elevatorDirection = ElevatorDirection.UP;
		}
	}
	
	//Elevator Zeroing
	public void zeroSensors() {
		elevator.setSelectedSensorPosition(0, Constants.elevatorUpData.slotIDx, Constants.elevatorUpData.timeoutMs);
	}

	//Stop Elevator
	public void stop() {
		elevator.set(ControlMode.PercentOutput, 0);
	}
	
	public void pushToDashboard() {
		
		SmartDashboard.putBoolean("Top Limit", topLimit.get());
		SmartDashboard.putBoolean("Bottom Limit", bottomLimit.get());
		SmartDashboard.putString("Elevator State", RobotWantedStates.wantedElevatorPos.name());
		SmartDashboard.putString("Elevator Direction", RobotStates.elevatorDirection.name());
		SmartDashboard.putNumber("Elevator Error", elevator.getClosedLoopError(0));
		SmartDashboard.putNumber("Elevator Setpoint", elevator.getSelectedSensorPosition(Constants.elevatorUpData.slotIDx));
		SmartDashboard.putNumber("Elevator Output", elevator.getMotorOutputVoltage());

	}

	public TalonSRX prepElevatorTalon(TalonSRX talon) {
		
		talon.configPeakCurrentLimit(30, Constants.elevatorUpData.timeoutMs);
		talon.configAllowableClosedloopError(Constants.elevatorUpData.slotIDx, Constants.elevatorUpData.allowedError,
				Constants.elevatorUpData.timeoutMs);
		talon.configPeakCurrentLimit(35, Constants.elevatorUpData.timeoutMs);
		talon.configPeakCurrentDuration(500, Constants.elevatorUpData.timeoutMs);
		talon.setSensorPhase(false);
		talon.configClosedloopRamp(.5, 10);
		talon.configOpenloopRamp(Constants.elevatorRampRate, 10);
		return talon;
		
	}
}
