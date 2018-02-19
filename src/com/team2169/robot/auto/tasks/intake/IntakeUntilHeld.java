package com.team2169.robot.auto.tasks.intake;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.RobotWantedStates.WantedIntakeClamp;
import com.team2169.robot.RobotWantedStates.WantedIntakeMode;
import com.team2169.robot.auto.tasks.Task;

public class IntakeUntilHeld extends Task {
	
	public IntakeUntilHeld() {
		RobotWantedStates.wantedIntakeMode = WantedIntakeMode.INTAKE;
		RobotWantedStates.wantedIntakeClamp = WantedIntakeClamp.NEUTRAL;
	}

	protected void initialize() {
		RobotStates.canCycleMode = true;
	}

	protected boolean isFinished(){
		
		return RobotStates.ultraWithinRange;
		
	}
	
	protected void interrupted() {
		end();
	}

	protected void end() {
		RobotWantedStates.wantedIntakeMode = WantedIntakeMode.IDLE;
		RobotWantedStates.wantedIntakeClamp = WantedIntakeClamp.CLAMP;
	}

	public void smartDashPush() {

	}

}
