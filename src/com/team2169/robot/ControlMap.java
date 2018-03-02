package com.team2169.robot;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.Joystick;

public class ControlMap {

	// Primary Controls

	// Button Constants
	static int shiftUp = 3;
	static int shiftDown = 2;

	// Operator Controls

	// Master Overrides
	static int primaryOverride = 1;

	// Axis Constants
	static int armOverrideButton = 2;
	static int elevatorOverrideButton = 1;

	static int clampButton = 5;
	static int dropButton = 3;
	private static int neutralButton = 4;
	static int dropAndExhaustButton = 6;

	// Elevator Macro Keys
	/*
	 * static int bMacroGround = 6; static int bMacroSwitch = 7; static int
	 * bMacroScaleLow = 8; static int bMacroScaleMid = 9; static int bMacroScaleHigh
	 * = 10; static int bMacroHang = 11;
	 */

	static int bMacroGround = 11;
	static int bMacroSwitch = 9;
	static int bMacroScaleLow = 7;
	static int bMacroScaleMid = 12;
	static int bMacroScaleHigh = 10;
	static int bMacroHang = 8;

	private static final int wantsUltrasonic = 1;

	// Intake Keys
	static int operatorAxis = 1;

	// Climb Keys
	static int climbPrimary = 10;
	static int climbOperator = 100;
	static int releasePlatform = 110;

	// Deadbands
	static double operatorDeadband = .65;

	// Create Joystick Objects
	static Joystick primaryLeft;
	static Joystick primaryRight;
	static Joystick operator;
	static Joystick buttonBoard;

	// Joystick Creater
	public static void init() {

		primaryLeft = new Joystick(0);
		primaryRight = new Joystick(1);
		operator = new Joystick(2);
		buttonBoard = new Joystick(2);

	}

	static enum OperatorStickState {
		INTAKE, ARM, ELEVATOR
	}

	static OperatorStickState operatorStickState;

	// Control Settings
	public static final double elevatorOverrideSetpointMovement = 6;
	public static final double armOverrideSetpointMovement = 6;

	// DriveTrain Control Sticks handler
	public static double leftTankStick(boolean squared) {
		if (squared) {
			return primaryLeft.getRawAxis(1) * Math.abs(primaryLeft.getRawAxis(1));
		}
		return primaryLeft.getRawAxis(1);
	}

	public static double rightTankStick(boolean squared) {
		if (squared) {
			return primaryRight.getRawAxis(1) * Math.abs(primaryRight.getRawAxis(1));
		}
		return primaryRight.getRawAxis(1);
	}

	public static boolean shiftUp() {
		return (primaryLeft.getRawButton(shiftUp) || primaryRight.getRawButton(shiftUp));
	}

	public static boolean shiftDown() {
		return (primaryLeft.getRawButton(shiftDown) || primaryRight.getRawButton(shiftDown));
	}

	// Primary Driver Speed-Cap/Shifting Override Handler
	public static boolean primaryDriverOverride() {
		if (primaryLeft.getRawButton(primaryOverride) || primaryRight.getRawButton(primaryOverride)) {
			return true;
		}
		return false;
	}

	public static boolean armExtendPressed() {
		return operator.getPOV() == 0;
	}

	public static boolean armRetractPressed() {
		return operator.getPOV() == 180;
	}

	// Macro Buttons
	// Ground Macro
	public static boolean groundMacroPressed() {
		return buttonBoard.getRawButton(bMacroGround);
	}

	// Switch Macro
	public static boolean switchMacroPressed() {
		return buttonBoard.getRawButton(bMacroSwitch);
	}

	// Scale Low Macro
	public static boolean scaleLowMacroPressed() {
		return buttonBoard.getRawButton(bMacroScaleLow);
	}

	// Scale Mid Macro
	public static boolean scaleMidMacroPressed() {
		return buttonBoard.getRawButton(bMacroScaleMid);
	}

	// Scale High Macro
	public static boolean scaleHighMacroPressed() {
		return buttonBoard.getRawButton(bMacroScaleHigh);
	}

	// Hang Macro
	public static boolean hangMacroPressed() {
		return buttonBoard.getRawButton(bMacroHang);
	}

	// Intake State Buttons
	public static boolean clampButtonPressed() {
		return operator.getRawButtonPressed(clampButton);
	}

	public static boolean dropButtonPressed() {
		return operator.getRawButtonPressed(dropButton);
	}

	public static boolean dropAndExhaustButton() {
		return operator.getRawButtonPressed(dropAndExhaustButton);
	}

	public static boolean neutralButtonPressed() {
		// TODO Auto-generated method stub
		return operator.getRawButton(neutralButton);
	}

	public static void getOperatorStickState() {

		if (operator.getRawButton(elevatorOverrideButton)) {
			operatorStickState = OperatorStickState.ELEVATOR;
			RobotStates.elevatorOverrideMode = true;
		} else if (operator.getRawButton(armOverrideButton)) {
			operatorStickState = OperatorStickState.ARM;
			RobotStates.armStickMode = true;
		} else {
			operatorStickState = OperatorStickState.INTAKE;
		}
	}

	// Operator Override Handlers
	public static double getOperatorStickValue() {
		return operator.getRawAxis(operatorAxis);
	}

	public static void operatorUnsafeAction() {
		operator.setRumble(RumbleType.kLeftRumble, 1);
	}

	// Hanging Logic
	public static boolean driversWantToHang() {
		return (primaryLeft.getRawButton(climbPrimary) || primaryRight.getRawButton(climbPrimary))
				&& operator.getRawButton(climbOperator);
	}

	public static void getWantedPlatform() {
		if (/* Robot.fms.remainingTimeTeleOp() <= 30 && */operator.getRawButton(releasePlatform)) {
			RobotWantedStates.platformRelease = true;
		}
	}

	public static boolean operatorWantsUltrasonic() {
		return operator.getRawButton(wantsUltrasonic);
	}

}
