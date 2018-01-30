package org.usfirst.frc.team2169.robot.auto.tasks;

import java.util.List;

import edu.wpi.first.wpilibj.Timer;

public class ParallelTask extends Task {
	
	Timer taskTime;
	List<Task> masterTasks;
	List<Task> slaveTasks;
	double timeout;
	boolean timeoutActive = false;
	
    public ParallelTask(List<Task> masterTasks_, List<Task> slaveTasks_, double timeout_) {
  
    	masterTasks = masterTasks_;
    	slaveTasks = slaveTasks_;
    	timeout = timeout_;
    	taskTime = new Timer();
    	taskTime.start();
    	
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	for(Task t: masterTasks) {
    		t.start();
    	}
    	for(Task t: slaveTasks) {
    		t.start();
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

    	//Check Timeout
    	if(taskTime.get() >= timeout) {
    		timeoutActive = true;
    		return true;
    	}
    	
    	//Timeout Clear, see if Master Tasks are finished
    	else {
    		for(Task t: masterTasks) {
    			if(t.isRunning()) {
    				return false;
    			}
    		}
    		return true;
    	}
    
    }
    // Called once after isFinished returns true
    protected void end() {
    	for(Task t: slaveTasks) {
    		t.cancel();
    	}
    	
    	if(timeoutActive) {
        	for(Task t: masterTasks) {
        		t.cancel();
        	}
    	}
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
