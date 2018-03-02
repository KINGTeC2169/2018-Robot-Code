package com.team2169.util;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.DriveMode;
import com.team2169.robot.RobotStates.Macro;

import edu.wpi.first.wpilibj.DriverStation;

public class FlyByWireHandler {

	// Elevator Wants to move. Decide if it's safe.
	public static boolean determineSafety(Macro elevatorPos_) {

		if (elevatorPos_ == Macro.GROUND) {
			return true;
		}

		return true;
	}

	public static double getSafeSpeed() {

		if (RobotStates.elevatorHeight <= .25) {

			return 1;

		}

		return ((-.8 * RobotStates.elevatorHeight) + 1.2);

	}

	// Drivetrain wants to shift. Decide if it's safe.
	public static boolean determineShiftSafety(DriveMode driveMode_) {

		if (driveMode_ == DriveMode.SHIFT_TO_HIGH) {

			if (RobotStates.elevatorPos == Macro.SCALE_HIGH) {
				return false;
			}
			if (RobotStates.elevatorPos == Macro.SCALE_MID) {
				return false;
			}
			if (RobotStates.elevatorPos == Macro.SCALE_LOW) {
				return false;
			}

		}

		if (driveMode_ == DriveMode.SHIFT_TO_LOW) {

			if (RobotStates.elevatorPos == Macro.SCALE_HIGH) {
				return false;
			}
			if (RobotStates.elevatorPos == Macro.SCALE_MID) {
				return false;
			}
			if (RobotStates.elevatorPos == Macro.SCALE_LOW) {
				return false;
			}
		}

		DriverStation.reportWarning("No Override!  Everything is safe!", false);

		return true;

	}

}
