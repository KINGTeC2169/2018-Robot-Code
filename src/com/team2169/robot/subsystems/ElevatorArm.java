package com.team2169.robot.subsystems;

import com.team2169.robot.Constants;
import com.team2169.robot.RobotStates;
import com.team2169.robot.subsystems.elevatorArm.Arm;
import com.team2169.robot.subsystems.elevatorArm.Elevator;
import com.team2169.util.DebugPrinter;

public class ElevatorArm extends Subsystem {

	private Arm arm;
	private Elevator elevator;

	private static ElevatorArm eInstance = null;

	public static ElevatorArm getInstance() {
		if (eInstance == null) {
			eInstance = new ElevatorArm();
		}
		return eInstance;
	}


	public ElevatorArm() {

		arm = new Arm();
		elevator = new Elevator();

		RobotStates.elevatorInPosition = false;

		// Calulcate Latest Macro Positions
		Constants.calculateMacros();

	}

	public void elevatorArmHandler() {

		// Arm Handler
		arm.armMacroLooper();

		// Elevator Handler
		elevator.elevatorMacroLooper();

		pushToDashboard();

	}

	@Override
	public void pushToDashboard() {

		arm.pushEncPos();
		
		if (RobotStates.debugMode) {
			DebugPrinter.armDebug();
			DebugPrinter.elevatorDebug();
		}

	}

	@Override
	public void zeroSensors() {

		elevator.zeroSensors();
		arm.zeroSensors();

	}

	@Override
	public void stop() {

		elevator.stop();
		arm.stop();

	}

}
