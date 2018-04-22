package com.team2169.robot.auto.tasks.elevator;

import com.team2169.robot.RobotStates.Macro;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.auto.tasks.Task;

public class ElevatorToScaleLow extends Task {

    public ElevatorToScaleLow() {

    }

    protected void initialize() {

        RobotWantedStates.wantedElevatorPos = Macro.SCALE_LOW;

    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    protected void end() {

    }
}
