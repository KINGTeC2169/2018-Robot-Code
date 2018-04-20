package com.team2169.robot.auto.modes.twoBlock.side;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.auto.AutoConstants;
import com.team2169.robot.auto.AutoConstants.RobotSide;
import com.team2169.robot.auto.modes.AutoMode;
import com.team2169.robot.auto.tasks.arm.ArmExtend;
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

	public TwoBlockScaleCloseAuto(RobotSide side) {
        
        RobotStates.runningMode = RunningMode.AUTO;
        this.autoName = "Close 2 Block Auto on " + side.name() + " side.";
        boolean inversion = true;

        if(side == RobotSide.RIGHT) {
        	inversion = false;
        }
        
        addSequential(new DriveStraight(AutoConstants.SideAutos.TwoBlockAutos.Close.startToPoint, 1), 4.5);
        addParallel(new ArmExtend());
        addParallel(new ElevatorToSwitch());
        
        addSequential(new TurnInPlace(AutoConstants.SideAutos.TwoBlockAutos.Close.pointToScaleTurn, .5, inversion), 2.25);
        addParallel(new ElevatorToScaleHigh());
        //addSequential(new DriveStraight(AutoConstants.SideAutos.TwoBlockAutos.Close.pointToScale, .5), 1);
        
        //addParallel(new IntakeOpen(), 0.5);
        addSequential(new IntakeExhaust(AutoConstants.SideAutos.TwoBlockAutos.Close.intakeSpeed, true), 0.3);
        //addSequential(new DriveStraight(-AutoConstants.SideAutos.TwoBlockAutos.Close.pointToScale, 0.5),3);
        addParallel(new IntakeOpen(), 0.5);
        
        addParallel(new ElevatorToGround());
        addSequential(new TurnInPlace(AutoConstants.SideAutos.TwoBlockAutos.Close.pointToBlockTurn, 0.5, inversion));
        addParallel(new IntakeIn(AutoConstants.SideAutos.TwoBlockAutos.Close.intakeInSpeed, false), 1);
        addSequential(new DriveStraight(AutoConstants.SideAutos.TwoBlockAutos.Close.pointToBlock, 0.75), 2);
        addParallel(new ElevatorToScaleHigh());
        addParallel(new IntakeClampAction());
        addSequential(new DriveStraight(-AutoConstants.SideAutos.TwoBlockAutos.Close.pointToBlock, 0.75), 3);
        addParallel(new IntakeIdle());

        addSequential(new TurnInPlace(-AutoConstants.SideAutos.TwoBlockAutos.Close.pointToBlockTurn, 0.5, inversion), 2);        //
        addParallel(new ArmExtend());
        //addParallel(new IntakeOpen());
        addSequential(new IntakeExhaust(AutoConstants.SideAutos.TwoBlockAutos.Close.intakeSpeed, true), 2);
        
    }

    // Put looping checks/code in here
    public void looper() {

        smartDashPush();

    }

    // Smartdashboard output
    public void smartDashPush() {

    }

}
