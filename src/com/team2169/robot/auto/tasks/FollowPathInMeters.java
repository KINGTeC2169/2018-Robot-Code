package com.team2169.robot.auto.tasks;

import jaci.pathfinder.Waypoint;

import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.DriveType;
import com.team2169.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.DriverStation;

public class FollowPathInMeters extends Task {

	public double RightPos;
	public double LeftPos;
	DriveTrain drive = DriveTrain.getInstance();
	public FollowPathInMeters(Waypoint[] points) {
		RobotStates.currentPath = points;
		for(Waypoint h : RobotStates.currentPath){
			h.x = h.x * 0.0254;
			h.y = h.y * 0.0254;
		}
		
		DriverStation.reportWarning("Path Created", false);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		DriverStation.reportWarning("Calculating path", false);
		RobotWantedStates.wantedDriveType = DriveType.WANTS_TO_FOLLOW_PATH;
		DriverStation.reportWarning("Path Calculated", false);
		

	}

	// Called repeatedly when this Command is scheduled to run
	
	protected void execute() {
		if (RobotStates.driveType == DriveType.WAITING) {
			RobotWantedStates.wantedDriveType = DriveType.FOLLOW_PATH;
			DriverStation.reportError("Starting Path", false);
		}
	}
	

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return drive.getIsProfileFinished();
	}

	// Called once after isFinished returns true
	protected void end() {
		System.out.println("Path Finished");
		RobotWantedStates.wantedDriveType = DriveType.NORMAL_DRIVING;
		drive.stopPath();
		drive.stop();
	}

	// Called when another Command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		RobotWantedStates.wantedDriveType = DriveType.NORMAL_DRIVING;
		drive.stop();
		drive.stopPath();
	}
}