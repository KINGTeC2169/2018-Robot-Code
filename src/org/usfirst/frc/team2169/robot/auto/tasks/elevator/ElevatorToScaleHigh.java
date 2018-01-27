package org.usfirst.frc.team2169.robot.auto.tasks.elevator;

import org.usfirst.frc.team2169.robot.RobotWantedStates;
import org.usfirst.frc.team2169.robot.RobotWantedStates.WantedElevatorPos;
import org.usfirst.frc.team2169.robot.auto.tasks.Task;

public class ElevatorToScaleHigh extends Task {

	public ElevatorToScaleHigh() {

    }

    protected void initialize() {
    	
    	RobotWantedStates.wantedElevatorPos = WantedElevatorPos.SCALE_HIGH;
    	
    }

	@Override
	protected boolean isFinished() {
		return false;
	}
	
    protected void end() {

    }
}
