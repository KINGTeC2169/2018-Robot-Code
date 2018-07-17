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
import com.team2169.robot.auto.tasks.elevator.ElevatorToScaleLow;
import com.team2169.robot.auto.tasks.intake.IntakeExhaust;
import com.team2169.robot.auto.tasks.intake.IntakeOpen;
import com.team2169.robot.auto.tasks.intake.IntakePin;
import com.team2169.robot.auto.tasks.timer.StartTimer;
import com.team2169.robot.auto.tasks.timer.StopTimer;

import edu.wpi.first.wpilibj.Timer;

public class SwitchFarAuto extends AutoMode {
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
	
    public SwitchFarAuto(RobotSide side) {
        
    	RobotStates.runningMode = RunningMode.AUTO;
        this.autoName = "Far Side Switch Auto on " + side.name() + " side.";
        boolean inversion = false;

        if(side == RobotSide.LEFT) {
        	inversion = true;
        }
        
        
        //Pull up Arm, Pin intake, and start the timer
        addParallel(new StartTimer(time));
        addParallel(new ArmRetract());
        addParallel(new IntakePin());
        addSequential(new DriveStraight(AutoConstants.SideAutos.OneBlockAutos.FarSwitch.startToPoint, 1));
        
        //Turn in place
        addSequential(new TurnInPlace(AutoConstants.SideAutos.OneBlockAutos.FarSwitch.pointToPoint2Turn, 0.5, inversion));
        
        //Drive Straight to the second point
        addSequential(new DriveStraight(AutoConstants.SideAutos.OneBlockAutos.FarSwitch.pointToPoint2, 1));
        
        //Bring up elevator and turn to the scale
        addParallel(new ElevatorToScaleLow());
        addSequential(new TurnInPlace(AutoConstants.SideAutos.OneBlockAutos.FarSwitch.point2ToSwitchTurn, 0.5, inversion));
        
        //Drive to scale and outake block
        addSequential(new DriveStraight(AutoConstants.SideAutos.OneBlockAutos.FarSwitch.point2ToSwitch, .5));
        addSequential(new IntakeExhaust(AutoConstants.SideAutos.OneBlockAutos.FarSwitch.intakeSpeed, true), 0.5);
        
        //Turn around and bring the elevator down
        addParallel(new DelayedTask(new ElevatorToGround(), 1));
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
