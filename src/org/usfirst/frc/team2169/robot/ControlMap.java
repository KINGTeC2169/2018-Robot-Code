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
	
	public ControlMap(){
		
		primaryLeft = new Joystick(0);
		primaryRight = new Joystick(1);
		operator = new Joystick(2);
	
	}
		
		public static double leftTankStick() {
			
			return primaryLeft.getRawAxis(1);
			
		}
		
		public static double rightTankStick() {
		
			return primaryRight.getRawAxis(1);
		
		}
		
		public static RobotWantedStates.WantedDriveMode getWantedShift(){
			if(primaryLeft.getRawButtonPressed(shiftUp) || primaryLeft.getRawButtonPressed(shiftUp)) {
				return WantedDriveMode.HIGH;
			}
			else if(primaryLeft.getRawButtonPressed(shiftDown) || primaryRight.getRawButtonPressed(shiftDown)){
				return WantedDriveMode.LOW;
			}
			return null;
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

