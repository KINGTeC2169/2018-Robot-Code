package org.usfirst.frc.team2169.robot.auto.tasks.elevator;

import org.usfirst.frc.team2169.robot.RobotWantedStates;
import org.usfirst.frc.team2169.robot.RobotWantedStates.WantedElevatorPos;
import org.usfirst.frc.team2169.robot.auto.tasks.Task;

public class ElevatorToScaleLow extends Task {

	public ElevatorToScaleLow() {

    }

    protected void initialize() {
    	
    	RobotWantedStates.wantedElevatorPos = WantedElevatorPos.SCALE_LOW;
    	
    }

	@Override
	protected boolean isFinished() {
		return false;
	}
	
    protected void end() {
    	cancelTasks();
    }
}
