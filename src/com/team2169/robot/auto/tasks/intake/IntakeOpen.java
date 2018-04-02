package com.team2169.robot.auto.tasks.intake;

import com.team2169.robot.RobotStates.IntakeClamp;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.auto.tasks.Task;

public class IntakeOpen extends Task {

    public IntakeOpen() {

    }

    protected void initialize() {

        RobotWantedStates.wantedIntakeClamp = IntakeClamp.OPEN;

    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    protected void end() {

    }
}