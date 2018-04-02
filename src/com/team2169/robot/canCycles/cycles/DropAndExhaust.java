package com.team2169.robot.canCycles.cycles;

import com.team2169.robot.RobotStates;
import com.team2169.robot.auto.tasks.arm.ArmExtend;
import com.team2169.robot.auto.tasks.intake.IntakeOpen;
import com.team2169.robot.auto.tasks.intake.IntakeExhaust;
import com.team2169.robot.canCycles.CanCycle;

public class DropAndExhaust extends CanCycle {

    public DropAndExhaust() {

        addSequential(new ArmExtend(), 1);
        addSequential(new IntakeOpen());
        addSequential(new IntakeExhaust(true), .25);

    }

    protected void initialize() {
        RobotStates.canCycleMode = true;
    }

    protected void interrupted() {
        end();
    }

    protected void end() {
        RobotStates.canCycleMode = false;
    }

    public void smartDashPush() {

    }

}
