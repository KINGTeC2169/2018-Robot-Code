package com.team2169.robot.auto.tasks;

import java.util.List;

import com.team2169.robot.RobotStates;

public class NestedPathTask extends Task {
	
	double startPercent;
	double endPercent;
	List<Task> tasks;
	
	public NestedPathTask(List<Task> tasks_, double startPercent_, double endPercent_) {

		tasks = tasks_;
		startPercent = startPercent_;
		endPercent = endPercent_;
		
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		
		if(RobotStates.leftPathCompletionPercent >= startPercent && RobotStates.rightPathCompletionPercent >= startPercent)
		for(Task t: tasks) {
			t.start();
		}

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if(RobotStates.leftPathCompletionPercent >= endPercent && RobotStates.rightPathCompletionPercent >= endPercent) {
			return true;	
		}
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		
		for(Task t: tasks) {
			t.cancel();
		}
	
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		end();
	}
}
