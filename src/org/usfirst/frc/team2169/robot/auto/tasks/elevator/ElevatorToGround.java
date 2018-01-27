package org.usfirst.frc.team2169.robot.auto.tasks.elevator;

import org.usfirst.frc.team2169.robot.RobotWantedStates;
import org.usfirst.frc.team2169.robot.RobotWantedStates.WantedElevatorPos;
import org.usfirst.frc.team2169.robot.auto.tasks.Task;

public class ElevatorToGround extends Task {

	public ElevatorToGround() {

    }

    protected void initialize() {
    	
    	RobotWantedStates.wantedElevatorPos = WantedElevatorPos.GROUND;
    	
    }

	@Override
	protected boolean isFinished() {
		return false;
	}
	
    protected void end() {

    }
}
