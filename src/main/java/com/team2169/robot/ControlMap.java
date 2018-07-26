package com.team2169.robot;

import com.team2169.robot.RobotStates.HookState;

import edu.wpi.first.wpilibj.Joystick;

public class ControlMap {

	// Primary Controls

	// Button Constants
	private static int baseShiftUp = 11;
	private static int baseShiftDown = 10;

	private static int topShiftUp = 3;
	private static int topShiftDown = 2;

	// Operator Controls

	// Master Overrides
	private static int primaryOverride = 1;

	// Axis Constants
	private static int armOverrideButton = 2;
	private static int elevatorOverrideButton = 1;

	private static int clampButton = 5;
	private static int openButton = 3;
	private static int resetElevatorButton = 12;

	private static int bMacroGround = 1;
	private static int bMacroSwitch = 2;
	private static int bMacroScaleLow = 3;
	private static int bMacroScaleMid = 4;
	private static int bMacroScaleHigh = 5;
	private static int bMacroHang = 6;

	public static int operatorBlockRelease = 1;

	// Intake Keys
	private static int operatorAxis = 1;
	private static int twistAxis = 2;

	// Climb Keys
	private static int climbPrimary = 6;
	private static int drivePrimary = 7;

	private static int retractArmButton = 6;
	private static int extendArmButton = 4;

	private static int releaseHook = 7;
	private static int retractHook = 9;

	// Deadbands
	public static double operatorDeadband = .075;

	// Create Joystick Objects
	private static Joystick primaryLeft;
	private static Joystick primaryRight;
	private static Joystick operator;
	private static Joystick buttonBoard;
	private static Joystick intakeStick;

	// Joystick Creater
	static void init() {

		primaryLeft = new Joystick(0);
		primaryRight = new Joystick(1);
		operator = new Joystick(2);
		buttonBoard = new Joystick(4);
		intakeStick = new Joystick(3);

	}

	enum OperatorStickState {
		INTAKE, ARM, ELEVATOR
	}

	static OperatorStickState operatorStickState;

	// DriveTrain Control Sticks handler
	public static double leftTankStick(boolean squared) {
		double stickVal = primaryLeft.getRawAxis(1);

		if (Math.abs(stickVal) < .01) {
			return 0;
		}

		if (squared) {
			return stickVal * Math.abs(stickVal);
		}
		return stickVal;
	}

	public static double rightTankStick(boolean squared) {

		double stickVal = primaryRight.getRawAxis(1);

		if (Math.abs(stickVal) < .01) {
			return 0;
		}

		if (squared) {
			return stickVal * Math.abs(stickVal);
		}
		return stickVal;
	}

	public static double getTurn(){
		return -primaryRight.getRawAxis(0);
	}

	// Primary Driver DT Shift Up
	static boolean shiftUp() {
		return (primaryRight.getRawButton(topShiftUp) || primaryLeft.getRawButton(topShiftUp)
				|| primaryRight.getRawButton(baseShiftUp));
	}

	// Primary Driver DT Shift Down
	static boolean shiftDown() {
		return (primaryRight.getRawButton(topShiftDown) || primaryLeft.getRawButton(topShiftDown)
				|| primaryRight.getRawButton(baseShiftDown));
	}

	// Primary Driver Speed-Cap/Shifting Override Handler
	static boolean primaryDriverOverride() {
		return primaryLeft.getRawButton(primaryOverride) || primaryRight.getRawButton(primaryOverride);
	}

	// Operator Variable Intake Amount
	public static double intakeAmount() {
		return intakeStick.getRawAxis(1);
	}

	// Macro Buttons
	// Ground Macro
	static boolean groundMacroPressed() {
		return buttonBoard.getRawButton(bMacroGround);
	}

	// Switch Macro
	static boolean switchMacroPressed() {
		return buttonBoard.getRawButton(bMacroSwitch);
	}

	// Scale Low Macro
	static boolean scaleLowMacroPressed() {
		return buttonBoard.getRawButton(bMacroScaleLow);
	}

	// Scale Mid Macro
	static boolean scaleMidMacroPressed() {
		return buttonBoard.getRawButton(bMacroScaleMid);
	}

	// Scale High Macro
	static boolean scaleHighMacroPressed() {
		return buttonBoard.getRawButton(bMacroScaleHigh);
	}

	// Hang Macro
	static boolean hangMacroPressed() {
		return buttonBoard.getRawButton(bMacroHang);
	}

	// Intake State Buttons
	static boolean clampButtonPressed() {
		return operator.getRawButtonPressed(clampButton);
	}

	static boolean openButtonPressed() {
		return operator.getRawButtonPressed(openButton);
	}

	static boolean retractArm() {
		return operator.getRawButtonPressed(retractArmButton);
	}

	static boolean extendArm() {
		return operator.getRawButtonPressed(extendArmButton);
	}

	public static boolean resetElevator() {
		return operator.getRawButton(resetElevatorButton);
	}

	public static boolean releaseBlock() {
		return intakeStick.getRawButton(operatorBlockRelease);
	}
	
	static void getOperatorStickState() {

		if (operator.getRawButton(elevatorOverrideButton)) {
			operatorStickState = OperatorStickState.ELEVATOR;
			RobotStates.elevatorOverrideMode = true;
		} else if (operator.getRawButton(armOverrideButton)) {
			operatorStickState = OperatorStickState.ARM;
			RobotStates.armOverride = true;
		} else {
			operatorStickState = OperatorStickState.INTAKE;
		}
	}

	public static boolean getArmZero() {
		return operator.getRawButton(11);
	}

	public static double getOperatorTwistValue() {
		return operator.getRawAxis(twistAxis);
	}

	// Operator Override Handlers
	public static double getOperatorStickValue() {
		return -operator.getRawAxis(operatorAxis);
	}

	// Hanging Logic
	static boolean driversWantToHang() {
		return (primaryRight.getRawButton(climbPrimary));
	}
	
	// Hanging Logic
	static boolean driversWantToDrive() {
		return (primaryRight.getRawButton(drivePrimary));
	}


	public static void getWantedHookRelease() {
		if (operator.getRawButton(releaseHook)) {
			RobotWantedStates.hookRelease = HookState.EXTEND;
		} else if (operator.getRawButton(retractHook)) {
			RobotWantedStates.hookRelease = HookState.RETRACT;
		} else {
			RobotWantedStates.hookRelease = HookState.IDLE;
		}
	}

	public static boolean getQuickTurn() {
		return primaryLeft.getRawButton(6) || primaryRight.getRawButton(11);
	}

}
