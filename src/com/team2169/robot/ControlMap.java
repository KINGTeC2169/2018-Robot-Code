package com.team2169.robot;

import edu.wpi.first.wpilibj.Joystick;

public class ControlMap {

    // Primary Controls

    // Button Constants
    private static int shiftUp = 3;
    private static int shiftDown = 2;

    // Operator Controls

    // Master Overrides
    private static int primaryOverride = 1;

    // Axis Constants
    private static int armOverrideButton = 2;
    private static int elevatorOverrideButton = 1;

    private static int clampButton = 5;
    private static int dropButton = 3;
    private static int neutralButton = 4;
    private static int dropAndExhaustButton = 6;

    // Elevator Macro Keys
    /*
	 * static int bMacroGround = 6; static int bMacroSwitch = 7; static int
	 * bMacroScaleLow = 8; static int bMacroScaleMid = 9; static int bMacroScaleHigh
	 * = 10; static int bMacroHang = 11;
	 */

    private static int bMacroGround = 1;
    private static int bMacroSwitch = 2;
    private static int bMacroScaleLow = 3;
    private static int bMacroScaleMid = 4;
    private static int bMacroScaleHigh = 5;
    private static int bMacroHang = 6;

    private static final int wantsUltrasonic = 1;

    // Intake Keys
    private static int operatorAxis = 1;
    private static int twistAxis = 2;

    // Climb Keys
    private static int climbPrimary = 10;
    private static int climbOperator = 100;
    private static int releasePlatform = 110;

    // Deadbands
    static double operatorDeadband = .25;

    // Create Joystick Objects
    private static Joystick primaryLeft;
    private static Joystick primaryRight;
    private static Joystick operator;
    private static Joystick buttonBoard;

    // Joystick Creater
    static void init() {

        primaryLeft = new Joystick(0);
        primaryRight = new Joystick(1);
        operator = new Joystick(2);
        buttonBoard = new Joystick(3);

    }

    enum OperatorStickState {
        INTAKE, ARM, ELEVATOR
    }

    static OperatorStickState operatorStickState;

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

    static boolean shiftUp() {
        return (primaryLeft.getRawButton(shiftUp) || primaryRight.getRawButton(shiftUp));
    }

    static boolean shiftDown() {
        return (primaryLeft.getRawButton(shiftDown) || primaryRight.getRawButton(shiftDown));
    }

    // Primary Driver Speed-Cap/Shifting Override Handler
    static boolean primaryDriverOverride() {
        return primaryLeft.getRawButton(primaryOverride) || primaryRight.getRawButton(primaryOverride);
    }

    static boolean intakeIn() {
        return operator.getPOV() == 180;
    }

    static boolean intakeExhaust() {
        return operator.getPOV() == 0;
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

    static boolean dropButtonPressed() {
        return operator.getRawButtonPressed(dropButton);
    }

    static boolean dropAndExhaustButton() {
        return operator.getRawButtonPressed(dropAndExhaustButton);
    }

    static boolean neutralButtonPressed() {
        return operator.getRawButton(neutralButton);
    }

    static void getOperatorStickState() {

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
        return (primaryLeft.getRawButton(climbPrimary) || primaryRight.getRawButton(climbPrimary))
                && operator.getRawButton(climbOperator);
    }

    public static void getWantedPlatform() {
        if (/* Robot.fms.remainingTimeTeleOp() <= 30 && */operator.getRawButton(releasePlatform)) {
            RobotWantedStates.platformRelease = true;
        }
    }

    static boolean operatorWantsUltrasonic() {
        return operator.getRawButton(wantsUltrasonic);
    }

}
