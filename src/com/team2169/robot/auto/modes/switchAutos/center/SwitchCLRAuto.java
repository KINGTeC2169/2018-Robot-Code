package com.team2169.robot.auto.modes.switchAutos.center;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.auto.AutoConstants;
import com.team2169.robot.auto.modes.AutoMode;
import com.team2169.robot.auto.tasks.drive.DriveStraight;
import com.team2169.robot.auto.tasks.drive.TurnInPlace;
import com.team2169.robot.auto.tasks.intake.IntakeExhaust;


public class SwitchCLRAuto extends AutoMode {
/*

	
       +-------+         +-------+
       |       |         |-------|
       |       |         |-------|
       |	   |         |-------|
       +-------+         +-------+

       +-------+         +-------+
       |-------|         |       |
       |-------|         |       |
       |-------|         |       |
       +-------+         +-------+

                 +-----+      
                 |     |      
                 |     |      
                 |     |      
                 +-----+      

*/

    public SwitchCLRAuto() {

        RobotStates.runningMode = RunningMode.AUTO;
        addSequential(new DriveStraight(AutoConstants.CenterAutos.SwitchAutos.LeftSwitch.startToPoint, .5));
        addSequential(new TurnInPlace(.5, AutoConstants.CenterAutos.SwitchAutos.LeftSwitch.pointToSwitchTurn), 2);
        addSequential(new DriveStraight(AutoConstants.CenterAutos.SwitchAutos.LeftSwitch.pointToSwitch, .5));
        addSequential(new TurnInPlace(.5, -AutoConstants.CenterAutos.SwitchAutos.LeftSwitch.pointToSwitchTurn), 2);
        addSequential(new IntakeExhaust(true), 2);
        
    }

    // Put looping checks/code in here
    public void looper() {

        smartDashPush();

    }

    // Smartdashboard output
    public void smartDashPush() {

    }

}
