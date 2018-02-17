package com.team2169.robot.auto.tasks.intake;

import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.RobotWantedStates.WantedIntakeClamp;
import com.team2169.robot.auto.tasks.Task;

public class IntakeClamp extends Task {

	public IntakeClamp() {

	}

	protected void initialize() {

		RobotWantedStates.wantedIntakeClamp = WantedIntakeClamp.CLAMP;

	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	protected void end() {

	}
}