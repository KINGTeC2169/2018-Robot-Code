package com.team2169.robot.auto.modes;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.auto.AutoConstants;
import com.team2169.robot.auto.tasks.drive.DriveStraight;
import com.team2169.robot.auto.tasks.drive.TurnInPlace;
import com.team2169.robot.auto.tasks.intake.IntakeExhaust;

import edu.wpi.first.wpilibj.DriverStation;

public class SelfTest extends AutoMode {

    public SelfTest() {
        DriverStation.reportError("AUTOMODE - SELF", false);
        addSequential(new DriveStraight(AutoConstants.CenterAutos.SwitchAutos.LeftSwitch.startToPoint, .5));
        addSequential(new TurnInPlace(.5, AutoConstants.CenterAutos.SwitchAutos.LeftSwitch.pointToSwitchTurn), 2);
        addSequential(new DriveStraight(AutoConstants.CenterAutos.SwitchAutos.LeftSwitch.pointToSwitch, .5));
        addSequential(new IntakeExhaust(.75, true), 2);
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
