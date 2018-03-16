package com.team2169.robot.auto.modes.switchAutos.center;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.auto.AutoConstants;
import com.team2169.robot.auto.modes.AutoMode;
import com.team2169.robot.auto.tasks.drive.DriveStraight;
import com.team2169.robot.auto.tasks.drive.TurnInPlace;
import com.team2169.robot.auto.tasks.elevator.ElevatorToSwitch;
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
        addSequential(new DriveStraight(AutoConstants.CenterAutos.SwitchAutos.RightSwitch.pointToSwitch, .5));
        addParallel(new TurnInPlace(AutoConstants.CenterAutos.SwitchAutos.RightSwitch.pointToSwitchTurn));
        addParallel(new ElevatorToSwitch());
        addSequential(new DriveStraight(AutoConstants.CenterAutos.SwitchAutos.RightSwitch.pointToSwitch, .5));
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
