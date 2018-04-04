package com.team2169.robot.auto.tasks.intake;

import com.team2169.robot.RobotStates.IntakeMode;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.auto.AutoConstants;
import com.team2169.robot.auto.tasks.Task;

public class IntakeExhaust extends Task {

    private boolean idleOnEnd;
    private double speed;

    public IntakeExhaust(double speed_, boolean idleOnEnd_) {

    	speed = speed_;
        idleOnEnd = idleOnEnd_;

    }

    protected void initialize() {

    	AutoConstants.desireIntakeSpeed = speed;
        RobotWantedStates.wantedIntakeMode = IntakeMode.EXHAUST;

    }

    @Override
    protected boolean isFinished() {

        return isTimedOut();

    }

    protected void end() {

    	System.out.println("DONE INTAKING");
        if (idleOnEnd) {
            RobotWantedStates.wantedIntakeMode = IntakeMode.IDLE;
        }

    }
}
