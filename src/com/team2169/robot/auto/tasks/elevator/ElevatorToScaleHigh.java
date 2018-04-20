package com.team2169.robot.auto.tasks.elevator;

import com.team2169.robot.RobotStates.Macro;
import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.auto.tasks.Task;


public class ElevatorToScaleHigh extends Task {

    public ElevatorToScaleHigh() {

    }

    protected void initialize() {

        RobotWantedStates.wantedElevatorPos = Macro.SCALE_HIGH;

    }

    @Override
    protected boolean isFinished() {
        return RobotStates.elevatorInPosition;
    }

    protected void end() {

    }
}
