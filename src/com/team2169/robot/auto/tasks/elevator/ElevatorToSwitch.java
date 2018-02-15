package com.team2169.robot.auto.tasks.elevator;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.RobotWantedStates.WantedMacro;
import com.team2169.robot.auto.tasks.Task;

public class ElevatorToSwitch extends Task {

	public ElevatorToSwitch() {

    }

    protected void initialize() {
    	
    	RobotWantedStates.wantedElevatorPos = WantedMacro.SWITCH;
    	
    }

	@Override
	protected boolean isFinished() {
		return RobotStates.elevatorInPosition;
	}
	
    protected void end() {

    }
}
