package com.team2169.robot;

import com.team2169.robot.ControlMap.OperatorStickState;
import com.team2169.robot.RobotStates.*;
import com.team2169.robot.canCycles.CANCycleHandler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class StateManager {

	public static void stateInit() {
		setWantedMacro(Macro.PASS);
	}

	static void teleOpStateLooper() {
		ControlMap.getOperatorStickState();
		getWantedMacroState();
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
		RobotStates.armStickMode = false;
		RobotStates.armButtonMode = false;
		RobotStates.intakeClampOverride = false;
	}

	// MacroButton/State Getter
	private static void getWantedMacroState() {
		SmartDashboard.putBoolean("Task Running", CANCycleHandler.dropAndExhaust.isRunning());
		
		/*if (ControlMap.groundMacroPressed()) {
			setWantedMacro(Macro.GROUND);
		} else if (ControlMap.switchMacroPressed()) {
			setWantedMacro(Macro.SWITCH);
		} else if (ControlMap.scaleLowMacroPressed()) {
			setWantedMacro(Macro.SCALE_LOW);
		} else if (ControlMap.scaleMidMacroPressed()) {
			setWantedMacro(Macro.SCALE_HIGH);
		} else if (ControlMap.hangMacroPressed()) {
			setWantedMacro(Macro.HANG);
		}
		*/
	}

	// Wanted Driver Override Handler
	private static void getWantedDriveOverride() {

		if (ControlMap.ptoActivate()) {
			System.out.println("HANG MODE");
			RobotWantedStates.wantedDriveType = DriveType.WANTS_TO_HANG;
		} else if (ControlMap.ptoActivate()) {
			System.out.println("Drive MODE");
			RobotWantedStates.wantedDriveType = DriveType.WANTS_TO_DRIVE;
		}
		// Drivers Want to Drive
		else {
			// Coming from Climbing
			if (RobotWantedStates.wantedDriveType == DriveType.WANTS_TO_HANG
					|| RobotWantedStates.wantedDriveType == DriveType.HANG) {
				RobotWantedStates.wantedDriveType = DriveType.WANTS_TO_DRIVE;
			} else if (ControlMap.primaryDriverOverride()) {
				RobotWantedStates.wantedDriveType = DriveType.OVERRIDE_DRIVING;
			} else {
				RobotWantedStates.wantedDriveType = DriveType.NORMAL_DRIVING;
			}
		}

	}

	// Wanted Driver Shift Handler
	private static void getWantedShiftState() {
		if (ControlMap.shiftUp()) {
			RobotWantedStates.wantedDriveMode = DriveMode.SHIFT_TO_HIGH;
		} else if (ControlMap.shiftDown()) {
			RobotWantedStates.wantedDriveMode = DriveMode.SHIFT_TO_LOW;
		}
	}

	// Wanted Operator Intake Handler
	private static void getWantedIntakeState() {

		// Intake Wheel States
		// Driver has taken control of mechanism, follow their controls.

		RobotWantedStates.wantedIntakeMode = IntakeMode.MANUAL;

		RobotStates.operatorWantsUltrasonic = ControlMap.operatorWantsUltrasonic();

		// Intake Clamp States

		// Not in CanCycle

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
			case GROUND:
			default:
				RobotWantedStates.wantedIntakeClamp = IntakeClamp.CLAMP;
				break;
			case HANG:
				RobotWantedStates.wantedIntakeClamp = IntakeClamp.CLAMP;
				break;
			case SCALE_HIGH:
				RobotWantedStates.wantedIntakeClamp = IntakeClamp.CLAMP;
				break;
			case SCALE_LOW:
				RobotWantedStates.wantedIntakeClamp = IntakeClamp.CLAMP;
				break;
			case SCALE_MID:
				RobotWantedStates.wantedIntakeClamp = IntakeClamp.CLAMP;
				break;
			case SWITCH:
				RobotWantedStates.wantedIntakeClamp = IntakeClamp.CLAMP;
				break;

			}
		}
	}

	// Wanted Operator Elevator Handler
	private static void getWantedElevatorState() {

		// Intake Wheel States
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
			RobotStates.armStickMode = true;
			CANCycleHandler.cancelArmElevatorCycles();
			RobotWantedStates.wantedArmPos = ArmPos.OVERRIDE;
		}
		else {
			RobotWantedStates.wantedArmPos = ArmPos.HOLD_POSITION;
			RobotStates.armStickMode = false;
			CANCycleHandler.cancelArmElevatorCycles();
		}

		// Robot is in a CanCycle, don't interfere unless overriden
		/*if (!RobotStates.canCycleMode && !RobotStates.armStickMode && !RobotStates.armButtonMode) {

			// No CanCycle or Driver Override, do the default Macro action

			switch (RobotWantedStates.wantedMacro) {
			case GROUND:
			default:
				RobotWantedStates.wantedArmPos = ArmPos.EXTENDED;
				break;
			case HANG:
				RobotWantedStates.wantedArmPos = ArmPos.EXTENDED;
				break;
			case SCALE_HIGH:
				RobotWantedStates.wantedArmPos = ArmPos.EXTENDED;
				break;
			case SCALE_LOW:
				RobotWantedStates.wantedArmPos = ArmPos.EXTENDED;
				break;
			case SCALE_MID:
				RobotWantedStates.wantedArmPos = ArmPos.EXTENDED;
				break;
			case SWITCH:
				RobotWantedStates.wantedArmPos = ArmPos.EXTENDED;
				break;

			}
		}
*/
	}
}
