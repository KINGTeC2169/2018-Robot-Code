package org.usfirst.frc.team2169.robot;

import org.usfirst.frc.team2169.robot.RobotWantedStates.WantedDriveMode;

import edu.wpi.first.wpilibj.Joystick;

public class ControlMap {
	
	static Joystick primaryLeft;
	static Joystick primaryRight;
	static Joystick operator;
	
	//Axis Constants
	static int armAxis = 1;
	static int elevatorAxis = 2;

	//Button Constants
	static int shiftUp = 3;
	static int shiftDown = 4;	
	
		public static void init() {
			
			primaryLeft = new Joystick(0);
			primaryRight = new Joystick(0);
			operator = new Joystick(0);
			
		}
		
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
		
		public static void getWantedShift(){
			if(primaryLeft.getRawButton(shiftUp) || primaryLeft.getRawButton(shiftUp)) {
				RobotWantedStates.wantedDriveMode = WantedDriveMode.SHIFT_TO_HIGH;
			}
			else if(primaryLeft.getRawButton(shiftDown) || primaryRight.getRawButton(shiftDown)){
				RobotWantedStates.wantedDriveMode = WantedDriveMode.SHIFT_TO_LOW;
			}
		}
		
		public static boolean operatorOverrideActive() {
			if(isArmOverrideActive() && isElevatorOverrideActive()) {
				return true;
			}
			return false;
		}
		
		public static double armOverrideValue() {
			return operator.getRawAxis(armAxis);
		}
		
		public static double elevatorOverrideValue() {
			return operator.getRawAxis(elevatorAxis);
		}
		
		public static boolean isArmOverrideActive() {
		
			if(operator.getRawAxis(armAxis) > .1 || operator.getRawAxis(armAxis) < -.1) {
				return true;
			}
			return false;
			
		}
		
		public static boolean isElevatorOverrideActive() {
			
			if(operator.getRawAxis(elevatorAxis) > .1 || operator.getRawAxis(elevatorAxis) < -.1) {
				return true;
			}
			return false;
			
		}

		public static boolean primaryDriverOverride() {
			if(primaryLeft.getRawButton(1) || primaryRight.getRawButton(1)) {
				return true;
			}
			return false;
		}

	}

