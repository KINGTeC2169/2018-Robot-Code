package com.team2169.robot.auto.tasks.intake;

import com.team2169.robot.RobotStates.IntakeMode;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.auto.tasks.Task;

public class IntakePin extends Task {

    public IntakePin() {

    }

    protected void initialize() {

        RobotWantedStates.wantedIntakeMode = IntakeMode.PIN;

    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    protected void end() {

    }
}
