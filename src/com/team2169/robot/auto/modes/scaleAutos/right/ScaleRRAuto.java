package com.team2169.robot.auto.modes.scaleAutos.right;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.auto.AutoConstants;
import com.team2169.robot.auto.modes.AutoMode;
import com.team2169.robot.auto.tasks.arm.ArmRetract;
import com.team2169.robot.auto.tasks.drive.DriveStraight;
import com.team2169.robot.auto.tasks.drive.TurnInPlace;
import com.team2169.robot.auto.tasks.elevator.ElevatorToScaleHigh;
import com.team2169.robot.auto.tasks.elevator.ElevatorToSwitch;
import com.team2169.robot.auto.tasks.intake.IntakeExhaust;
import com.team2169.robot.auto.tasks.intake.IntakeOpen;

public class ScaleRRAuto extends AutoMode {
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

    public ScaleRRAuto() {

    	 RobotStates.runningMode = RunningMode.AUTO;
    	 this.autoName = "Scale RR";
    	 addParallel(new ElevatorToSwitch());
         addParallel(new ArmRetract());
         addSequential(new DriveStraight(AutoConstants.RightAutos.ScaleAutos.RightScale.startToPoint, .5), 6);
         addParallel(new ElevatorToScaleHigh(), 2);
         addSequential(new TurnInPlace(.5, AutoConstants.RightAutos.ScaleAutos.RightScale.pointToScaleTurn), 1.5);
         addSequential(new DriveStraight(AutoConstants.RightAutos.ScaleAutos.RightScale.pointToScale, .5), 10);
         addParallel(new IntakeOpen());
         addSequential(new IntakeExhaust(AutoConstants.RightAutos.ScaleAutos.RightScale.intakeSpeed, true), 7);


    }

    // Put looping checks/code in here
    public void looper() {

        smartDashPush();

    }

    // Smartdashboard output
    public void smartDashPush() {

    }

}
