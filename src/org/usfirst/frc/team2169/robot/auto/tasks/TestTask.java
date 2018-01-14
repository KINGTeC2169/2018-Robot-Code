package org.usfirst.frc.team2169.robot.auto.tasks;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TestTask extends Command {

	int i;
	boolean finished;
    public TestTask() {
    	i=0;
    	finished = false;
    	DriverStation.reportWarning("Command Created", false);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	DriverStation.reportWarning("Initializing task", false);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	DriverStation.reportWarning("Ran Task " + i + " times", false);
    	i++;
    	if(i>5) {
    		finished = true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return finished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Task Finished");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
