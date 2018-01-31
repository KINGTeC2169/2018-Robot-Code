package com.team2169.robot;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;

import com.team2169.robot.RobotWantedStates.WantedDriveMode;
import com.team2169.robot.RobotWantedStates.WantedDriveOverride;
import com.team2169.robot.RobotWantedStates.WantedElevatorPos;
import com.team2169.robot.RobotWantedStates.WantedIntakeMode;

import edu.wpi.first.wpilibj.Joystick;

public class ControlMap {
	
	//Primary Controls
	
		//Button Constants
		static int shiftUp = 4;
		static int shiftDown = 3;	
		
	//Operator Controls
		
		//Master Overrides
		static int primaryOverride = 3;
		static int operatorOverride = 10;
		
		//Axis Constants
		static int armAxis = 1;
		static int elevatorAxis = 5;
		
		//Elevator Macro Keys
		static int liftGroundMacro = 1;
		static int liftSwitchMacro = 2;
		static int liftScaleLowMacro = 3;
		static int liftScaleMidMacro = 4;
		static int liftScaleHighMacro = 5;
		static int liftHangMacro = 6;
		
		//Intake Keys
		static int intakeAxis = 3;
		static int clamp = 7;
	
		//Climb Keys
		static int climbPrimary = 1;
		static int climbOperator = 2;
		static int releasePlatform = 1;
		
		//Deadbands
		static double elevatorDeadband = .2;
		static double armDeadband = .2;
		
		//Create Joystick Objects
		static Joystick primaryLeft;
		static Joystick primaryRight;
		static Joystick operator;
		
		//Joystick Creater
		public static void init() {
			
			primaryLeft = new Joystick(0);
			primaryRight = new Joystick(0);
			operator = new Joystick(0);
			
		}
		
	//DriveTrain Control Sticks handler
		public static double leftTankStick(boolean squared) {
		
			if(squared) {
				return primaryLeft.getRawAxis(1)*Math.abs(primaryLeft.getRawAxis(1));	
			}
			return primaryLeft.getRawAxis(1);
			
		}
		
		public static double rightTankStick(boolean squared) {
		
			if(squared) {
				return primaryRight.getRawAxis(1)*Math.abs(primaryRight.getRawAxis(1));	
			}
			return primaryRight.getRawAxis(1);
		
		}
		
	//DriveTrain Shifting WantedState handler
	public static void getWantedShift(){
		if(primaryLeft.getRawButton(shiftUp) || primaryLeft.getRawButton(shiftUp)) {
			RobotWantedStates.wantedDriveMode = WantedDriveMode.SHIFT_TO_HIGH;
		}
		else if(primaryLeft.getRawButton(shiftDown) || primaryRight.getRawButton(shiftDown)){
			RobotWantedStates.wantedDriveMode = WantedDriveMode.SHIFT_TO_LOW;
		}
	}

	//Elevator WantedState handler
	public static void getWantedElevatorPos(){
		if(operator.getRawButton(liftGroundMacro) || operator.getRawButton(liftGroundMacro)) {
			RobotWantedStates.wantedElevatorPos = WantedElevatorPos.GROUND;
		}
		else if(operator.getRawButton(liftSwitchMacro) || operator.getRawButton(liftSwitchMacro))  {
			RobotWantedStates.wantedElevatorPos = WantedElevatorPos.SWITCH;
		}
		else if(operator.getRawButton(liftScaleLowMacro) || operator.getRawButton(liftScaleLowMacro))  {
			RobotWantedStates.wantedElevatorPos = WantedElevatorPos.SCALE_LOW;			
		}
		else if(operator.getRawButton(liftScaleMidMacro) || operator.getRawButton(liftScaleMidMacro))  {
			RobotWantedStates.wantedElevatorPos = WantedElevatorPos.SCALE_MID;			
		}
		else if(operator.getRawButton(liftScaleHighMacro) || operator.getRawButton(liftScaleHighMacro))  {
			RobotWantedStates.wantedElevatorPos = WantedElevatorPos.SCALE_HIGH;				
		}
		else if(operator.getRawButton(liftHangMacro) || operator.getRawButton(liftHangMacro))  {
			RobotWantedStates.wantedElevatorPos = WantedElevatorPos.HANG;				
		}
	}
	
	//Intake WantedState handler
	public static void getWantedIntake(){

		//Intake Wheel States
		if(operator.getRawAxis(intakeAxis) < -.2) {
			RobotWantedStates.wantedIntakeMode = WantedIntakeMode.INTAKE;
		}
		else if(operator.getRawAxis(intakeAxis) > .2){
			RobotWantedStates.wantedIntakeMode = WantedIntakeMode.EXHAUST;
		}
		else{
			RobotWantedStates.wantedIntakeMode = WantedIntakeMode.IDLE;
		}
		
		//Intake Clamp States
		
		//Clamp Button pressed and Is Clamping
		if(operator.getRawButtonPressed(clamp)) {
			
			if(!RobotWantedStates.intakeClamp) {
				RobotWantedStates.intakeClamp = true;
			}
			else if(RobotWantedStates.intakeClamp) {
				RobotWantedStates.intakeClamp = false;
			}
			else {
				//Something failed
				RobotWantedStates.intakeClamp = false;
			}
			
		}
	}
	
	//Primary Driver Speed-Cap/Shifting Override Handler
	public static boolean primaryDriverOverride() {
		if(primaryLeft.getRawButton(primaryOverride) || primaryRight.getRawButton(primaryOverride)) {
			return true;
		}
		return false;
	}
	
	//Operator Override Handlers
		public static boolean operatorOverrideActive() {
			return operator.getRawButton(operatorOverride);
		}
		
		public static double armOverrideValue() {
			return operator.getRawAxis(armAxis);
		}
		
		public static double elevatorOverrideValue() {
			return operator.getRawAxis(elevatorAxis);
		}
	
		public static boolean isArmOverrideActive() {

		if(operator.getRawAxis(armAxis) > armDeadband || operator.getRawAxis(armAxis) < -armDeadband) {
			return true;
		}
		return false;
		
	}
	
		public static boolean isElevatorOverrideActive() {
		
		if(operator.getRawAxis(elevatorAxis) > elevatorDeadband || operator.getRawAxis(elevatorAxis) < -elevatorDeadband) {
			return true;
		}
		return false;
		
	}
		
	public static void operatorUnsafeAction() {
		operator.setRumble(RumbleType.kLeftRumble, 1);
	}

	static boolean driversWantToHang() {
		return (primaryLeft.getRawButton(climbPrimary) || primaryLeft.getRawButton(climbPrimary)) 
		&& operator.getRawButton(climbOperator);
	}
	
	public static void getWantedDriveOverride() {
		
		//Drivers Want to Hang
		if(driversWantToHang()) {
			if(RobotWantedStates.wantedDriveOverride == WantedDriveOverride.WANTS_TO_HANG 
					|| RobotWantedStates.wantedDriveOverride == WantedDriveOverride.HANG) {
				RobotWantedStates.wantedDriveOverride = WantedDriveOverride.HANG;
			}
			
			else {
				RobotWantedStates.wantedDriveOverride = WantedDriveOverride.WANTS_TO_HANG;
			}
		}
		//Drivers Want to Drive
		else {
			//Coming from Climbing
			if(RobotWantedStates.wantedDriveOverride == WantedDriveOverride.WANTS_TO_HANG 
					|| RobotWantedStates.wantedDriveOverride == WantedDriveOverride.HANG) {
				RobotWantedStates.wantedDriveOverride = WantedDriveOverride.WANTS_TO_DRIVE;
			}
			else if(primaryDriverOverride()){
				RobotWantedStates.wantedDriveOverride = WantedDriveOverride.OVERRIDE;
			}
			else {
				RobotWantedStates.wantedDriveOverride = WantedDriveOverride.NONE;
			}
		}
		
	}

	public static void getWantedPlatform() {
		if(/*Robot.fms.remainingTimeTeleOp() <= 30 && */operator.getRawButton(releasePlatform)) {
			
			RobotWantedStates.platformRelease = true;
			
		}
		
	}


	}

