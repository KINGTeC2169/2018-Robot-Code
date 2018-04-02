package com.team2169.robot.auto.tasks.drive;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.DriveType;
import com.team2169.robot.auto.tasks.Task;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.DriverStation;
import jaci.pathfinder.Waypoint;

public class FollowPath extends Task {

    private DriveTrain drive = DriveTrain.getInstance();

    public FollowPath(Waypoint[] points, boolean reverse_) {

        RobotStates.reverseCurrentPath = reverse_;
        RobotStates.currentPath = points;
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
    	
    }


    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return this.isTimedOut();
    }

    // Called once after isFinished returns true
    protected void end() {
        System.out.println("Path Finished");
        RobotWantedStates.wantedDriveType = DriveType.NORMAL_DRIVING;
        drive.stop();
    }

    // Called when another Command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
        end();
    }
}