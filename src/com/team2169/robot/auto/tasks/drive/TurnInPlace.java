package com.team2169.robot.auto.tasks.drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.RobotStates.DriveType;
import com.team2169.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnInPlace extends Command implements PIDOutput {

	private DriveTrain drive;
	public static PIDController turnController;
	private static final double kP = 0.2;
	private static final double kI = 0.05;
	private static final double kD = 0.1;
	private static final double kF = 0.1;
	private static final double kToleranceDegrees = 1.0f;
	private double rotateToAngleRate = 0;
	private double targetAngle = 0;
	private double currentAngle = 0;

	public TurnInPlace(double targetAngle) {
		this.targetAngle = targetAngle;
	}

	protected void initialize() {

		RobotWantedStates.wantedDriveType = DriveType.IDLE;
		drive = DriveTrain.getInstance();
		drive.navX.setPIDSourceType(PIDSourceType.kRate);
		turnController = new PIDController(kP, kI, kD, kF, drive.navX, this);
		turnController.setInputRange(-180.0f, 180.0f);
		turnController.setOutputRange(-0.6, 0.6);
		turnController.setAbsoluteTolerance(kToleranceDegrees);
		turnController.setContinuous(true);
		turnController.setSetpoint(targetAngle);
		turnController.enable();

	}

	protected void execute() {
		synchronized (this) {
			currentAngle = drive.navX.getAngle();
			SmartDashboard.putNumber("Current Angle", currentAngle);
		}
	}

	protected boolean isFinished() {
		// Stop rotating when the PID speed drops below our deadband.
		return Math.abs(targetAngle - currentAngle) < kToleranceDegrees;
	}

	protected void end() {
		DriverStation.reportError("DoNE", false);
		turnController.disable();
	}

	protected void interrupted() {
		end();
	}

	@Override
	public void pidWrite(double output) {
		synchronized (this) {
			rotateToAngleRate = output;
			drive.leftMaster.set(ControlMode.PercentOutput, -1 * rotateToAngleRate);
			drive.rightMaster.set(ControlMode.PercentOutput, rotateToAngleRate);
		}
	}

}
