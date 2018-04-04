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

import edu.wpi.first.wpilibj.command.WaitCommand;

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
        addSequential(new ParallelTask(Arrays.asList(new Task[] {
                new DriveStraight(AutoConstants.RightAutos.ScaleAutos.RightScale.startToPoint, .5),
                new ArmRetract(),
                new ElevatorToSwitch(),        
        })));
        addSequential(new ParallelTask(Arrays.asList(new Task[] {
        		new TurnInPlace(.5, AutoConstants.RightAutos.ScaleAutos.RightScale.pointToScaleTurn),
                new ArmRetract(),
                new ElevatorToScaleHigh(),        
        })), 2);
        addSequential(new DriveStraight(AutoConstants.RightAutos.ScaleAutos.RightScale.pointToScale, .5));
        addSequential(new WaitCommand(1));
        addSequential(new IntakeExhaust(AutoConstants.RightAutos.ScaleAutos.RightScale.intakeSpeed, true), 2);


    }

    // Put looping checks/code in here
    public void looper() {

        smartDashPush();

    }

    // Smartdashboard output
    public void smartDashPush() {

    }

}