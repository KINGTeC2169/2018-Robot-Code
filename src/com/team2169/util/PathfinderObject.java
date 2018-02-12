package com.team2169.util;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import com.team2169.robot.Constants;
import com.team2169.robot.subsystems.DriveTrain;
import com.team2169.robot.subsystems.Superstructure;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PathfinderObject {
	
	//Waypoints go here
	Waypoint[] points;
	TalonSRX leftTalon;
	TalonSRX rightTalon;
	int leftID;
	int rightID;
	AHRS gyro;
	
	public boolean isFinished = false;
	
	public PathfinderObject(Waypoint[] importedPoints){
		points = importedPoints;
		leftTalon = DriveTrain.getInstance().leftMaster;
		rightTalon = DriveTrain.getInstance().rightMaster;
		leftID = leftTalon.getDeviceID();
		rightID = rightTalon.getDeviceID();
		gyro = Superstructure.getInstance().navX;
	}

	EncoderFollower leftFollower;
	EncoderFollower rightFollower;
	
	public void calculatePath() {
		
		leftTalon.set(ControlMode.PercentOutput, 0);
		rightTalon.set(ControlMode.PercentOutput, 0);
		gyro.reset();
	
		
		System.out.println("Creating config");
		Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_FAST,
			Constants.timeStep, Constants.maxVelocity, Constants.maxAcceleration, Constants.maxJerk);
		System.out.println("config created");
		System.out.println("generating path");
	// Generate the trajectory
	Trajectory trajectory = Pathfinder.generate(points, config);
	System.out.println("path generated");
	// Create the Modifier Object
	TankModifier modifier = new TankModifier(trajectory);

	// Generate the Left and Right trajectories using the original trajectory
	// as the center
	modifier.modify(Constants.wheelBaseWidth);
	Trajectory left  = modifier.getLeftTrajectory();
	Trajectory right = modifier.getRightTrajectory();
	
	
	//Make Encoder Followers
	leftFollower = new EncoderFollower(left);
	rightFollower = new EncoderFollower(right);
	
	leftFollower.configureEncoder(leftTalon.getSelectedSensorPosition(Constants.leftDriveData.slotIDx), Constants.ticksPerRotation, Constants.wheelDiameter);
	rightFollower.configureEncoder(rightTalon.getSelectedSensorPosition(Constants.rightDriveData.slotIDx), Constants.ticksPerRotation, Constants.wheelDiameter);
	
	//Configure Pathfinder PID
	leftFollower.configurePIDVA(Constants.pathfinderP, Constants.pathfinderI, Constants.pathfinderD, 1 / Constants.maxVelocity, Constants.accelerationGain);
	
	
	}
	
	public void pathfinderLooper() {
		double l = leftFollower.calculate(leftTalon.getSelectedSensorPosition(Constants.leftDriveData.slotIDx));
		double r = rightFollower.calculate(rightTalon.getSelectedSensorPosition(Constants.rightDriveData.slotIDx));

		double gyro_heading = gyro.getYaw();    // Assuming the gyro is giving a value in degrees
		double desired_heading = Pathfinder.r2d(leftFollower.getHeading());  // Should also be in degrees

		double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
		double turn = 0.8 * (-1.0/80.0) * angleDifference;

		//If left wheel trajectory isn't finished, set new power.
		if(!leftFollower.isFinished()) {
			leftTalon.set(ControlMode.PercentOutput,l + turn);
		}

		//If right wheel trajectory isn't finished, set new power.
		if(!rightFollower.isFinished()) {
			rightTalon.set(ControlMode.PercentOutput, r - turn);
		}
		
		SmartDashboard.putNumber("Left PathFinder Value", l + turn);
		SmartDashboard.putNumber("Right PathFinder Value", r - turn);
		
		//Return if trajectories are both finished
		if(leftFollower.isFinished() && rightFollower.isFinished()) {
			
			isFinished = true;
		
		}
		
		
		//
		else {
		
			isFinished = false;
		
		}
		
	}
		public void pathfinderLooper(int leftEnc, int rightEnc) {
			double l = leftFollower.calculate(leftEnc);
			double r = rightFollower.calculate(rightEnc);

			double gyro_heading = gyro.getYaw();    // Assuming the gyro is giving a value in degrees
			double desired_heading = Pathfinder.r2d(leftFollower.getHeading());  // Should also be in degrees

			double angleDifference = Pathfinder.boundHalfDegrees(desired_heading - gyro_heading);
			double turn = 0.8 * (-1.0/80.0) * angleDifference;

			//If left wheel trajectory isn't finished, set new power.
			if(!leftFollower.isFinished()) {
				leftTalon.set(ControlMode.PercentOutput,l + turn);
			}

			//If right wheel trajectory isn't finished, set new power.
			if(!rightFollower.isFinished()) {
				rightTalon.set(ControlMode.PercentOutput,r - turn);
			}
			
			//Return if trajectories are both finished
			if(leftFollower.isFinished() && rightFollower.isFinished()) {
				
				isFinished = true;
			
			}
	}
	
	
}

