package com.team2169.robot.auto.modes.switchAutos.center;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.auto.modes.AutoMode;
import com.team2169.robot.auto.tasks.drive.DriveStraight;
import com.team2169.robot.auto.tasks.drive.TurnInPlace;
import com.team2169.robot.auto.tasks.intake.IntakeExhaust;


public class SwitchCRLAuto extends AutoMode {
/*

	
       +-------+         +-------+
       |-------|         |       |
       |-------|         |       |
       |-------|         |       |
       +-------+         +-------+
       	
       +-------+         +-------+
       |       |         |-------|
       |       |         |-------|
       |	   |         |-------|
       +-------+         +-------+

                 +-----+      
                 |     |      
                 |     |      
                 |     |      
                 +-----+      
	 
*/

    public SwitchCRLAuto() {

        RobotStates.runningMode = RunningMode.AUTO;
        addSequential(new DriveStraight(36, .5));
        addSequential(new TurnInPlace(.5, 45), 2);
        addSequential(new DriveStraight(48, .5));
        addSequential(new TurnInPlace(.5, -45), 2);
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
