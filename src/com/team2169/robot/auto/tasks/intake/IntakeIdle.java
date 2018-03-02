package com.team2169.robot.auto.tasks.intake;

import com.team2169.robot.RobotStates.IntakeMode;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.auto.tasks.Task;

public class IntakeIdle extends Task {

	public IntakeIdle() {

	}

	protected void initialize() {

		RobotWantedStates.wantedIntakeMode = IntakeMode.IDLE;

	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	protected void end() {

	}
}
