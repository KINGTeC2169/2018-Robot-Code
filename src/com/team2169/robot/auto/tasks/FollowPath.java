package com.team2169.robot.auto.tasks;

import jaci.pathfinder.Waypoint;

import com.team2169.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.DriverStation;

public class FollowPath extends Task {

	public double RightPos;
	public double LeftPos;
	DriveTrain drive = DriveTrain.getInstance();
	public FollowPath(Waypoint[] points) {

		DriveTrain.getInstance().SetWaypoint(points);

		DriverStation.reportWarning("Path Created", false);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		DriverStation.reportWarning("calculating path", false);
		DriveTrain.getInstance().calculatePath();
		DriverStation.reportWarning("Path Calculated", false);

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {

		DriverStation.reportError("Executing path", false);
		DriveTrain.getInstance().pathfinderLooper();

	}
	

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return DriveTrain.getInstance().isPathFinished;
	}

	// Called once after isFinished returns true
	protected void end() {
		System.out.println("Path Finished");
		DriveTrain.getInstance().stop();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		DriveTrain.getInstance().stop();
		DriveTrain.getInstance().stopPath();
	}
}
