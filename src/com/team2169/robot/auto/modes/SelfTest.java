package com.team2169.robot.auto.modes;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.auto.tasks.arm.ArmRetract;
import com.team2169.robot.auto.tasks.drive.DriveStraight;
import com.team2169.robot.auto.tasks.drive.TurnInPlace;
import com.team2169.robot.auto.tasks.intake.IntakeExhaust;

import edu.wpi.first.wpilibj.DriverStation;

public class SelfTest extends AutoMode {

	public SelfTest() {
		DriverStation.reportError("AUTOMODE - SELF", false);
		// addSequential(new TimeTask(5, "Waiting To Start"));
		addSequential(new DriveStraight(36, .6));
		addParallel(new ArmRetract());
		addSequential(new TurnInPlace(45));
		addSequential(new DriveStraight(72, .7));
		addSequential(new IntakeExhaust(true), 3);

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
