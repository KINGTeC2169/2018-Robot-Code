package com.team2169.robot.canCycles;

import com.team2169.robot.RobotStates;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class CanCycle extends CommandGroup {

    protected void initialize() {
        RobotStates.canCycleMode = false;
    }

    protected void interrupted() {
        RobotStates.canCycleMode = false;
    }

    protected void end() {
        RobotStates.canCycleMode = false;
    }

}
