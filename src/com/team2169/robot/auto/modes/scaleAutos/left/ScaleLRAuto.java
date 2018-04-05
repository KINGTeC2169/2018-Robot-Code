package com.team2169.robot.auto.modes.scaleAutos.left;

import java.util.Arrays;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.auto.AutoConstants;
import com.team2169.robot.auto.modes.AutoMode;
import com.team2169.robot.auto.tasks.ParallelTask;
import com.team2169.robot.auto.tasks.Task;
import com.team2169.robot.auto.tasks.arm.ArmRetract;
import com.team2169.robot.auto.tasks.drive.DriveStraight;
import com.team2169.robot.auto.tasks.drive.TurnInPlace;
import com.team2169.robot.auto.tasks.elevator.ElevatorToScaleHigh;
import com.team2169.robot.auto.tasks.elevator.ElevatorToSwitch;
import com.team2169.robot.auto.tasks.intake.IntakeExhaust;

public class ScaleLRAuto extends AutoMode {
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

+----+
|    |
|    |
|    |
+----+
	 
*/

    public ScaleLRAuto() {
    	
    	this.autoName = "Scale LR";
    	
        RobotStates.runningMode = RunningMode.AUTO;
        /*addSequential(new ParallelTask(Arrays.asList(new Task[] {
                new DriveStraight(AutoConstants.LeftAutos.ScaleAutos.RightScale.startToPoint, .5),
                new ArmRetract(),
                new ElevatorToSwitch(),        
        })));
        addSequential(new TurnInPlace(.5, AutoConstants.LeftAutos.ScaleAutos.RightScale.pointToPoint2Turn));
        addSequential(new DriveStraight(AutoConstants.LeftAutos.ScaleAutos.RightScale.pointToPoint2, .5));
        addSequential(new ParallelTask(Arrays.asList(new Task[] {
        		new TurnInPlace(.25, AutoConstants.LeftAutos.ScaleAutos.RightScale.point2ToScaleTurn),
                new ElevatorToScaleHigh(),        
        })), 2);
        addSequential(new DriveStraight(AutoConstants.LeftAutos.ScaleAutos.RightScale.point2ToScale, .25));
        addSequential(new IntakeExhaust(AutoConstants.LeftAutos.ScaleAutos.RightScale.intakeSpeed, true), 2);
         */
    }

    // Put looping checks/code in here
    public void looper() {

        smartDashPush();

    }

    // Smartdashboard output
    public void smartDashPush() {

    }

}
