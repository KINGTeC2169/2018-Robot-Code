package org.usfirst.frc.team2169.util;

import org.usfirst.frc.team2169.robot.RobotStates;
import org.usfirst.frc.team2169.robot.RobotWantedStates.WantedDriveMode;

import edu.wpi.first.wpilibj.DriverStation;

import org.usfirst.frc.team2169.robot.RobotStates.ElevatorPos;

public class FlyByWireHandler {

	
	//Elevator Wants to move.  Decide if it's safe.
	public static boolean determineSafety(ElevatorPos elevatorPos_) {
		
		if(elevatorPos_ == ElevatorPos.GROUND) {
			return true;
		}

		return true;
	}
	
	public static double getSafeSpeed() {
		
		if(RobotStates.elevatorHeight <=.25) {
		
			return 1;
		
		}
		
		return ((-.8*RobotStates.elevatorHeight)+1.2);
		
	}
	
	//Drivetrain wants to shift.  Decide if it's safe.
	public static boolean determineShiftSafety(WantedDriveMode driveMode_) {
		
		if(driveMode_ == WantedDriveMode.SHIFT_TO_HIGH) {
			
			if(RobotStates.elevatorPos == ElevatorPos.SCALE_HIGH) {
				return false;
			}
			if(RobotStates.elevatorPos == ElevatorPos.SCALE_MID) {
				return false;
			}
			if(RobotStates.elevatorPos == ElevatorPos.SCALE_LOW) {
				return false;
			}
			
		}
		
		if(driveMode_ == WantedDriveMode.SHIFT_TO_LOW) {
			
			if(RobotStates.elevatorPos == ElevatorPos.SCALE_HIGH) {
				return false;
			}
			if(RobotStates.elevatorPos == ElevatorPos.SCALE_MID) {
				return false;
			}
			if(RobotStates.elevatorPos == ElevatorPos.SCALE_LOW) {
				return false;
			}
		}
		
		DriverStation.reportWarning("No Override!  Everything is safe!", false);
		
		return true;
	
	}
	
}
