package com.team2169.robot;

import com.team2169.robot.ControlMap.OperatorStickState;
import com.team2169.robot.RobotWantedStates.WantedArmPos;
import com.team2169.robot.RobotWantedStates.WantedDriveMode;
import com.team2169.robot.RobotWantedStates.WantedDriveOverride;
import com.team2169.robot.RobotWantedStates.WantedIntakeClamp;
import com.team2169.robot.RobotWantedStates.WantedIntakeMode;
import com.team2169.robot.RobotWantedStates.WantedMacro;
import com.team2169.robot.canCycles.CANCycleHandler;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class StateManager {

	public static void stateInit() {
		setWantedMacro(WantedMacro.GROUND);
	}

	public static void teleOpStateLooper() {
		ControlMap.getOperatorStickState();
		getCANCycles();
		getWantedMacroState();
		getWantedDriveOverride();
		getWantedShiftState();
		getWantedArmState();
		getWantedIntakeState();
		getWantedElevatorState();
	}

	static void getCANCycles() {

		if (ControlMap.dropAndExhaustButton()) {
			CANCycleHandler.dropAndExhaust.start();
			RobotStates.elevatorOverrideMode = false;
			RobotStates.armStickMode = false;
			RobotStates.armButtonMode = false;
			RobotStates.intakeClampOverride = false;
		}

	}

	// Local MacroSetter
	static void setWantedMacro(WantedMacro pos) {
		RobotWantedStates.wantedMacro = pos;
		CANCycleHandler.cancelArmElevatorCycles();
		RobotStates.elevatorOverrideMode = false;
		RobotStates.armStickMode = false;
		RobotStates.armButtonMode = false;
		RobotStates.intakeClampOverride = false;
	}

	// MacroButton/State Getter
	static void getWantedMacroState() {
		SmartDashboard.putBoolean("Task Running", CANCycleHandler.dropAndExhaust.isRunning());
		if (ControlMap.groundMacroPressed()) {
			setWantedMacro(WantedMacro.GROUND);
		} else if (ControlMap.switchMacroPressed()) {
			setWantedMacro(WantedMacro.SWITCH);
		} else if (ControlMap.scaleLowMacroPressed()) {
			setWantedMacro(WantedMacro.SCALE_LOW);
		} else if (ControlMap.scaleMidMacroPressed()) {
			setWantedMacro(WantedMacro.SCALE_MID);
		} else if (ControlMap.scaleHighMacroPressed()) {
			setWantedMacro(WantedMacro.SCALE_HIGH);
		} else if (ControlMap.hangMacroPressed()) {
			setWantedMacro(WantedMacro.HANG);
		}
	}

	// Wanted Driver Override Handler
	static void getWantedDriveOverride() {

		// Drivers Want to Hang
		if (ControlMap.driversWantToHang()) {
			if (RobotWantedStates.wantedDriveOverride == WantedDriveOverride.WANTS_TO_HANG
					|| RobotWantedStates.wantedDriveOverride == WantedDriveOverride.HANG) {
				RobotWantedStates.wantedDriveOverride = WantedDriveOverride.HANG;
			}

			else {
				RobotWantedStates.wantedDriveOverride = WantedDriveOverride.WANTS_TO_HANG;
			}
		}

		// Drivers Want to Drive
		else {
			// Coming from Climbing
			if (RobotWantedStates.wantedDriveOverride == WantedDriveOverride.WANTS_TO_HANG
					|| RobotWantedStates.wantedDriveOverride == WantedDriveOverride.HANG) {
				RobotWantedStates.wantedDriveOverride = WantedDriveOverride.WANTS_TO_DRIVE;
			} else if (ControlMap.primaryDriverOverride()) {
				RobotWantedStates.wantedDriveOverride = WantedDriveOverride.OVERRIDE;
			} else {
				RobotWantedStates.wantedDriveOverride = WantedDriveOverride.NONE;
			}
		}

	}

	// Wanted Driver Shift Handler
	static void getWantedShiftState() {
		if (ControlMap.shiftUp()) {
			RobotWantedStates.wantedDriveMode = WantedDriveMode.SHIFT_TO_HIGH;
		} else if (ControlMap.shiftDown()) {
			RobotWantedStates.wantedDriveMode = WantedDriveMode.SHIFT_TO_LOW;
		}
	}

	// Wanted Operator Intake Handler
	static void getWantedIntakeState() {

		// Intake Wheel States
		// Driver has taken control of mechanism, follow their controls.
		if (ControlMap.operatorStickState == OperatorStickState.INTAKE || RobotStates.canCycleMode) {

			// Intake Wheel States
			if (ControlMap.getOperatorStickValue() < -ControlMap.operatorDeadband) {
				CANCycleHandler.cancelArmElevatorCycles();
				RobotWantedStates.wantedIntakeMode = WantedIntakeMode.EXHAUST;
			} else if (ControlMap.getOperatorStickValue() > ControlMap.operatorDeadband) {
				CANCycleHandler.cancelArmElevatorCycles();
				RobotWantedStates.wantedIntakeMode = WantedIntakeMode.INTAKE;
			} else if (!RobotStates.canCycleMode) {
				RobotWantedStates.wantedIntakeMode = WantedIntakeMode.IDLE;
			}

		}

		// Intake stick is busy at the moment, shut off intake
		else {
			RobotWantedStates.wantedIntakeMode = WantedIntakeMode.IDLE;
		}

		if (ControlMap.operatorWantsUltrasonic()) {
			RobotStates.operatorWantsUltrasonic = true;
		} else {
			RobotStates.operatorWantsUltrasonic = false;
		}

		// Intake Clamp States

		// Not in CanCycle

		if (ControlMap.neutralButtonPressed()) {
			CANCycleHandler.cancelArmElevatorCycles();
			RobotWantedStates.wantedIntakeClamp = WantedIntakeClamp.NEUTRAL;
			RobotStates.intakeClampOverride = true;
		}

		else if (ControlMap.clampButtonPressed()) {
			CANCycleHandler.cancelArmElevatorCycles();
			RobotWantedStates.wantedIntakeClamp = WantedIntakeClamp.CLAMP;
			RobotStates.intakeClampOverride = true;
		}

		else if (ControlMap.dropButtonPressed()) {
			CANCycleHandler.cancelArmElevatorCycles();
			RobotWantedStates.wantedIntakeClamp = WantedIntakeClamp.DROP;
			RobotStates.intakeClampOverride = true;
		}
		
		if (!RobotStates.intakeClampOverride && !RobotStates.canCycleMode) {
			switch (RobotWantedStates.wantedMacro) {
			case GROUND:
			default:
				RobotWantedStates.wantedIntakeClamp = WantedIntakeClamp.CLAMP;
				break;
			case HANG:
				RobotWantedStates.wantedIntakeClamp = WantedIntakeClamp.CLAMP;
				break;
			case SCALE_HIGH:
				RobotWantedStates.wantedIntakeClamp = WantedIntakeClamp.CLAMP;
				break;
			case SCALE_LOW:
				RobotWantedStates.wantedIntakeClamp = WantedIntakeClamp.CLAMP;
				break;
			case SCALE_MID:
				RobotWantedStates.wantedIntakeClamp = WantedIntakeClamp.CLAMP;
				break;
			case SWITCH:
				RobotWantedStates.wantedIntakeClamp = WantedIntakeClamp.CLAMP;
				break;

			}
		}
	}

	// Wanted Operator Elevator Handler
	static void getWantedElevatorState() {

		// Intake Wheel States
		// Driver has taken control of mechanism, follow their controls.
		if (ControlMap.operatorStickState == OperatorStickState.ELEVATOR) {
			CANCycleHandler.cancelArmElevatorCycles();
			RobotStates.elevatorOverrideMode = true;
			RobotWantedStates.wantedElevatorPos = WantedMacro.OVERRIDE;
		}

		else if (RobotStates.elevatorOverrideMode) {
			RobotWantedStates.wantedElevatorPos = WantedMacro.HOLD_POSITION;
		}

		// Robot is in a CanCycle, don't interfere unless overriden
		if (!RobotStates.canCycleMode && !RobotStates.elevatorOverrideMode) {
			RobotWantedStates.wantedElevatorPos = RobotWantedStates.wantedMacro;
		}

	}

	// Wanted Operator Arm Handler
	static void getWantedArmState() {

		// Intake Wheel States
		// Driver has taken control of mechanism, follow their controls.
		
		if (ControlMap.operatorStickState == OperatorStickState.ARM) {
			RobotStates.armStickMode = true;
			CANCycleHandler.cancelArmElevatorCycles();
			RobotWantedStates.wantedArmPos = WantedArmPos.OVERRIDE;
		}
		
		else if(ControlMap.armExtendPressed()) {			
			RobotStates.armButtonMode = true;
			RobotStates.armStickMode = false;
			CANCycleHandler.cancelArmElevatorCycles();
			RobotWantedStates.wantedArmPos = WantedArmPos.EXTENDED;			
		}
		
		else if(ControlMap.armRetractPressed()) {
			RobotStates.armButtonMode = true;
			RobotStates.armStickMode = false;
			CANCycleHandler.cancelArmElevatorCycles();
			RobotWantedStates.wantedArmPos = WantedArmPos.RETRACTED;
		}

		else if (RobotStates.armStickMode) {
			RobotWantedStates.wantedArmPos = WantedArmPos.HOLD_POSITION;
		}

		// Robot is in a CanCycle, don't interfere unless overriden
		if (!RobotStates.canCycleMode && !RobotStates.armStickMode && !RobotStates.armButtonMode) {

			// No CanCycle or Driver Override, do the default Macro action

			switch (RobotWantedStates.wantedMacro) {
			case GROUND:
			default:
				RobotWantedStates.wantedArmPos = WantedArmPos.EXTENDED;
				break;
			case HANG:
				RobotWantedStates.wantedArmPos = WantedArmPos.EXTENDED;
				break;
			case SCALE_HIGH:
				RobotWantedStates.wantedArmPos = WantedArmPos.EXTENDED;
				break;
			case SCALE_LOW:
				RobotWantedStates.wantedArmPos = WantedArmPos.EXTENDED;
				break;
			case SCALE_MID:
				RobotWantedStates.wantedArmPos = WantedArmPos.EXTENDED;
				break;
			case SWITCH:
				RobotWantedStates.wantedArmPos = WantedArmPos.EXTENDED;
				break;

			}
		}

	}
}
