package com.team2169.robot.auto.modes.switchAutos.right;

import com.team2169.robot.RobotStates;
import com.team2169.robot.auto.AutoConstants;
import com.team2169.robot.auto.modes.AutoMode;
import com.team2169.robot.auto.tasks.arm.ArmRetract;
import com.team2169.robot.auto.tasks.drive.DriveStraight;
import com.team2169.robot.auto.tasks.drive.TurnInPlace;
import com.team2169.robot.auto.tasks.intake.IntakeExhaust;

public class SwitchRRRAuto extends AutoMode {
/*

	
       +-------+         +-------+
       |       |         |-------|
       |       |         |-------|
       |       |         |-------|
       +-------+         +-------+
     	
       +-------+         +-------+
       |       |         |-------|
       |       |         |-------|
       |       |         |-------|
       +-------+         +-------+

                                   +-----+
                                   |     |
                                   |     |
                                   |     |
                                   +-----+

*/

    public SwitchRRRAuto() {

        RobotStates.runningMode = RobotStates.RunningMode.AUTO;
        addSequential(new DriveStraight(AutoConstants.sideInchesForwardFirst, .6));
        addParallel(new ArmRetract());
        addSequential(new TurnInPlace(-AutoConstants.sideDegreesFirst));
        addSequential(new DriveStraight(AutoConstants.sideInchesToSwitch, .4));
        addSequential(new IntakeExhaust(true), 3);


    }

    // Put looping checks/code in here
    public void looper() {

        smartDashPush();

    }

    // Smartdashboard output
    public void smartDashPush() {

    }

}
