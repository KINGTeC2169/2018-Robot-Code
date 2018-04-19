package com.team2169.robot.auto.modes.driveForward;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.auto.AutoConstants.RobotSide;
import com.team2169.robot.auto.modes.AutoMode;
import com.team2169.robot.auto.tasks.drive.TurnInPlace;

public class DriveForwardAuto extends AutoMode {

    public DriveForwardAuto() {

		RobotStates.runningMode = RunningMode.AUTO;
		this.autoName = "Drive Forward";
		//addSequential(new DriveStraight(120, .5));
		addSequential(new TurnInPlace(90, .7, RobotSide.LEFT));
		//addSequential(new DriveStraight(120, .5));
		//addSequential(new DriveStraight(120, .5));

    }

    // Put looping checks/code in here
    public void looper() {

        smartDashPush();

    }

    // Smartdashboard output
    public void smartDashPush() {

    }

}
