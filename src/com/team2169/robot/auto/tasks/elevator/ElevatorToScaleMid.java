package com.team2169.robot.auto.tasks.elevator;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.RobotWantedStates.WantedMacro;
import com.team2169.robot.auto.tasks.Task;

public class ElevatorToScaleMid extends Task {

	public ElevatorToScaleMid() {

    }

    protected void initialize() {
    	
    	RobotWantedStates.wantedElevatorPos = WantedMacro.SCALE_MID;
    	
    }

	@Override
	protected boolean isFinished() {
		return RobotStates.elevatorInPosition;
	}
	
    protected void end() {

    }
}
