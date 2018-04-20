package com.team2169.robot.auto.tasks.elevator;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.Macro;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.auto.tasks.Task;

public class ElevatorToGround extends Task {

    public ElevatorToGround() {

    }

    protected void initialize() {

        RobotWantedStates.wantedElevatorPos = Macro.GROUND;

    }
    @Override
    protected boolean isFinished() {
        return  RobotStates.elevatorInPosition;
    }

    protected void end() {

    }
}
