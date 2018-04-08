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
import com.team2169.robot.auto.tasks.intake.IntakeOpen;


public class SwitchCLAuto extends AutoMode {
/*

	
       +-------+         +-------+
       |-------|         |       |
       |-------|         |       |
       |-------|         |       |
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

    public SwitchCLAuto() {

        RobotStates.runningMode = RunningMode.AUTO;
        this.autoName = "Switch CL";
        addParallel(new ArmRetract());
        addParallel(new ElevatorToSwitch());
        addSequential(new DriveStraight(AutoConstants.CenterAutos.SwitchAutos.LeftSwitch.startToPoint, .5));
        addSequential(new TurnInPlace(.5, AutoConstants.CenterAutos.SwitchAutos.LeftSwitch.pointToSwitchTurn), 1.5);
        addSequential(new DriveStraight(AutoConstants.CenterAutos.SwitchAutos.LeftSwitch.pointToPoint2, .5));
        addSequential(new TurnInPlace(.5, -AutoConstants.CenterAutos.SwitchAutos.LeftSwitch.pointToSwitchTurn), 1.5);
        addSequential(new DriveStraight(AutoConstants.CenterAutos.SwitchAutos.LeftSwitch.pointToSwitch, .5));
        addParallel(new IntakeOpen());
        addSequential(new IntakeExhaust(AutoConstants.CenterAutos.SwitchAutos.LeftSwitch.intakeSpeed, true), 2);
        
    }

    // Put looping checks/code in here
    public void looper() {

        smartDashPush();

    }

    // Smartdashboard output
    public void smartDashPush() {

    }

}
