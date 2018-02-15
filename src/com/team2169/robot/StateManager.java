package com.team2169.robot;

import com.team2169.robot.RobotWantedStates.WantedDriveMode;
import com.team2169.robot.RobotWantedStates.WantedDriveOverride;
import com.team2169.robot.RobotWantedStates.WantedMacro;

public class StateManager {
	
	//DriveTrain Shifting WantedState handler
		public static void getWantedShiftState(){
			if(ControlMap.shiftUp()) {
				RobotWantedStates.wantedDriveMode = WantedDriveMode.SHIFT_TO_HIGH;
			}
			else if(ControlMap.shiftDown()){
				RobotWantedStates.wantedDriveMode = WantedDriveMode.SHIFT_TO_LOW;
			}
		}
	
	//Macro WantedState handler
	public static void getWantedMacroState(){
		if(ControlMap.groundMacroPressed()) {
			setWantedMacro(WantedMacro.GROUND);
		}
		else if(ControlMap.switchMacroPressed())  {
			setWantedMacro(WantedMacro.SWITCH);
		}
		else if(ControlMap.scaleLowMacroPressed())  {
			setWantedMacro(WantedMacro.SCALE_LOW);
		}
		else if(ControlMap.scaleMidMacroPressed())  {
			setWantedMacro(WantedMacro.SCALE_MID);
		}
		else if(ControlMap.scaleHighMacroPressed())  {
			setWantedMacro(WantedMacro.SCALE_HIGH);
		}
		else if(ControlMap.hangMacroPressed())  {
			setWantedMacro(WantedMacro.HANG);
		}
	}

	//Local WantedMacro handler
	static void setWantedMacro(WantedMacro pos) {
		RobotWantedStates.wantedMacro = pos;
		RobotStates.elevatorOverideMode = false;
		RobotStates.armOverideMode = false;
		RobotStates.intakeClampOverride = false;
	}
	
	public static void getWantedDriveOverride() {
		
		//Drivers Want to Hang
		if(ControlMap.driversWantToHang()) {
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
			else if(ControlMap.primaryDriverOverride()){
				RobotWantedStates.wantedDriveOverride = WantedDriveOverride.OVERRIDE;
			}
			else {
				RobotWantedStates.wantedDriveOverride = WantedDriveOverride.NONE;
			}
		}
		
	}	
	
}
