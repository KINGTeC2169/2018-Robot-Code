package org.usfirst.frc.team2169.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;

public class ControlMap {
	
	static Joystick joy;
	static Button button;
	
	public ControlMap(){
		
		//Controls go here
		joy = new Joystick(0);
		
	
	}
	
		public static boolean buttonPressed() {
			
			return joy.getRawButtonPressed(1);
			
		}
	
		public static double leftStickX() {
			
			return joy.getRawAxis(0);
			
		}
		
		public static boolean leftStickActive() {
			if(joy.getRawAxis(0) > -.05 && joy.getRawAxis(0) < .05) {
				return false;
			}
			return true;
		}
		
	}

