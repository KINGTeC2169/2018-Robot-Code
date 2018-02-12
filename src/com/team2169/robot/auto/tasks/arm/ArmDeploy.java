package com.team2169.robot.auto.tasks.arm;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.RobotWantedStates.WantedArmPos;
import com.team2169.robot.auto.tasks.Task;

public class ArmDeploy extends Task {

	public ArmDeploy() {

    }

    protected void initialize() {
    	
    	RobotWantedStates.wantedArmPos = WantedArmPos.EXTENDED;

    }

	@Override
	protected boolean isFinished() {
		return RobotStates.armInPosition;
	}
	
    protected void end() {

    }
}
