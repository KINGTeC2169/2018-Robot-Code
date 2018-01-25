package org.usfirst.frc.team2169.robot.auto.tasks.elevator;

import org.usfirst.frc.team2169.robot.RobotWantedStates;
import org.usfirst.frc.team2169.robot.RobotWantedStates.WantedElevatorPos;
import org.usfirst.frc.team2169.robot.auto.tasks.Task;

public class ElevatorToScaleMid extends Task {

	public ElevatorToScaleMid() {

    }

    protected void initialize() {
    	
    	RobotWantedStates.wantedElevatorPos = WantedElevatorPos.SCALE_MID;
    	
    }

	@Override
	protected boolean isFinished() {
		return false;
	}
	
    protected void end() {
    	cancelTasks();
    }
}
