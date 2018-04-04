package com.team2169.robot.auto.modes.scaleAutos.right;

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

public class ScaleRLAuto extends AutoMode {
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

    public ScaleRLAuto() {
        RobotStates.runningMode = RunningMode.AUTO;
        addSequential(new ParallelTask(Arrays.asList(new Task[] {
                new DriveStraight(AutoConstants.RightAutos.ScaleAutos.LeftScale.startToPoint, .5),
                new ArmRetract(),
                new ElevatorToSwitch(),        
        })));
        addSequential(new TurnInPlace(.5, AutoConstants.RightAutos.ScaleAutos.LeftScale.pointToPoint2Turn));
        addSequential(new DriveStraight(AutoConstants.RightAutos.ScaleAutos.LeftScale.pointToPoint2, .5));
        addSequential(new ParallelTask(Arrays.asList(new Task[] {
        		new TurnInPlace(.25, AutoConstants.RightAutos.ScaleAutos.LeftScale.point2ToScaleTurn),
                new ElevatorToScaleHigh(),        
        })), 2);
        addSequential(new DriveStraight(AutoConstants.RightAutos.ScaleAutos.LeftScale.point2ToScale, .25));
        addSequential(new IntakeExhaust(AutoConstants.RightAutos.ScaleAutos.LeftScale.intakeSpeed, true), 2);
    }

    // Put looping checks/code in here
    public void looper() {

        smartDashPush();

    }

    // Smartdashboard output
    public void smartDashPush() {

    }

}
