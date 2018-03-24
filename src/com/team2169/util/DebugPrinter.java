package com.team2169.util;

import com.team2169.robot.RobotStates;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DebugPrinter {

    public static void intakeDebug() {
        SmartDashboard.putString("Intake Wheels State: ", RobotStates.intakeMode.name());
        SmartDashboard.putString("Intake Clamp State: ", RobotStates.intakeClamp.name());
    }

    public static void elevatorDebug() {
        //DriverStation.reportWarning("Elevator Position: " +
        //RobotStates.elevatorHeight, false);
        SmartDashboard.putString("Elevator State: ", RobotStates.elevatorPos.name());
        String string = "" + RobotStates.elevatorOverrideMode;
        SmartDashboard.putString("Elevator Override Mode: ", string);
        // DriverStation.reportWarning("Elevator In Position: " +
        // RobotStates.elevatorInPosition, false);
    }

    public static void armDebug() {
        SmartDashboard.putString("Arm State: ", RobotStates.armPos.name());
        String string = "" + RobotStates.armStickMode;
        SmartDashboard.putString("Arm Override Mode: ", string);
        // DriverStation.reportWarning("Arm In Position: " + RobotStates.armInPosition,
        // false);
        SmartDashboard.putBoolean("CanCycle Active", RobotStates.canCycleMode);
    }

    public static void driveTrainDebug() {
        /*
		 * DriverStation.reportWarning("Left Wheel Power: " +
		 * DriveTrain.getInstance().leftMaster.getMotorOutputVoltage(), false);
		 * DriverStation.reportWarning("Right Wheel Power: " +
		 * DriveTrain.getInstance().rightMaster.getMotorOutputVoltage(), false);
		 * DriverStation.reportWarning("Left Encoder Value: " +
		 * DriveTrain.getInstance().leftMaster.getSelectedSensorPosition(Constants.
		 * rightDriveData.slotIDx), false);
		 * DriverStation.reportWarning("Right Encoder Value: " +
		 * DriveTrain.getInstance().rightMaster.getSelectedSensorPosition(Constants.
		 * rightDriveData.slotIDx), false);
		 * DriverStation.reportWarning("Driver Override: " +
		 * RobotStates.driveOverride.name(), false);
		 * DriverStation.reportWarning("Driver Mode: " + RobotStates.driveMode.name(),
		 * false);
		 */
    }

    public static void platformDebug() {
        // DriverStation.reportWarning("Platform Released: " +
        // RobotStates.platformRelease, false);
    }
}
