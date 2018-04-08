package com.team2169.robot.auto.modes;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.auto.tasks.arm.ArmExtend;
import com.team2169.robot.auto.tasks.drive.DriveStraight;
import com.team2169.robot.auto.tasks.drive.TurnInPlace;
import com.team2169.robot.auto.tasks.elevator.ElevatorToSwitch;
import com.team2169.robot.auto.tasks.intake.IntakeClampAction;
import com.team2169.robot.auto.tasks.intake.IntakeExhaust;
import com.team2169.robot.auto.tasks.intake.IntakeOpen;

public class SelfTest extends AutoMode {

    public SelfTest() {
    	
    	this.autoName = "Self Test";
        addParallel(new ElevatorToSwitch());
        addSequential(new ArmExtend());
        addParallel(new IntakeOpen());
    	addSequential(new IntakeExhaust(.5, true), 2);
    	addSequential(new DriveStraight(24, .5));
    	addSequential(new TurnInPlace(.25, 30));
    	addSequential(new IntakeClampAction());
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
