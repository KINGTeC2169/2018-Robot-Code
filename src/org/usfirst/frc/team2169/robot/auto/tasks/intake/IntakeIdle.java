package org.usfirst.frc.team2169.robot.auto.tasks.intake;

import org.usfirst.frc.team2169.robot.RobotWantedStates;
import org.usfirst.frc.team2169.robot.RobotWantedStates.WantedIntakeMode;
import org.usfirst.frc.team2169.robot.auto.tasks.Task;

public class IntakeIdle extends Task {

	public IntakeIdle() {

    }

    protected void initialize() {
    	
    	RobotWantedStates.wantedIntakeMode = WantedIntakeMode.IDLE;
    	
    }

	@Override
	protected boolean isFinished() {
		return true;
	}
	
    protected void end() {
    	
    }
}
