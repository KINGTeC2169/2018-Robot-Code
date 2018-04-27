package com.team2169.robot.auto.modes.driveForward;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.auto.modes.AutoMode;
import com.team2169.robot.auto.tasks.drive.DriveStraight;

import edu.wpi.first.wpilibj.command.WaitCommand;

public class DriveForwardAuto extends AutoMode {

    public DriveForwardAuto() {

		RobotStates.runningMode = RunningMode.AUTO;
		this.autoName = "Drive Forward";
		
		addSequential(new WaitCommand(10));
		addSequential(new DriveStraight(110, 1));
		//addSequential(new TurnInPlace(90, 1));
		
    }

    // Put looping checks/code in here
    public void looper() {

        smartDashPush();

    }

    // Smartdashboard output
    public void smartDashPush() {

    }

}
