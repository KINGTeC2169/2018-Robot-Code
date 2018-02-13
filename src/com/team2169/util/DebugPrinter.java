package com.team2169.util;

import com.team2169.robot.Constants;
import com.team2169.robot.RobotStates;
import com.team2169.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.DriverStation;

public class DebugPrinter {

	public static void intakeDebug() {
		DriverStation.reportWarning("Intake Wheels State: " + RobotStates.intakeMode.name(), false);
		DriverStation.reportWarning("Intake Clamp State: " + RobotStates.intakeClamp, false);
	}
	
	public static void elevatorDebug() {
		DriverStation.reportWarning("Elevator Position: " + RobotStates.elevatorHeight, false);
		DriverStation.reportWarning("Elevator State: " + RobotStates.elevatorPos.name(), false);
		DriverStation.reportWarning("Elevator Override Mode: " + RobotStates.elevatorOverideMode, false);
		DriverStation.reportWarning("Elevator In Position: " + RobotStates.elevatorInPosition, false);
	}
	
	public static void armDebug() {
		DriverStation.reportWarning("Arm State: " + RobotStates.armPos.name(), false);
		DriverStation.reportWarning("Arm Override Mode: " + RobotStates.armOverideMode, false);
		DriverStation.reportWarning("Arm In Position: " + RobotStates.armInPosition, false);
	}
	
	public static void pathfinderDebug() {
		
	}
	
	public static void driveTrainDebug() {
		DriverStation.reportWarning("Left Wheel Power: " + DriveTrain.getInstance().leftMaster.getMotorOutputVoltage(), false);
		DriverStation.reportWarning("Right Wheel Power: " + DriveTrain.getInstance().rightMaster.getMotorOutputVoltage(), false);
		DriverStation.reportWarning("Left Encoder Value: " + 
				DriveTrain.getInstance().leftMaster.getSelectedSensorPosition(Constants.rightDriveData.slotIDx), false);
		DriverStation.reportWarning("Right Encoder Value: " + 
				DriveTrain.getInstance().rightMaster.getSelectedSensorPosition(Constants.rightDriveData.slotIDx), false);
		DriverStation.reportWarning("Driver Override: " + RobotStates.driveOverride.name(), false);
		DriverStation.reportWarning("Driver Mode: " + RobotStates.driveMode.name(), false);
	}
	
	public static void platformDebug() {
		DriverStation.reportWarning("Platform Released: " + RobotStates.platformRelease, false);
	}
}
