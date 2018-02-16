package com.team2169.robot.auto.tasks.intake;

import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.RobotWantedStates.WantedIntakeClamp;
import com.team2169.robot.auto.tasks.Task;

public class IntakeDrop extends Task {

	public IntakeDrop() {

	}

    protected void initialize() {
    	
    	RobotWantedStates.wantedIntakeClamp = WantedIntakeClamp.DROP;
    	
    }

	@Override
	protected boolean isFinished() {
		return true;
	}
	
    protected void end() {
    	
    }
}