package com.team2169.robot.auto.modes.oneBlock.side;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.auto.AutoConstants;
import com.team2169.robot.auto.AutoConstants.RobotSide;
import com.team2169.robot.auto.modes.AutoMode;
import com.team2169.robot.auto.tasks.DelayedTask;
import com.team2169.robot.auto.tasks.arm.ArmRetract;
import com.team2169.robot.auto.tasks.drive.DriveStraight;
import com.team2169.robot.auto.tasks.drive.TurnInPlace;
import com.team2169.robot.auto.tasks.elevator.ElevatorToGround;
import com.team2169.robot.auto.tasks.elevator.ElevatorToScaleHigh;
import com.team2169.robot.auto.tasks.elevator.ElevatorToScaleLow;
import com.team2169.robot.auto.tasks.intake.IntakeExhaust;
import com.team2169.robot.auto.tasks.intake.IntakeOpen;
import com.team2169.robot.auto.tasks.intake.IntakePin;
import com.team2169.robot.auto.tasks.timer.StartTimer;
import com.team2169.robot.auto.tasks.timer.StopTimer;

import edu.wpi.first.wpilibj.Timer;

public class OneBlockScaleCloseAuto extends AutoMode {
/*

	
       +-------+         +-------+
       |-------|         |       |
       |-------|         |       |
       |-------|         |       |
       +-------+         +-------+

       +-------+         +-------+
       |       |         |       |
       |       |         |       |
       |       |         |       |
       +-------+         +-------+

+----+
|    |
|    |
|    |
+----+
	 
*/
	Timer time = new Timer();
	
    public OneBlockScaleCloseAuto(RobotSide side) {

    	RobotStates.runningMode = RunningMode.AUTO;
    	this.autoName = "Scale Close on " + side.name() + " side.";
        boolean inversion = false;

        if(side == RobotSide.LEFT) {
        	inversion = true;
        }
        
    	
        //To Start,  put down the arm and move up the elevator and drive to first point
        addParallel(new StartTimer(time));
        addParallel(new IntakePin());
        addParallel(new ArmRetract());
        addParallel(new ElevatorToScaleLow());        
        addSequential(new DriveStraight(AutoConstants.SideAutos.OneBlockAutos.CloseScale.startToPoint, 1), 4.5);
        
        //We're at the scale, turn and bring the elevator all the way up
        addParallel(new ElevatorToScaleHigh());
        addSequential(new TurnInPlace(AutoConstants.SideAutos.OneBlockAutos.CloseScale.pointToScaleTurn, .5, inversion), 2.25);
        

        //Exhaust (shoot) and then open 
        addSequential(new IntakeExhaust(AutoConstants.SideAutos.OneBlockAutos.CloseScale.intakeSpeed, true), 0.3);
        //addSequential(new IntakeOpen(), 0.5);
        
        //Turn around and bring the elevator down
        addParallel(new DelayedTask(new ElevatorToGround(), 2));
        addParallel(new IntakeOpen());
        addSequential(new DriveStraight(-12, .5));
        addSequential(new TurnInPlace(100, 1));

        //Stop Timer
        addSequential(new StopTimer(time));

    }

    // Put looping checks/code in here
    public void looper() {

        smartDashPush();

    }

    // Smartdashboard output
    public void smartDashPush() {

    }

}
