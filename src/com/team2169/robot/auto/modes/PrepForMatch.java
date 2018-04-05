package com.team2169.robot.auto.modes;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.auto.tasks.arm.ArmStow;
import edu.wpi.first.wpilibj.DriverStation;

@SuppressWarnings("unused")
public class PrepForMatch extends AutoMode {

    public PrepForMatch() {


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
