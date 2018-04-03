package com.team2169.robot.auto.modes;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.auto.tasks.drive.OpenLoopDrive;
import com.team2169.robot.auto.tasks.intake.IntakeExhaust;

import edu.wpi.first.wpilibj.DriverStation;

public class YesNoAutoRight extends AutoMode {

	public YesNoAutoRight() {

		RobotStates.runningMode = RunningMode.AUTO;
		addSequential(new OpenLoopDrive(), 3.2);

		if (DriverStation.getInstance().getGameSpecificMessage().startsWith("R")) {
			System.out.println("Exhausting");
			addSequential(new IntakeExhaust(.75, true), 3);

		}
	}
	// This is where you put tasks

	// Put looping checks/code in here
	public void looper() {

		smartDashPush();

	}

	// Smartdashboard output
	public void smartDashPush() {
	}

}
