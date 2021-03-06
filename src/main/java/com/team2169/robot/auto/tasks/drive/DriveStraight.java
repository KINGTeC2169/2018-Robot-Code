package com.team2169.robot.auto.tasks.drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
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
	private int i;
	private int k;
	private int j = 0;
	private double speed;
	private int directionFactor;
	private double leftError = 0;
	private double rightError = 0;

	public DriveStraight(double inches, double speed_) {
		i = 0;
		j = 0;
		k = 0;
		RobotWantedStates.wantedDriveType = DriveType.EXTERNAL_DRIVING;
		directionFactor = (desiredEncoderTicks >= 0) ? -1 : 1;
		desiredEncoderTicks = (int) (inches / (Constants.wheelDiameter * Math.PI) * Constants.ticksPerRotation);
		desiredEncoderTicks += 600;
		drive = DriveTrain.getInstance();
		speed = speed_;
	}

	protected void initialize() {
		k = 0;
		RobotWantedStates.wantedDriveType = DriveType.EXTERNAL_DRIVING;
		drive.left.setSelectedSensorPosition(0, 0, 10);
		drive.right.setSelectedSensorPosition(0, 0, 10);
		drive.navX.reset();
		initialAngle = drive.navX.getAngle();
		i = 0;

		// Verify that the encoder actually zeroed and didn't jump back to original
		// value
		while ((i < 5) || k > 100) {

			if (((Math.abs(drive.left.getSelectedSensorPosition(0)) <= 50)
					|| drive.left.getSelectedSensorPosition(0) == 0)
					&& ((Math.abs(drive.right.getSelectedSensorPosition(0)) <= 50)
							|| drive.right.getSelectedSensorPosition(0) == 0)) {
				i++;
			} else {

				i = 0;

			}
			k++;
			// System.out.println("Loop Count: " + k);
		}
		// System.out.println("Total Loops: " + k);
		i = 0;

	}

	protected void execute() {

		// Wait 10 loops to verify encoder didn't retain old value. If you have more
		// than 10 loops, execute as normal.
		if (i > 10) {

			leftError = desiredEncoderTicks - drive.left.getSelectedSensorPosition(0);
			rightError = desiredEncoderTicks - drive.right.getSelectedSensorPosition(0);
			double leftOutput = 0;
			double rightOutput = 0;
			leftOutput = (getDesiredSpeed(leftError) - getAngleCorrection()) * directionFactor;
			rightOutput = (getDesiredSpeed(leftError) + getAngleCorrection()) * directionFactor;
			SmartDashboard.putNumber("Left Output", leftOutput);
			SmartDashboard.putNumber("Right Output", rightOutput);
			SmartDashboard.putNumber("Desired Ticks", desiredEncoderTicks);
			SmartDashboard.putNumber("Left DT Error", leftError);
			SmartDashboard.putNumber("Right DT Error", rightError);
			drive.left.set(ControlMode.PercentOutput, leftOutput);
			drive.right.set(ControlMode.PercentOutput, rightOutput);

		}

		i++;

	}

	private double getDesiredSpeed(double error) {

		// Cap PID output
		double p = Constants.driveStraightP * error;
		if (p > speed) {
			return speed;
		} else if (-p < -speed) {
			return -speed;
		}

		return p;

	}

	@Override
	protected boolean isFinished() {
		if (i > 10) {
			boolean finished = distanceFinished() || this.isTimedOut();
			return finished;
		}
		else {
			return false;
		}
	}

	public boolean distanceFinished() {

		// If either encoder has hit the point, stop. This is because red/orange
		// encoders don't read as many ticks, so they overshoot.
		if (Math.abs(leftError) < 750) {
			j++;
		} else {
			j = 0;
		}

		return j > 1;
	}

	private double getAngleCorrection() {
		return Constants.driveTrainAngleCorrectionP * getAngleError();
	}

	private double getAngleError() {
		double error = initialAngle - drive.getAngle();
		SmartDashboard.putNumber("Angle Error", error);
		return error;
	}

	protected void end() {
		System.out.println("DONE DRIVING");
		drive.left.setNeutralMode(NeutralMode.Brake);
		drive.right.setNeutralMode(NeutralMode.Brake);
		drive.left.set(ControlMode.PercentOutput, 0);
		drive.right.set(ControlMode.PercentOutput, 0);
	}

	protected void interrupted() {
		end();
	}
}