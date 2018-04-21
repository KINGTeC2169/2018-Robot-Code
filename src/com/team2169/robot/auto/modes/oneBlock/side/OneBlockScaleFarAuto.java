package com.team2169.robot.auto.modes.oneBlock.side;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.auto.AutoConstants;
import com.team2169.robot.auto.AutoConstants.RobotSide;
import com.team2169.robot.auto.modes.AutoMode;
import com.team2169.robot.auto.tasks.arm.ArmExtend;
import com.team2169.robot.auto.tasks.drive.DriveStraight;
import com.team2169.robot.auto.tasks.drive.TurnInPlace;
import com.team2169.robot.auto.tasks.elevator.ElevatorToScaleHigh;
import com.team2169.robot.auto.tasks.elevator.ElevatorToSwitch;
import com.team2169.robot.auto.tasks.intake.IntakeExhaust;
import com.team2169.robot.auto.tasks.intake.IntakeOpen;
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
    	addParallel(new StartTimer(time));
        addParallel(new ArmExtend());
        addParallel(new ElevatorToSwitch());
        addSequential(new DriveStraight(AutoConstants.SideAutos.OneBlockAutos.FarScale.startToPoint, .75), 4);       
        addSequential(new TurnInPlace(AutoConstants.SideAutos.OneBlockAutos.FarScale.pointToPoint2Turn, .5, side), 2);
        addSequential(new DriveStraight(AutoConstants.SideAutos.OneBlockAutos.FarScale.pointToPoint2, .75), 4);
        addParallel(new ElevatorToScaleHigh());
        addSequential(new TurnInPlace(AutoConstants.SideAutos.OneBlockAutos.FarScale.point2ToScaleTurn, .4, side), 2);
        addSequential(new DriveStraight(AutoConstants.SideAutos.OneBlockAutos.FarScale.point2ToScale, .25), 4);
        addParallel(new IntakeOpen());
        addSequential(new IntakeExhaust(AutoConstants.SideAutos.OneBlockAutos.FarScale.intakeSpeed, true), 2);
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
