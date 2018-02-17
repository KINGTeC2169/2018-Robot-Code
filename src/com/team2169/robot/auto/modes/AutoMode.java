package com.team2169.robot.auto.modes;

import edu.wpi.first.wpilibj.command.CommandGroup;

public abstract class AutoMode extends CommandGroup {

	public abstract void looper();

	public abstract void smartDashPush();

}
