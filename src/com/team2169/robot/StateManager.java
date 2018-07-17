package com.team2169.robot;

import com.team2169.robot.ControlMap.OperatorStickState;
import com.team2169.robot.RobotStates.*;
import com.team2169.robot.canCycles.CANCycleHandler;
import com.team2169.util.FlyByWireHandler;

public class StateManager {

	public static void stateInit() {
		setWantedMacro(Macro.PASS);
	}

	static void teleOpStateLooper() {
		ControlMap.getOperatorStickState();
		getWantedDriveOverride();
		getWantedShiftState();
		getWantedArmState();
		getWantedIntakeState();
		getWantedElevatorState();
	}

	// Local MacroSetter
	private static void setWantedMacro(Macro pos) {
		RobotWantedStates.wantedMacro = pos;
		CANCycleHandler.cancelArmElevatorCycles();
		RobotStates.elevatorOverrideMode = false;
		RobotStates.intakeClampOverride = false;
	}

	// Wanted Driver Override Handler
	private static void getWantedDriveOverride() {

		
		
	}

	// Wanted Driver Shift Handler
	private static void getWantedShiftState() {
		
		if(RobotStates.driveTrainOverride) {
			if(ControlMap.shiftUp()) {
				RobotWantedStates.wantedDriveMode = DriveMode.HIGH;
			}
			else if(ControlMap.shiftDown()) {
				RobotWantedStates.wantedDriveMode = DriveMode.LOW;
			}
		}
		else {
			if(ControlMap.shiftUp() && FlyByWireHandler.determineShiftSafety(DriveMode.HIGH)) {
				RobotWantedStates.wantedDriveMode = DriveMode.HIGH;
			}
			else if(ControlMap.shiftDown() && FlyByWireHandler.determineShiftSafety(DriveMode.LOW)) {
				RobotWantedStates.wantedDriveMode = DriveMode.LOW;
			}
		}

	}

	// Wanted Operator Intake Handler
	private static void getWantedIntakeState() {

		RobotWantedStates.wantedIntakeMode = IntakeMode.MANUAL;

		if (ControlMap.clampButtonPressed()) {
			CANCycleHandler.cancelArmElevatorCycles();
			RobotWantedStates.wantedIntakeClamp = IntakeClamp.CLAMP;
			RobotStates.intakeClampOverride = true;
		} else if (ControlMap.openButtonPressed()) {
			CANCycleHandler.cancelArmElevatorCycles();
			RobotWantedStates.wantedIntakeClamp = IntakeClamp.OPEN;
			RobotStates.intakeClampOverride = true;
		}

		if (!RobotStates.intakeClampOverride && !RobotStates.canCycleMode) {
			switch (RobotWantedStates.wantedMacro) {
			case SCALE_HIGH: case SCALE_MID: case SCALE_LOW: case SWITCH:
			default:
				
				RobotWantedStates.wantedIntakeClamp = IntakeClamp.CLAMP;
				break;

			}
		}
	}

	// Wanted Operator Elevator Handler
	private static void getWantedElevatorState() {

		// Driver has taken control of mechanism, follow their controls.
		if (ControlMap.operatorStickState == OperatorStickState.ELEVATOR) {
			CANCycleHandler.cancelArmElevatorCycles();
			RobotStates.elevatorOverrideMode = true;
			RobotWantedStates.wantedElevatorPos = Macro.OVERRIDE;
		} else if (RobotStates.elevatorOverrideMode) {
			RobotWantedStates.wantedElevatorPos = Macro.HOLD_POSITION;
		}

		// Robot is in a CanCycle, don't interfere unless overriden
		if (!RobotStates.canCycleMode && !RobotStates.elevatorOverrideMode) {
			RobotWantedStates.wantedElevatorPos = RobotWantedStates.wantedMacro;
		}

	}

	// Wanted Operator Arm Handler
	private static void getWantedArmState() {

		// Intake Wheel States
		// Driver has taken control of mechanism, follow their controls.
		if (ControlMap.operatorStickState == OperatorStickState.ARM) {
			RobotStates.armOverride= true;
			CANCycleHandler.cancelArmElevatorCycles();
			RobotWantedStates.wantedArmPos = ArmPos.OVERRIDE;
		}

		else if (ControlMap.extendArm()) {
			RobotStates.armOverride= false;
			CANCycleHandler.cancelArmElevatorCycles();
			RobotWantedStates.wantedArmPos = ArmPos.EXTEND;
		}

		else if (ControlMap.retractArm()) {
			RobotStates.armOverride= false;
			CANCycleHandler.cancelArmElevatorCycles();
			RobotWantedStates.wantedArmPos = ArmPos.RETRACT;
		}

		//Robot is in stick mode, and not a button mode
		else if (RobotStates.armOverride) {
			RobotWantedStates.wantedArmPos = ArmPos.HOLD_POSITION;
		}
	}
}
