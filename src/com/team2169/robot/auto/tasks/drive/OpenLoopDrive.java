package com.team2169.robot.auto.tasks.drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.team2169.robot.RobotStates.DriveType;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.auto.tasks.Task;
import com.team2169.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OpenLoopDrive extends Task {

    private DriveTrain drive;
    Timer timer;

    public OpenLoopDrive() {

        drive = DriveTrain.getInstance();

    }

    protected void initialize() {

        RobotWantedStates.wantedDriveType = DriveType.STOP_PATH;
    }

    protected void execute() {

        double leftOutput = 1;
        double rightOutput = 1;
        SmartDashboard.putNumber("Left Output", leftOutput);
        SmartDashboard.putNumber("Right Output", rightOutput);
        drive.left.set(ControlMode.PercentOutput, -.5);
        drive.right.set(ControlMode.PercentOutput, -.5);

    }

    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }


    protected void end() {
        drive.left.set(ControlMode.PercentOutput, 0);
        drive.right.set(ControlMode.PercentOutput, 0);
    }

    protected void interrupted() {
        end();
    }
}