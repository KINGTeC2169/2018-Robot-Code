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

public class ScaleFarAuto extends AutoMode {
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

+----+
|    |
|    |
|    |
+----+
	 
*/

    public ScaleFarAuto(RobotSide side) {
    	
    	RobotStates.runningMode = RunningMode.AUTO;
    	this.autoName = "Far Scale Auto on " + side.name() + " side.";
    	
        addParallel(new ArmExtend());
        addParallel(new ElevatorToSwitch());
        addSequential(new DriveStraight(AutoConstants.SideAutos.FarScale.startToPoint, .75), 4);       
        addSequential(new TurnInPlace(AutoConstants.SideAutos.FarScale.pointToPoint2Turn, .5, side), 2);
        addSequential(new DriveStraight(AutoConstants.SideAutos.FarScale.pointToPoint2, .75), 4);
        addParallel(new ElevatorToScaleHigh());
        addSequential(new TurnInPlace(AutoConstants.SideAutos.FarScale.point2ToScaleTurn, .4, side), 2);
        addSequential(new DriveStraight(AutoConstants.SideAutos.FarScale.point2ToScale, .25), 4);
        addParallel(new IntakeOpen());
        addSequential(new IntakeExhaust(AutoConstants.SideAutos.FarScale.intakeSpeed, true), 2);
    }

    // Put looping checks/code in here
    public void looper() {

        smartDashPush();

    }

    // Smartdashboard output
    public void smartDashPush() {

    }

}
