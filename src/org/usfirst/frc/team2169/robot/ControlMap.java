package org.usfirst.frc.team2169.robot;

import edu.wpi.first.wpilibj.Joystick;

public class ControlMap {
	
	static Joystick joy;
	
	public ControlMap(){
		
		joy = new Joystick(1);
		//Controls go here
	
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

