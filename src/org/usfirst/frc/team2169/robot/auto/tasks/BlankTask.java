package org.usfirst.frc.team2169.robot.auto.tasks;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class BlankTask extends Command {
	
	boolean finished;

    public BlankTask() {
    
    	finished = false;
    	
    	// Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if(finished /* || Put Control Override here (if it exists)*/) {
        	DriverStation.reportWarning("Task Cancelled", false);
        	return true;	
        }    
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
