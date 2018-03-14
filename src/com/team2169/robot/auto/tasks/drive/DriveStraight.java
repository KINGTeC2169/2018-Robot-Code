package com.team2169.robot.auto.tasks.drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.team2169.robot.Constants;
import com.team2169.robot.RobotStates.DriveType;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.auto.tasks.Task;
import com.team2169.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveStraight extends Task {

	private int desiredEncoderTicks;
	private double initialAngle;
	private DriveTrain drive;
	private double speed;
	int directionFactor;
	//PIDController turnController;

	public DriveStraight(double inches, double speed_) {

		//turnController.enable();
		directionFactor = (desiredEncoderTicks >= 0) ? 1 : -1;
		desiredEncoderTicks = (int) (inches * Constants.ticksPerRotation * Constants.wheelDiameter * Math.PI);
		drive = DriveTrain.getInstance();
		speed = speed_;

	}

	protected void initialize() {

		RobotWantedStates.wantedDriveType = DriveType.WAITING;
		drive.leftMaster.setSelectedSensorPosition(0, 0, 10);
		drive.rightMaster.setSelectedSensorPosition(0, 0, 10);
		initialAngle = drive.navX.getAngle();

	}

	protected void execute() {

		double leftOutput = -(speed + getAngleCorrection());
		double rightOutput = speed - getAngleCorrection();
		SmartDashboard.putNumber("Left Output", leftOutput);
		SmartDashboard.putNumber("Right Output", rightOutput);
		drive.leftMaster.set(ControlMode.PercentOutput, leftOutput);
		drive.rightMaster.set(ControlMode.PercentOutput, rightOutput);

	}

	@Override
	protected boolean isFinished() {
		boolean finished = getLeftCompletionPercentage() > .995 && getRightCompletionPercentage() > .995;
		SmartDashboard.putBoolean("Segment Finished", finished);
		return finished;
	}

	double getLeftCompletionPercentage() {

		double completion = (double) drive.leftMaster.getSelectedSensorPosition(0) / (double) desiredEncoderTicks;
		SmartDashboard.putNumber("Left Completion %", completion);
		return completion;

	}

	double getRightCompletionPercentage() {

		double completion = (double) drive.rightMaster.getSelectedSensorPosition(0) / (double) desiredEncoderTicks;
		SmartDashboard.putNumber("Right Completion %", completion);
		return completion;
		
	}

	double getAngleCorrection() {
		return Constants.driveTrainP * getAngleError();
	}

	double getAngleError() {
		double error = initialAngle - drive.getAngle();
		SmartDashboard.putNumber("Angle Error", error);
		return error;
	}
	
	protected void end() {
		drive.leftMaster.set(ControlMode.PercentOutput, 0);
		drive.rightMaster.set(ControlMode.PercentOutput, 0);
	}

	protected void interrupted() {
		end();
	}
}