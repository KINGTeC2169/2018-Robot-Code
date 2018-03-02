package com.team2169.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team2169.robot.ActuatorMap;
import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.RobotStates.IntakeClamp;
import com.team2169.robot.RobotStates.IntakeMode;
import com.team2169.util.DebugPrinter;
import edu.wpi.first.wpilibj.Ultrasonic;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import com.team2169.robot.Constants;

public class Intake extends Subsystem {

	private static Intake iInstance = null;

	public static Intake getInstance() {
		if (iInstance == null) {
			iInstance = new Intake();
		}
		return iInstance;
	}

	// private int blockPastPositions[];
	Ultrasonic ultra;
	private TalonSRX left;
	private TalonSRX right;
	DoubleSolenoid dropSolenoid;
	DoubleSolenoid clampSolenoid;

	public Intake() {
		// blockPastPositions = new int[20];
		ultra = new Ultrasonic(ActuatorMap.intakeUltrasonicOutputPort, ActuatorMap.intakeUltrasonicInputPort);
		left = new TalonSRX(ActuatorMap.leftIntakeID);
		right = new TalonSRX(ActuatorMap.rightIntakeID);
		right.setInverted(true);
		dropSolenoid = new DoubleSolenoid(ActuatorMap.PCMPort, ActuatorMap.dropPortForward,
				ActuatorMap.dropPortReverse);
		clampSolenoid = new DoubleSolenoid(ActuatorMap.PCMPort, ActuatorMap.clampPortForward,
				ActuatorMap.clampPortReverse);
		RobotWantedStates.wantedIntakeClamp = IntakeClamp.CLAMP;
		ultra.setAutomaticMode(true);
	}

	public void intakeManual(double power) {
		left.set(ControlMode.PercentOutput, power);
		right.set(ControlMode.PercentOutput, power);
	}

	public double getBlockDistance() {
		return ultra.getRangeInches();
	}
	/*
	 * public void storeBlockHistory(boolean currentBlockPosition){ for(int i = 19;
	 * i > 0; i--){ blockPastPositions[i] = blockPastPositions[i-1]; }
	 * blockPastPositions[0] = null; }
	 */

	public void intakeHandler() {
		RobotStates.ultraWithinRange = (getBlockDistance() <= Constants.maxUltraTriggerDistance
				&& getBlockDistance() >= Constants.minUltraTriggerDistance);

		if (RobotStates.operatorWantsUltrasonic && RobotStates.ultraWithinRange) {
			RobotWantedStates.wantedIntakeClamp = IntakeClamp.CLAMP;
		}

		// Handle Intake State
		switch (RobotWantedStates.wantedIntakeMode) {

		case IDLE:
		default:

			// Stop Intakes
			intakeManual(0);

			RobotStates.intakeMode = IntakeMode.IDLE;

			break;

		case INTAKE:

			// Run Intakes

			intakeManual(-Constants.intakeSpeed);

			RobotStates.intakeMode = IntakeMode.INTAKE;
			break;

		case EXHAUST:

			// Run Intakes Backwards
			intakeManual(Constants.intakeSpeed);
			RobotStates.intakeMode = IntakeMode.EXHAUST;
			break;

		}

		// Handle Wanted Clamp State
		switch (RobotWantedStates.wantedIntakeClamp) {

		case NEUTRAL:
		default:

			// Set Clamp to Neutral
			clampSolenoid.set(Value.kReverse);
			dropSolenoid.set(Value.kReverse);
			RobotStates.intakeClamp = IntakeClamp.NEUTRAL;
			break;

		case CLAMP:

			// Set Clamp to Clamped
			clampSolenoid.set(Value.kForward);
			dropSolenoid.set(Value.kReverse);
			RobotStates.intakeClamp = IntakeClamp.CLAMP;
			break;

		case DROP:

			// Set Clamp to Drop
			clampSolenoid.set(Value.kReverse);
			dropSolenoid.set(Value.kForward);
			RobotStates.intakeClamp = IntakeClamp.DROP;
			break;

		}

		pushToDashboard();

	}

	@Override
	public void pushToDashboard() {

		if (RobotStates.debugMode) {
			DebugPrinter.intakeDebug();
		}

	}

	@Override
	public void zeroSensors() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}
