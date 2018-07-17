package com.team2169.robot.auto.tasks.arm;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.ArmPos;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.auto.tasks.Task;

public class ArmStow extends Task {

    public ArmStow() {

    }

    protected void initialize() {

        RobotWantedStates.wantedArmPos = ArmPos.STOW;
    }

    @Override
    protected boolean isFinished() {
        return RobotStates.armInPosition;
    }

    protected void end() {

    }
}
