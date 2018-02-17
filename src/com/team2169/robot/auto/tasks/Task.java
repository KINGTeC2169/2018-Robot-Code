package com.team2169.robot.auto.tasks;

import edu.wpi.first.wpilibj.command.Command;

public abstract class Task extends Command {

	boolean cancelled;

	public Task() {

		cancelled = false;

		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
	}

}
