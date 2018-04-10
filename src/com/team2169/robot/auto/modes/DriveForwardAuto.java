package com.team2169.robot.auto.modes;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;

public class DriveForwardAuto extends AutoMode {

    public DriveForwardAuto() {

		RobotStates.runningMode = RunningMode.AUTO;
		this.autoName = "Drive Forward";
		addSequential(new OpenLoopDriveForward(), 5);

    }

    // Put looping checks/code in here
    public void looper() {

        smartDashPush();

    }

    // Smartdashboard output
    public void smartDashPush() {

    }

}
