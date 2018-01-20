package org.usfirst.frc.team2169.robot;

import edu.wpi.first.wpilibj.Joystick;

public class ControlMap {
	
	static Joystick primaryLeft;
	static Joystick primaryRight;
	static Joystick operator;
	
	//Axis/Button Constants
	public static int armAxis = 1;
	public static int elevatorAxis = 2;
	
	public ControlMap(){
		
		primaryLeft = new Joystick(0);
		primaryRight = new Joystick(1);
		operator = new Joystick(2);
	
	}
		
		public static double leftTankStick() {
			
			return primaryLeft.getRawAxis(1);
			
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
	
	}

