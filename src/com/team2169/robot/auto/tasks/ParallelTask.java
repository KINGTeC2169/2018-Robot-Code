package com.team2169.robot.auto.tasks;

import java.util.List;

import edu.wpi.first.wpilibj.Timer;

public class ParallelTask extends Task {

	Timer taskTime;
	List<Task> masterTasks;

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

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		for (Task t : masterTasks) {
			if (t.isRunning()) {
				return false;
			}
		}
		return true;
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
