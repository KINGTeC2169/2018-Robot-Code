package com.team2169.robot.auto.tasks.intake;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.IntakeClamp;
import com.team2169.robot.RobotStates.IntakeMode;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.auto.tasks.Task;

public class IntakeUntilHeld extends Task {
	
	public IntakeUntilHeld() {
		RobotWantedStates.wantedIntakeMode = IntakeMode.INTAKE;
		RobotWantedStates.wantedIntakeClamp = IntakeClamp.NEUTRAL;
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
		RobotWantedStates.wantedIntakeMode = IntakeMode.IDLE;
		RobotWantedStates.wantedIntakeClamp = IntakeClamp.CLAMP;
	}

	public void smartDashPush() {

	}

}
