package com.team2169.robot.auto.tasks.arm;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.ArmPos;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.auto.tasks.Task;

public class ArmConfig extends Task {

    public ArmConfig() {

    }

    protected void initialize() {

        RobotWantedStates.wantedArmPos = ArmPos.CONFIG;

    }

    @Override
    protected boolean isFinished() {
        return RobotStates.armPos == ArmPos.HOLD_POSITION;
    }

    protected void end() {

    }
}