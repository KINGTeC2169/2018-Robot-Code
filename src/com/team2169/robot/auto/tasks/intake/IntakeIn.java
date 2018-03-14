package com.team2169.robot.auto.tasks.intake;

import com.team2169.robot.RobotStates.IntakeMode;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.auto.tasks.Task;

@SuppressWarnings("unused")
public class IntakeIn extends Task {

    private boolean idleOnEnd;

    public IntakeIn(boolean idleOnEnd_) {

        idleOnEnd = idleOnEnd_;

    }

    protected void initialize() {

        RobotWantedStates.wantedIntakeMode = IntakeMode.INTAKE;

    }

    @Override
    protected boolean isFinished() {

        return !idleOnEnd;

    }

    protected void end() {

        if (idleOnEnd) {
            RobotWantedStates.wantedIntakeMode = IntakeMode.IDLE;
        }

    }
}
