package com.team2169.robot.auto.tasks.intake;

import com.team2169.robot.RobotStates.IntakeMode;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.auto.tasks.Task;

public class IntakeExhaust extends Task {

	boolean idleOnEnd;

	public IntakeExhaust(boolean idleOnEnd_) {

		idleOnEnd = idleOnEnd_;

	}

	protected void initialize() {

		RobotWantedStates.wantedIntakeMode = IntakeMode.EXHAUST;

	}

	@Override
	protected boolean isFinished() {

		if (idleOnEnd) {
			return false;
		}
		return true;

	}

	protected void end() {

		if (idleOnEnd) {
			RobotWantedStates.wantedIntakeMode = IntakeMode.IDLE;
		}

	}
}
