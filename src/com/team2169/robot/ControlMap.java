package com.team2169.robot;

import com.team2169.robot.RobotStates.HookState;

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
    private static int openButton = 3;
    private static int resetElevatorButton = 12;

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
    private static int releasePlatform = 110;
    
	private static int retractArmButton = 5;
	private static int extendArmButton = 4;
	
	private static int releaseHook = 7;
	private static int retractHook = 9;

    // Deadbands
    static double operatorDeadband = .25;

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
    	
        if(Math.abs(stickVal) < .01) {
        	return 0;
        }
        
        if (squared) {
            return stickVal * Math.abs(stickVal);
        }
        return stickVal;
    }

    public static double rightTankStick(boolean squared) {
        
        double stickVal = primaryRight.getRawAxis(1);
    	
        if(Math.abs(stickVal) < .01) {
        	return 0;
        }
    	
    	if (squared) {
            return stickVal * Math.abs(stickVal);
        }
        return stickVal;
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
        return (primaryLeft.getRawButton(climbPrimary) || primaryRight.getRawButton(climbPrimary));
    }

    public static void getWantedPlatform() {
        if (/* Robot.fms.remainingTimeTeleOp() <= 30 && */operator.getRawButton(releasePlatform)) {
            RobotWantedStates.platformRelease = true;
        }
    }
    
    public static void getWantedHookRelease() {
        if (/* Robot.fms.remainingTimeTeleOp() <= 30 && */operator.getRawButton(releaseHook)) {
            RobotWantedStates.hookRelease = HookState.EXTEND;
        }
        else if(operator.getRawButton(retractHook)) {
        	RobotWantedStates.hookRelease = HookState.RETRACT;
        }
        else {
        	RobotWantedStates.hookRelease = HookState.IDLE;
        }
    }


    static boolean operatorWantsUltrasonic() {
        return operator.getRawButton(wantsUltrasonic);
    }

	public static boolean ptoActivate() {
		return primaryLeft.getRawButton(11);
	}
	
	public static boolean ptoDeactivate() {
		return primaryLeft.getRawButton(10);
	}

}
