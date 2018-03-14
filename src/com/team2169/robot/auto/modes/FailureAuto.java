package com.team2169.robot.auto.modes;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;

public class FailureAuto extends AutoMode {

    public FailureAuto() {

        RobotStates.runningMode = RunningMode.AUTO;
        // This is where you put tasks

    }

    // Put looping checks/code in here
    public void looper() {

        smartDashPush();

    }

    // Smartdashboard output
    public void smartDashPush() {

    }

}
