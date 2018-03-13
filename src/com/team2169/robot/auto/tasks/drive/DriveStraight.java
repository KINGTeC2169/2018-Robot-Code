package com.team2169.robot.auto.tasks.drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.team2169.robot.Constants;
import com.team2169.robot.RobotStates.DriveType;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.auto.tasks.Task;
import com.team2169.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.PIDController;

public class DriveStraight extends Task {

	private int desiredEncoderTicks;
	private double initialAngle;
	private DriveTrain drive;
	private double speed;
	int directionFactor;
	PIDController turnController;

	public DriveStraight(double inches, double speed_) {

		turnController.enable();
		directionFactor = (desiredEncoderTicks >= 0) ? 1 : -1;
		desiredEncoderTicks = (int) (inches * Constants.ticksPerRotation * Constants.wheelDiameter * Math.PI);
		drive = DriveTrain.getInstance();
		speed = speed_;

	}

	protected void initialize() {

		RobotWantedStates.wantedDriveType = DriveType.IDLE;
		drive.leftMaster.setSelectedSensorPosition(0, 0, 10);
		drive.rightMaster.setSelectedSensorPosition(0, 0, 10);
		initialAngle = drive.navX.getAngle();

	}

	protected void execute() {

		drive.leftMaster.set(ControlMode.PercentOutput, speed + getAngleCorrection() * getRightRamp());
		drive.rightMaster.set(ControlMode.PercentOutput, speed - getAngleCorrection() * getLeftRamp());

	}

	@Override
	protected boolean isFinished() {
		return getLeftCompletionPercentage() > .995 && getRightCompletionPercentage() > .995;
	}

	double getLeftRamp() {
		if (getLeftCompletionPercentage() > .94) {
			return (1 - getLeftCompletionPercentage() * 8);
		} else if (getLeftCompletionPercentage() < .6) {
			return (getLeftCompletionPercentage() * 8);
		}
		return 1;
	}

	double getRightRamp() {
		if (getRightCompletionPercentage() > .94) {
			return (1 - getRightCompletionPercentage() * 8);
		} else if (getRightCompletionPercentage() < .6) {
			return (getRightCompletionPercentage() * 8);
		}
		return 1;
	}

	double getLeftCompletionPercentage() {

		return ((double) drive.leftMaster.getSelectedSensorPosition(0) / (double) desiredEncoderTicks);

	}

	double getRightCompletionPercentage() {

		return ((double) drive.rightMaster.getSelectedSensorPosition(0) / (double) desiredEncoderTicks);

	}

	double getAngleCorrection() {
		return Constants.driveTrainP * getAngleError();
	}

	double getAngleError() {
		return (initialAngle - drive.getAngle());
	}

	protected void interrupted() {
		end();
	}
}