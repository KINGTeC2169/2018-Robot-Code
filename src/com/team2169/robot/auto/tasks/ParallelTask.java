package com.team2169.robot.auto.tasks;

import java.util.List;

public class ParallelTask extends Task {

    private List<Task> masterTasks;

    public ParallelTask(List<Task> masterTasks_) {

        masterTasks = masterTasks_;

    }

    // Called just before this Command runs the first time
    protected void initialize() {
        for (Task t : masterTasks) {
            t.start();
        }
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    	System.out.println("Parallel Running: " + this.isRunning());
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	boolean stop = true;
        for (Task t : masterTasks) {
        	System.out.println(t.isRunning());
            if (t.isRunning()) {
                stop = false;
            }
        }
        
        System.out.println("Still Going: " + stop);
        
        return stop;
        
        
    }

    // Called once after isFinished returns true
    protected void end() {

        for (Task t : masterTasks) {
            t.cancel();
        }
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {

        for (Task t : masterTasks) {
            t.cancel();
        }
    }
}
