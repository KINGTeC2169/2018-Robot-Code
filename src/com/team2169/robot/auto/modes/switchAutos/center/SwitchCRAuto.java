package com.team2169.robot.auto.modes.switchAutos.center;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.auto.AutoConstants;
import com.team2169.robot.auto.modes.AutoMode;
import com.team2169.robot.auto.tasks.arm.ArmRetract;
import com.team2169.robot.auto.tasks.drive.DriveStraight;
import com.team2169.robot.auto.tasks.drive.TurnInPlace;
import com.team2169.robot.auto.tasks.elevator.ElevatorToSwitch;
import com.team2169.robot.auto.tasks.intake.IntakeExhaust;

public class SwitchCRAuto extends AutoMode {
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

    public SwitchCRAuto() {

        RobotStates.runningMode = RunningMode.AUTO;
        this.autoName = "Switch CR";
        addParallel(new ArmRetract());
        addParallel(new ElevatorToSwitch());
        addSequential(new DriveStraight(AutoConstants.CenterAutos.SwitchAutos.RightSwitch.startToPoint, .5));
        addSequential(new TurnInPlace(.5, AutoConstants.CenterAutos.SwitchAutos.RightSwitch.pointToSwitchTurn), 1);
        addSequential(new DriveStraight(AutoConstants.CenterAutos.SwitchAutos.RightSwitch.pointToSwitch, .5));
        addSequential(new TurnInPlace(.5, -AutoConstants.CenterAutos.SwitchAutos.RightSwitch.pointToSwitchTurn), 1);
        addSequential(new IntakeExhaust(AutoConstants.CenterAutos.SwitchAutos.RightSwitch.intakeSpeed, true), 2);
     
    }

    // Put looping checks/code in here
    public void looper() {

        smartDashPush();

    }

    // Smartdashboard output
    public void smartDashPush() {

    }

}
