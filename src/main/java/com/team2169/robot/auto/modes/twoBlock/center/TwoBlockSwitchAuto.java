package com.team2169.robot.auto.modes.twoBlock.center;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.auto.AutoConstants;
import com.team2169.robot.auto.AutoConstants.ElementSide;
import com.team2169.robot.auto.modes.AutoMode;
import com.team2169.robot.auto.tasks.arm.ArmExtend;
import com.team2169.robot.auto.tasks.arm.ArmRetract;
import com.team2169.robot.auto.tasks.drive.DriveStraight;
import com.team2169.robot.auto.tasks.drive.TurnInPlace;
import com.team2169.robot.auto.tasks.elevator.ElevatorToGround;
import com.team2169.robot.auto.tasks.elevator.ElevatorToSwitch;
import com.team2169.robot.auto.tasks.intake.IntakeClampAction;
import com.team2169.robot.auto.tasks.intake.IntakeExhaust;
import com.team2169.robot.auto.tasks.intake.IntakeIn;
import com.team2169.robot.auto.tasks.intake.IntakeOpen;
import com.team2169.robot.auto.tasks.timer.StartTimer;
import com.team2169.robot.auto.tasks.timer.StopTimer;

import edu.wpi.first.wpilibj.Timer;


public class TwoBlockSwitchAuto extends AutoMode {
/*

	
       +-------+         +-------+
       |       |         |       |
       |       |         |       |
       |       |         |       |
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

	Timer time = new Timer();
	
    
	public TwoBlockSwitchAuto(ElementSide side) {

        RobotStates.runningMode = RunningMode.AUTO;
        this.autoName = "Center Switch Auto on " + side.name() + " side.";
        boolean inversion = false;
        
        addParallel(new StartTimer(time));
        
        if(side == ElementSide.RIGHT) {
        	inversion = true;
        	
	        addParallel(new ArmRetract());
	        addParallel(new ElevatorToSwitch());
	        addSequential(new DriveStraight(AutoConstants.CenterAutos.OneBlock.Right.startToPoint, 1));
	        addSequential(new TurnInPlace(AutoConstants.CenterAutos.OneBlock.Right.pointToSwitchTurn, .5), 1);
	        addSequential(new DriveStraight(AutoConstants.CenterAutos.OneBlock.Right.pointToPoint2, 1));
	        addSequential(new TurnInPlace(-AutoConstants.CenterAutos.OneBlock.Right.pointToSwitchTurn, .5), 1);
	        addSequential(new ArmExtend());
	        addSequential(new DriveStraight(AutoConstants.CenterAutos.OneBlock.Right.pointToSwitch, 1));
	        addSequential(new IntakeExhaust(AutoConstants.CenterAutos.OneBlock.intakeSpeed, true), 1);
	        addSequential(new DriveStraight(AutoConstants.CenterAutos.TwoBlock.switchToPoint, .5));
	        addParallel(new ElevatorToGround());
	        addParallel(new ArmExtend());
	        addSequential(new TurnInPlace(-AutoConstants.CenterAutos.TwoBlock.pointToPileTurn, 1, inversion), 1);
	        addParallel(new IntakeIn(AutoConstants.CenterAutos.OneBlock.intakeInSpeed, false));
	        addParallel(new IntakeOpen());
	        addSequential(new DriveStraight(AutoConstants.CenterAutos.TwoBlock.pointToPile, 1));
	        addSequential(new TurnInPlace(-15, 1, inversion));
	        addSequential(new IntakeClampAction());
	        addSequential(new DriveStraight(-AutoConstants.CenterAutos.TwoBlock.pointToPile, 1));
	        addParallel(new ElevatorToSwitch());
	        addSequential(new TurnInPlace(60, .5, inversion), 1);
	        addSequential(new DriveStraight(50, 1));
	        addParallel(new IntakeOpen(), 1);
	        addSequential(new IntakeExhaust(AutoConstants.CenterAutos.OneBlock.intakeSpeed, true), .5);
	        addSequential(new StopTimer(time));
	    } else if(side == ElementSide.LEFT) {
        	inversion = false;
	    	
	    	addParallel(new ArmRetract());
	        addParallel(new ElevatorToSwitch());
	        addSequential(new DriveStraight(AutoConstants.CenterAutos.OneBlock.Left.startToPoint, 1));
	        addSequential(new TurnInPlace(AutoConstants.CenterAutos.OneBlock.Left.pointToSwitchTurn, 1), 1);
	        addSequential(new DriveStraight(AutoConstants.CenterAutos.OneBlock.Left.pointToPoint2, 1));
	        addSequential(new TurnInPlace(-AutoConstants.CenterAutos.OneBlock.Left.pointToSwitchTurn, 1), 1);
	        addSequential(new ArmExtend());
	        addSequential(new DriveStraight(AutoConstants.CenterAutos.OneBlock.Left.pointToSwitch, 1));
	        addSequential(new IntakeExhaust(AutoConstants.CenterAutos.OneBlock.intakeSpeed, true), 1);
	        //Start 2 Blcok
	        addSequential(new DriveStraight(-75, .5));
	        addParallel(new ElevatorToGround());
	        addParallel(new ArmExtend());
	        addSequential(new TurnInPlace(-AutoConstants.CenterAutos.TwoBlock.pointToPileTurn, 1, inversion), 1);
	        addParallel(new IntakeIn(AutoConstants.CenterAutos.OneBlock.intakeInSpeed, false), 1);
	        addParallel(new IntakeOpen());
	        addSequential(new DriveStraight(60, 1));
	        addSequential(new TurnInPlace(-15, 1, inversion));
	        addSequential(new IntakeClampAction());
	        addSequential(new DriveStraight(-60, 1));
	        addParallel(new ElevatorToSwitch());
	        addSequential(new TurnInPlace(60, .5, inversion), 1.25);
	        addSequential(new DriveStraight(55, 1));
	        addParallel(new IntakeOpen(), 1);
	        addSequential(new IntakeExhaust(AutoConstants.CenterAutos.OneBlock.intakeSpeed, true), .5);
	        addSequential(new StopTimer(time));
	    }


        
        
    }

    // Put looping checks/code in here
    public void looper() {

        smartDashPush();

    }

    // Smartdashboard output
    public void smartDashPush() {

    }

}
