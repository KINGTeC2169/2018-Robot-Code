package com.team2169.robot.auto.tasks.intake;

import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.RobotWantedStates.WantedIntakeMode;
import com.team2169.robot.auto.tasks.Task;

public class IntakeIn extends Task {

	public IntakeIn() {

    }

    protected void initialize() {
    	
    	RobotWantedStates.wantedIntakeMode = WantedIntakeMode.INTAKE;
    	
    }

	@Override
	protected boolean isFinished() {
		return true;
	}
	
    protected void end() {

    }
}
