package com.team2169.robot.auto.tasks.arm;

import com.team2169.robot.RobotStates.ArmPos;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.auto.tasks.Task;

public class ArmExtend extends Task {

    public ArmExtend() {

    }

    protected void initialize() {

        RobotWantedStates.wantedArmPos = ArmPos.EXTEND;
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    protected void end() {

    }
}
