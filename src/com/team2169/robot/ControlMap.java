package com.team2169.robot;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;

import com.team2169.robot.RobotWantedStates.WantedIntakeClamp;
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
		
		//Axis Constants
		static int armOverrideButton = 2;
		static int elevatorOverrideButton = 3;
		
		static int clampButton = 10;
		static int neutralButton = 11;
		static int dropButton = 12;
		
		//Elevator Macro Keys
		static int macroGround = 1;
		static int macroSwitch = 2;
		static int macroScaleLow = 3;
		static int macroScaleMid = 4;
		static int macroScaleHigh = 5;
		static int macroHang = 6;
		
		//Intake Keys
		static int operatorAxis = 3;
		static int clamp = 7;
	
		//Climb Keys
		static int climbPrimary = 1;
		static int climbOperator = 21;
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
			primaryRight = new Joystick(1);
			operator = new Joystick(2);
			
		}
		
		static enum OperatorStickState{
			INTAKE, ARM, ELEVATOR
		}
		static OperatorStickState operatorStickState;
		
		//Control Settings
		public static final double elevatorOverrideSetpointMovement = 6;
		public static final double armOverrideSetpointMovement = 6;	
		
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
		
		public static boolean shiftUp() {
			return (primaryLeft.getRawButton(shiftUp) || primaryRight.getRawButton(shiftUp));
		}
		
		public static boolean shiftDown() {
			return (primaryLeft.getRawButton(shiftDown) || primaryRight.getRawButton(shiftDown));
		}
	
		//Primary Driver Speed-Cap/Shifting Override Handler
		public static boolean primaryDriverOverride() {
			if(primaryLeft.getRawButton(primaryOverride) || primaryRight.getRawButton(primaryOverride)) {
				return true;
			}
			return false;
		}


	//Macro Buttons

		//Ground Macro
		public static boolean groundMacroPressed() {
			return operator.getRawButton(macroGround);
		}
		
		//Switch Macro
		public static boolean switchMacroPressed() {
			return operator.getRawButton(macroSwitch);
		}
		
		//Scale Low Macro
		public static boolean scaleLowMacroPressed() {
			return operator.getRawButton(macroScaleLow);
		}
		
		//Scale Mid Macro
		public static boolean scaleMidMacroPressed() {
			return operator.getRawButton(macroScaleMid);
		}
		
		//Scale High Macro
		public static boolean scaleHighMacroPressed() {
			return operator.getRawButton(macroScaleHigh);
		}
		
		//Hang Macro
		public static boolean hangMacroPressed() {
			return operator.getRawButton(macroHang);
		}
		

	
	public static void getOperatorStickState() {
		
		if(operator.getRawButton(elevatorOverrideButton)) {
			operatorStickState = OperatorStickState.ELEVATOR;
			RobotStates.elevatorOverideMode = true;
		}
		else if(operator.getRawButton(armOverrideButton)){
			operatorStickState = OperatorStickState.ARM;
			RobotStates.armOverideMode = true;
		}
		else {
			operatorStickState = OperatorStickState.INTAKE;
		}
		
	}
	
	//Intake WantedState handler
	public static void getWantedIntakeState(){

		//Intake Wheel States
			if(operatorStickState == OperatorStickState.INTAKE) {
			
				//Intake Wheel States
				if(operator.getRawAxis(operatorAxis) < -.2) {
					
					RobotWantedStates.wantedIntakeMode = WantedIntakeMode.INTAKE;
				
				}
				else if(operator.getRawAxis(operatorAxis) > .2){
					
					RobotWantedStates.wantedIntakeMode = WantedIntakeMode.EXHAUST;
				
				}
				else{
					
					RobotWantedStates.wantedIntakeMode = WantedIntakeMode.IDLE;
				
				}
				
			}
			
			//Intake stick is busy at the moment, shut off intake
			else {
				
				RobotWantedStates.wantedIntakeMode = WantedIntakeMode.IDLE;
			
			}
		
		//Intake Clamp States
		
			if(operator.getRawButtonPressed(clampButton)) {
	
				RobotWantedStates.wantedIntakeClamp = WantedIntakeClamp.CLAMP;
				
			}
			
			else if(operator.getRawButtonPressed(neutralButton)) {
	
				RobotWantedStates.wantedIntakeClamp = WantedIntakeClamp.NEUTRAL;
				
			}
			
			else if(operator.getRawButtonPressed(dropButton)) {
	
				RobotWantedStates.wantedIntakeClamp = WantedIntakeClamp.DROP;
				
			}
	}
	
	//Operator Override Handlers
		
		public static double getOperatorOverrideValue() {
			return operator.getRawAxis(operatorAxis);
		}
			
		public static void operatorUnsafeAction() {
			operator.setRumble(RumbleType.kLeftRumble, 1);
		}

	//Hanging Logic
		
		public static boolean driversWantToHang() {
			
			return (primaryLeft.getRawButton(climbPrimary) || primaryRight.getRawButton(climbPrimary)) 
			&& operator.getRawButton(climbOperator);
		
		}
		
	
		public static void getWantedPlatform() {
			
			if(/*Robot.fms.remainingTimeTeleOp() <= 30 && */operator.getRawButton(releasePlatform)) {
				
				RobotWantedStates.platformRelease = true;
				
			}
			
		}

	}

