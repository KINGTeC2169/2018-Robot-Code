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
import com.team2169.robot.auto.tasks.elevator.ElevatorToScaleLow;
import com.team2169.robot.auto.tasks.intake.IntakeClampAction;
import com.team2169.robot.auto.tasks.intake.IntakeExhaust;
import com.team2169.robot.auto.tasks.intake.IntakeIn;
import com.team2169.robot.auto.tasks.intake.IntakeOpen;
import com.team2169.robot.auto.tasks.intake.IntakePin;
import com.team2169.robot.auto.tasks.timer.StartTimer;
import com.team2169.robot.auto.tasks.timer.StopTimer;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class TwoBlockScaleCloseAuto extends AutoMode {
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
	
	public TwoBlockScaleCloseAuto(RobotSide side) {
		
        RobotStates.runningMode = RunningMode.AUTO;
        this.autoName = "Close 2 Block Auto on " + side.name() + " side.";
        boolean inversion = true;

        if(side == RobotSide.RIGHT) {
        	inversion = false;
        }
        
        //To Start,  put down the arm and move up the elevator and drive to first point
        addParallel(new StartTimer(time));
        addParallel(new IntakePin());
        addParallel(new ArmRetract());
        addParallel(new ElevatorToScaleLow());        
        addSequential(new DriveStraight(AutoConstants.SideAutos.TwoBlockAutos.Close.startToPoint, 1), 4.5);
        addParallel(new ElevatorToScaleHigh());
        
        //We're at the scale, turn and bring the elevator all the way up
        addSequential(new TurnInPlace(AutoConstants.SideAutos.TwoBlockAutos.Close.pointToScaleTurn, .5, inversion), 2.25);
        

        //Exhaust (shoot) and then open 
        addSequential(new IntakeExhaust(AutoConstants.SideAutos.TwoBlockAutos.Close.intakeSpeed, true), 0.3);
        addSequential(new IntakeOpen(), 0.5);
        
        //Turn and bring elevator to the ground to prep to pick up block
        addParallel(new ElevatorToGround());
        addParallel(new ArmExtend());
        addSequential(new TurnInPlace(AutoConstants.SideAutos.TwoBlockAutos.Close.pointToBlockTurn, 0.5, inversion));
        
        //Start running intake in and bring the arm down
        addParallel(new ArmExtend());
        addParallel(new IntakeIn(AutoConstants.SideAutos.TwoBlockAutos.Close.intakeInSpeed, false), 1);
        //Drive to block
        addSequential(new DriveStraight(AutoConstants.SideAutos.TwoBlockAutos.Close.pointToBlock, 0.75), 2);
        
        //Clamp block, wait 1/4 of a second and start to bring elevator up
        addSequential(new IntakeClampAction());
        addSequential(new WaitCommand(.25));
        
        //Back up to scale and stop the intakes
        addParallel(new ElevatorToScaleHigh());
        addParallel(new DelayedTask(new ArmRetract(), 2));
        addParallel(new IntakeIn(AutoConstants.SideAutos.TwoBlockAutos.Close.intakeSpeed,false));
        addSequential(new DriveStraight(-AutoConstants.SideAutos.TwoBlockAutos.Close.pointToBlock, 0.5), 3);

        //Turn to the scale
        addParallel(new IntakePin());
        addSequential(new TurnInPlace(-AutoConstants.SideAutos.TwoBlockAutos.Close.pointToBlockTurn, 0.5, inversion), 1.25);        //
        
        //Extend the arm, wait for it to go down, and shoot
        addSequential(new IntakeExhaust(AutoConstants.SideAutos.TwoBlockAutos.Close.intakeSpeed, true), 1);
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
