package com.team2169.robot.auto.modes;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import edu.wpi.first.wpilibj.DriverStation;

public class SelfTest extends AutoMode {

    public SelfTest() {
        DriverStation.reportError("AUTOMODE - SELF", false);
        //addSequential(new FollowPath(Paths.example, false));

    }

    public void looper() {

        // Put looping checks/code in here

        smartDashPush();
        RobotStates.runningMode = RunningMode.AUTO;
    }

    public void smartDashPush() {

        // Put Smartdashboard output

        // SmartDashboard.putString("Running Auto: ", AutoManager.autoName);
    }

}
