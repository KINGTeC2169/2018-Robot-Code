package com.team2169.robot.auto.tasks.intake;

import com.team2169.robot.RobotStates.IntakeClamp;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.auto.tasks.Task;

public class IntakeClampAction extends Task {

	public IntakeClampAction() {

	}

	protected void initialize() {

		RobotWantedStates.wantedIntakeClamp = IntakeClamp.CLAMP;

	}

	@Override
	protected boolean isFinished() {
		return true;
	}

	protected void end() {

	}
}