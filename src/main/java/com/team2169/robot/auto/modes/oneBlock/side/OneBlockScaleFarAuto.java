package com.team2169.robot.auto.modes.oneBlock.side;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.auto.AutoConstants;
import com.team2169.robot.auto.AutoConstants.RobotSide;
import com.team2169.robot.auto.modes.AutoMode;
import com.team2169.robot.auto.tasks.DelayedTask;
import com.team2169.robot.auto.tasks.arm.ArmExtend;
import com.team2169.robot.auto.tasks.arm.ArmRetract;
import com.team2169.robot.auto.tasks.drive.DriveStraight;
import com.team2169.robot.auto.tasks.drive.TurnInPlace;
import com.team2169.robot.auto.tasks.elevator.ElevatorToGround;
import com.team2169.robot.auto.tasks.elevator.ElevatorToScaleHigh;
import com.team2169.robot.auto.tasks.intake.IntakeExhaust;
import com.team2169.robot.auto.tasks.intake.IntakeOpen;
import com.team2169.robot.auto.tasks.intake.IntakePin;
import com.team2169.robot.auto.tasks.timer.StartTimer;
import com.team2169.robot.auto.tasks.timer.StopTimer;

import edu.wpi.first.wpilibj.Timer;

public class OneBlockScaleFarAuto extends AutoMode {
/*

	
       +-------+         +-------+
       |       |         |-------|
       |       |         |-------|
       |	   |         |-------|
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
	
    public OneBlockScaleFarAuto(RobotSide side) {
    	
    	RobotStates.runningMode = RunningMode.AUTO;
    	this.autoName = "Far Scale Auto on " + side.name() + " side.";
        boolean inversion = false;

        if(side == RobotSide.LEFT) {
        	inversion = true;
        }
        
        
        //Pull up Arm, Pin intake, and start the timer
        addParallel(new StartTimer(time));
        addParallel(new ArmRetract());
        addParallel(new IntakePin());
        addSequential(new DriveStraight(AutoConstants.SideAutos.OneBlockAutos.FarScale.startToPoint, 1));
        
        //Turn in place
        addSequential(new TurnInPlace(AutoConstants.SideAutos.OneBlockAutos.FarScale.pointToPoint2Turn, 0.5, inversion));
        
        //Drive Straight to the second point
        addSequential(new DriveStraight(AutoConstants.SideAutos.OneBlockAutos.FarScale.pointToPoint2, 1));
        
        //Bring up elevator and turn to the scale
        addParallel(new ElevatorToScaleHigh());
        addSequential(new TurnInPlace(AutoConstants.SideAutos.OneBlockAutos.FarScale.point2ToScaleTurn, 0.5, inversion));
        
        //Drive to scale and outake block
        addSequential(new DriveStraight(AutoConstants.SideAutos.OneBlockAutos.FarScale.point2ToScale, .5));
        addSequential(new IntakeExhaust(AutoConstants.SideAutos.OneBlockAutos.FarScale.intakeSpeed, true), 0.5);
        
        //Turn around and bring the elevator down
        addParallel(new DelayedTask(new ElevatorToGround(), 1));
        addParallel(new DelayedTask(new ArmExtend(), 1));
        addParallel(new IntakeOpen());
        addSequential(new DriveStraight(-12, .5));
        addSequential(new TurnInPlace(180, 1));
        
        //Stop the timer
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
