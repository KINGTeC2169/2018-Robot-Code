package com.team2169.robot.auto.tasks;

import jaci.pathfinder.Waypoint;

import com.team2169.robot.subsystems.DriveTrain;
import com.team2169.util.PathfinderObject;

import edu.wpi.first.wpilibj.DriverStation;

public class FollowPath extends Task {

	PathfinderObject path;
	
    public FollowPath(Waypoint[] points) {
    
    	path = new PathfinderObject(points);
    	
    	DriverStation.reportWarning("Path Created", false);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	DriverStation.reportWarning("calculating path", false);
    	path.calculatePath();
    	DriverStation.reportWarning("Path Calculated", false);
    		
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	DriverStation.reportError("Name Jeff", false);
    	path.pathfinderLooper();
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return path.isFinished;
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
    }
}
