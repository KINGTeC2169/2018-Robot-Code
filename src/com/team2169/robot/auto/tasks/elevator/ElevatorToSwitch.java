package com.team2169.robot.auto.tasks.elevator;

import com.team2169.robot.RobotStates.Macro;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.auto.tasks.Task;

public class ElevatorToSwitch extends Task {

    public ElevatorToSwitch() {

    }

    protected void initialize() {

        RobotWantedStates.wantedElevatorPos = Macro.SWITCH;

    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    protected void end() {

    }
}
