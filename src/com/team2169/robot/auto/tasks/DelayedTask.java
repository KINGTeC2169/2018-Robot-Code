package com.team2169.robot.auto.tasks;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class DelayedTask extends Command {

	Timer timer = new Timer();
	Task task_;
	double delay_;
	boolean started = false;
	
    public DelayedTask(Task task, double delay) {
    	System.out.println("Created Timed Task!");
    	task_ = task;
    	delay_ = delay;
    }
    
    protected void initialize() {
    	timer.start();
    }
    
    protected void execute() {
    	if(timer.get() >= delay_ && !started) {
    		started = true;
    		System.out.println("Started Timed Task!");
    		task_.start();
    	}
    }
    
    protected void end() {
    	task_.cancel();
    }
    
    protected void interrupted() {
    	end();
    }

	@Override
	protected boolean isFinished() {
		return (started && !task_.isRunning()) || this.isTimedOut();
	}

}
