package com.team2169.robot.auto.modes.driveForward;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.auto.AutoConstants;
import com.team2169.robot.auto.modes.AutoMode;
import com.team2169.robot.auto.tasks.drive.DriveStraight;

public class OpenLoopDriveForward extends AutoMode {

    public OpenLoopDriveForward() {

		RobotStates.runningMode = RunningMode.AUTO;
		this.autoName = "Open Loop Drive Forward";
		addSequential(new DriveStraight(AutoConstants.driveForwardDistance, .5), 5);

    }

    // Put looping checks/code in here
    public void looper() {

        smartDashPush();

    }

    // Smartdashboard output
    public void smartDashPush() {

    }

}
