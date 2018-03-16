package com.team2169.robot.auto.modes;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.auto.tasks.drive.OpenLoopDrive;

public class DriveForwardAuto extends AutoMode {

    public DriveForwardAuto() {

		RobotStates.runningMode = RunningMode.AUTO;
		addSequential(new OpenLoopDrive(), 3.2);
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
