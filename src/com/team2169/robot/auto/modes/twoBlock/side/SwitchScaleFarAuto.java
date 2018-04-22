package com.team2169.robot.auto.modes.twoBlock.side;

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
import com.team2169.robot.auto.tasks.elevator.ElevatorToSwitch;
import com.team2169.robot.auto.tasks.intake.IntakeClampAction;
import com.team2169.robot.auto.tasks.intake.IntakeExhaust;
import com.team2169.robot.auto.tasks.intake.IntakeIdle;
import com.team2169.robot.auto.tasks.intake.IntakeIn;
import com.team2169.robot.auto.tasks.intake.IntakeOpen;
import com.team2169.robot.auto.tasks.timer.StartTimer;
import com.team2169.robot.auto.tasks.timer.StopTimer;

import edu.wpi.first.wpilibj.Timer;

public class SwitchScaleFarAuto extends AutoMode {
/*

	
       +-------+         +-------+
       |       |         |-------|
       |       |         |-------|
       |	   |         |-------|
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

    
	Timer time = new Timer();
	
	public SwitchScaleFarAuto(RobotSide side) {
        
        RobotStates.runningMode = RunningMode.AUTO;
        this.autoName = "Fars Switch/Scale Auto on " + side.name() + " side.";
        boolean inversion = false;

        if(side == RobotSide.LEFT) {
        	inversion = true;
        }
        
        addParallel(new StartTimer(time));
        addParallel(new ArmRetract());
        addSequential(new DriveStraight(AutoConstants.SideAutos.TwoBlockAutos.Far.startToPoint, 0.5));
        addSequential(new TurnInPlace(AutoConstants.SideAutos.TwoBlockAutos.Far.pointToPoint2Turn, 0.5, inversion));
        addSequential(new DriveStraight(AutoConstants.SideAutos.TwoBlockAutos.Far.pointToPoint2, 0.5));
        addSequential(new TurnInPlace(AutoConstants.SideAutos.TwoBlockAutos.Far.point2ToScaleTurn, 0.5, inversion));
        addParallel(new ElevatorToScaleHigh());
        addSequential(new DriveStraight(AutoConstants.SideAutos.TwoBlockAutos.Far.point2ToScale, 0.5));
        addParallel(new IntakeOpen());
        addSequential(new IntakeExhaust(AutoConstants.SideAutos.TwoBlockAutos.Far.intakeSpeed, true), 0.5);
        addSequential(new DriveStraight(AutoConstants.SideAutos.TwoBlockAutos.Far.point2ToPoint3, 0.5));
        addParallel(new ElevatorToGround());
        addParallel(new ArmExtend());
        addSequential(new TurnInPlace(AutoConstants.SideAutos.TwoBlockAutos.Far.point3ToBlockTurn, 0.5, inversion));
        addParallel(new IntakeIn(AutoConstants.SideAutos.TwoBlockAutos.Far.intakeInSpeed, false));
        addSequential(new DriveStraight(AutoConstants.SideAutos.TwoBlockAutos.Far.point3ToBlock, 0.5));
        addParallel(new IntakeClampAction());
        addParallel(new IntakeIdle());
        addParallel(new ElevatorToSwitch());
        addSequential(new DriveStraight(AutoConstants.SideAutos.TwoBlockAutos.Far.ScaleSwitchFar.BlockToSwitch, 0.5));
        addParallel(new IntakeOpen());
        addSequential(new IntakeExhaust(AutoConstants.SideAutos.TwoBlockAutos.Far.intakeSpeed, true), 2);
        
        //Turn around and bring the elevator down
        addParallel(new DelayedTask(new ElevatorToGround(), 2));
        addParallel(new IntakeOpen());
        addSequential(new DriveStraight(-12, .5));
        addSequential(new TurnInPlace(180, 1));
        
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
