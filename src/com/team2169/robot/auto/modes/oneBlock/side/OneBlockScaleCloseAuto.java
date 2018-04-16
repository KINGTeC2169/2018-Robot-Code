package com.team2169.robot.auto.modes.oneBlock.side;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.auto.AutoConstants;
import com.team2169.robot.auto.AutoConstants.RobotSide;
import com.team2169.robot.auto.modes.AutoMode;
import com.team2169.robot.auto.tasks.arm.ArmRetract;
import com.team2169.robot.auto.tasks.drive.DriveStraight;
import com.team2169.robot.auto.tasks.drive.TurnInPlace;
import com.team2169.robot.auto.tasks.elevator.ElevatorToScaleHigh;
import com.team2169.robot.auto.tasks.elevator.ElevatorToSwitch;
import com.team2169.robot.auto.tasks.intake.IntakeExhaust;
import com.team2169.robot.auto.tasks.intake.IntakeOpen;

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
    public OneBlockScaleCloseAuto(RobotSide side) {

    	RobotStates.runningMode = RunningMode.AUTO;
    	this.autoName = "Scale Close on " + side.name() + " side.";
    	
        addParallel(new ElevatorToSwitch());
        addParallel(new ArmRetract());
        addSequential(new DriveStraight(AutoConstants.SideAutos.OneBlockAutos.CloseScale.startToPoint, .75), 6);
        addParallel(new ElevatorToScaleHigh(), 2);
        addSequential(new TurnInPlace(AutoConstants.SideAutos.OneBlockAutos.CloseScale.pointToScaleTurn, .5, side), 1.5);
        addSequential(new DriveStraight(AutoConstants.SideAutos.OneBlockAutos.CloseScale.pointToScale, .5), 10);
        addParallel(new IntakeOpen());
        addSequential(new IntakeExhaust(AutoConstants.SideAutos.OneBlockAutos.CloseScale.intakeSpeed, true), 7);



    }

    // Put looping checks/code in here
    public void looper() {

        smartDashPush();

    }

    // Smartdashboard output
    public void smartDashPush() {

    }

}
