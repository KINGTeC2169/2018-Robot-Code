package com.team2169.robot.auto.tasks.intake;

import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.RobotWantedStates.WantedIntakeMode;
import com.team2169.robot.auto.tasks.Task;

public class IntakeExhaust extends Task {

	public IntakeExhaust() {

    }

    protected void initialize() {
    	
    	RobotWantedStates.wantedIntakeMode = WantedIntakeMode.EXHAUST;
    	
    }

	@Override
	protected boolean isFinished() {
		return true;
	}
	
    protected void end() {

    }
}
