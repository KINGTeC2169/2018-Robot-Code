package com.team2169.util;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.KTEncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team2169.robot.Constants;
import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.PathfinderState;
import com.team2169.robot.subsystems.DriveTrain;


import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PathfinderObject {

	// Waypoints go here
	Waypoint[] points;
	TalonSRX leftTalon;
	TalonSRX rightTalon;
	//int leftID;
	//int rightID;

	public boolean isFinished = false;

	public PathfinderObject(Waypoint[] importedPoints) {
		points = importedPoints;
		leftTalon = DriveTrain.getInstance().leftMaster;
		rightTalon = DriveTrain.getInstance().rightMaster;
		//leftID = leftTalon.getDeviceID();
		//rightID = rightTalon.getDeviceID();
		isFinished = false;
		RobotStates.pathfinderState = PathfinderState.INITIALIZING;
	}

	public KTEncoderFollower leftFollower;
	public KTEncoderFollower rightFollower;

	public void calculatePath() {
		isFinished = false;
		if (RobotState.isAutonomous()) {
			RobotStates.pathfinderState = PathfinderState.CALCULATING_PATH;
			leftTalon.set(ControlMode.PercentOutput, 0);
			rightTalon.set(ControlMode.PercentOutput, 0);


			Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,
					Trajectory.Config.SAMPLES_FAST, Constants.timeStep, Constants.maxVelocity,
					Constants.maxAcceleration, Constants.maxJerk);

			// Generate the trajectory
			Trajectory trajectory = Pathfinder.generate(points, config);

			// Create the Modifier Object
			TankModifier modifier = new TankModifier(trajectory);

			// Generate the Left and Right trajectories using the original trajectory
			// as the center
			modifier.modify(Constants.wheelBaseWidth);
			Trajectory left = modifier.getLeftTrajectory();
			Trajectory right = modifier.getRightTrajectory();

			// Make Encoder Followers
			leftFollower = new KTEncoderFollower(left);
			rightFollower = new KTEncoderFollower(right);

			leftFollower.configureEncoder(leftTalon.getSelectedSensorPosition(Constants.leftDriveData.slotIDx),
					Constants.ticksPerRotation, Constants.wheelDiameter);
			rightFollower.configureEncoder(rightTalon.getSelectedSensorPosition(Constants.rightDriveData.slotIDx),
					Constants.ticksPerRotation, Constants.wheelDiameter);

			// Configure Pathfinder PID
			leftFollower.configurePIDVA(Constants.pathfinderP, Constants.pathfinderI, Constants.pathfinderD,
					1 / Constants.maxVelocity, Constants.accelerationGain);
			

		}
	}

	public void pathfinderLooper() {
		
		if (RobotState.isAutonomous()) {
			double l = leftFollower.calculate(leftTalon.getSelectedSensorPosition(Constants.leftDriveData.slotIDx));
			double r = rightFollower.calculate(rightTalon.getSelectedSensorPosition(Constants.rightDriveData.slotIDx));

			double gyro_heading = RobotStates.GyroAngle; // Assuming the gyro is giving a value in degrees
			double desired_heading = Pathfinder.r2d(leftFollower.getHeading()); // Should also be in degrees

			double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
			double turn = 0.5 * (-1.0 / 80.0) * angleDifference;

			// If left wheel trajectory isn't finished, set new power.
			if (!leftFollower.isFinished() && !rightFollower.isFinished()) {
				leftTalon.set(ControlMode.PercentOutput, l + turn);
				rightTalon.set(ControlMode.PercentOutput, r - turn);

			}

			SmartDashboard.putNumber("Pathfinder Left Percentage", leftFollower.getCompletionPercentage());
			SmartDashboard.putNumber("Pathfinder Right Percentage", rightFollower.getCompletionPercentage());
			SmartDashboard.putNumber("Left PathFinder Value + turn", l + turn);
			SmartDashboard.putNumber("Right PathFinder Value - turn", r - turn);
			SmartDashboard.putNumber("Left PathFinder Value", l);
			SmartDashboard.putNumber("Right PathFinder Value", r);
			SmartDashboard.putNumber("Pathfinder Turn", turn);

			RobotStates.leftPathCompletionPercent = leftFollower.getCompletionPercentage();
			RobotStates.rightPathCompletionPercent = rightFollower.getCompletionPercentage();

			// Return if trajectories are both finished
			if (leftFollower.isFinished() && rightFollower.isFinished()) {
				RobotStates.leftPathCompletionPercent = leftFollower.getCompletionPercentage();
				RobotStates.rightPathCompletionPercent = rightFollower.getCompletionPercentage();
				RobotStates.pathfinderState = PathfinderState.STOPPED;
				RobotStates.leftPathCompletionPercent = 1;
				RobotStates.rightPathCompletionPercent = 1;
				isFinished = true;
				leftTalon.set(ControlMode.PercentOutput,  0);
				rightTalon.set(ControlMode.PercentOutput, 0);

			} else {

				RobotStates.pathfinderState = PathfinderState.LOOPING;

			}

		}
	}

	public void Stop() {
		isFinished = true;
	}

	public boolean isPercentComplete(double percent) {
		percent = percent / 100;
		if (leftFollower.getCompletionPercentage() >= percent && leftFollower.getCompletionPercentage() >= percent) {
			return true;
		}
		return false;
	}

	public void pathfinderLooper(int leftEnc, int rightEnc) {

		if (RobotState.isAutonomous()) {
			double l = leftFollower.calculate(leftEnc);
			double r = rightFollower.calculate(rightEnc);

			double gyro_heading = RobotStates.GyroAngle; // Assuming the gyro is giving a value in degrees
			double desired_heading = Pathfinder.r2d(leftFollower.getHeading()); // Should also be in degrees

			double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
			double turn = 0.8 * (-1.0 / 80.0) * angleDifference;

			// If left wheel trajectory isn't finished, set new power.
			if (!leftFollower.isFinished()) {
				leftTalon.set(ControlMode.PercentOutput, l + turn);
				SmartDashboard.putNumber("leftPathfinder", l + turn);
			}

			// If right wheel trajectory isn't finished, set new power.
			if (!rightFollower.isFinished()) {
				rightTalon.set(ControlMode.PercentOutput, r - turn);
				SmartDashboard.putNumber("rightPathfinder", r - turn);
			}

			// Return if trajectories are both finished
			if (leftFollower.isFinished() && rightFollower.isFinished()) {

				isFinished = true;
				RobotStates.pathfinderState = PathfinderState.LOOPING;

			}

		}
	}
}
