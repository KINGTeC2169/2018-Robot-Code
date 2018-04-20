package com.team2169.robot.auto.tasks.drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.team2169.robot.Constants;
import com.team2169.robot.RobotStates.DriveType;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.auto.AutoConstants.RobotSide;
import com.team2169.robot.auto.tasks.Task;
import com.team2169.robot.subsystems.DriveTrain;
import com.team2169.util.PIDF;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnInPlace extends Task {

	private double angle = 0.0;
	private int i = 0;
	private PIDF pid;
	private DriveTrain drive;
	private double error;
	private double slope;
	private double intercept;

	// Constructor for turns with RobotSide object (used in side autos)
	public TurnInPlace(double angle, double speed, RobotSide side) {

		this.angle = angle;
		pid = new PIDF(Constants.driveTurnP, Constants.driveTurnI, Constants.driveTurnD, Constants.driveTurnF);
		pid.setSetpoint(angle);
		drive = DriveTrain.getInstance();

	}

	// Constructor for turns with an invert boolean (used in center autos)
	public TurnInPlace(double angle, double speed, boolean inverted) {
		if(inverted){
			this.angle = -angle;
		} else{
			this.angle = angle;
		}
		pid = new PIDF(Constants.driveTurnP, Constants.driveTurnI, Constants.driveTurnD, Constants.driveTurnF);
		pid.setSetpoint(angle);
		drive = DriveTrain.getInstance();

	}

	// Used for quick fixes and tests
	public TurnInPlace(double angle, double speed) {

		this.angle = angle;
		pid = new PIDF(Constants.driveTurnP, Constants.driveTurnI, Constants.driveTurnD, Constants.driveTurnF);
		pid.setSetpoint(angle);
		drive = DriveTrain.getInstance();

	}

	protected void initialize() {

		//Calculate Data about Speed Line
		slope = (Constants.turnMaxSpeed - Constants.turnMinSpeed) / (Constants.turnMaxError - Constants.turnMinError);
		intercept = Constants.turnMaxSpeed - (slope * Constants.turnMaxError);
		
		drive.resetGyro();
		RobotWantedStates.wantedDriveType = DriveType.EXTERNAL_DRIVING;

	}

	protected boolean isFinished() {

		// Make sure robot is in tolerance for 3 loops to verify it didn't just fly past
		// it.
		if (Math.abs(error) < Constants.driveTrainTurnAllowedError || this.isTimedOut()) {
			i++;
		} else {
			i = 0;
		}

		return i > 20;

	}

	protected void execute() {

		// Find Error and set the motors to the PID output
		error = drive.getAngle() - this.angle;
		System.out.println("Output: " + getMotorOutput(true));
		System.out.println("Error: " + error);
		drive.left.set(ControlMode.PercentOutput, getMotorOutput(false));
		drive.right.set(ControlMode.PercentOutput, getMotorOutput(true));
		SmartDashboard.putNumber("Turn Error", error);

	}

	private double getMotorOutput(boolean left) {

		// Get PID value
		double num = this.error;
		double dir = 1;

		// Turn Direction Multiplier around if going reverse
		if (num < 0) {
			dir = -1;
		}

		// Max. Error Speed Capping
		if (Math.abs(num) >= Constants.turnMaxError) {
			num = Constants.turnMaxSpeed * dir;
		} else {
			// Zero Speed
			if (Math.abs(num) <= Constants.turnZeroError) {
				num = 0;
			}
			// Not in Zero Speed, check if below minimum speed
			else if (Math.abs(num) <= Constants.turnMinError) {
				num = Constants.turnMinSpeed * dir;
			}
			// We're on the curve. Use that C U R V E
			else {
				num = ((Math.abs(num) * slope) + intercept) * dir;
			}
		}

		// Invert value for motor
		if (left) {
			return num;
		}
		return -num;

	}

	protected void end() {

		// Stop motors on end
		drive.left.set(ControlMode.PercentOutput, 0);
		drive.right.set(ControlMode.PercentOutput, 0);

	}

	protected void interrupted() {

		end();
	}
}