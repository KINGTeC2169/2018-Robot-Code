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

public class ScaleCloseAuto extends AutoMode {
/*

	
       +-------+         +-------+
       |-------|         |       |
       |-------|         |       |
       |-------|         |       |
       +-------+         +-------+

       +-------+         +-------+
       |-------|         |       |
       |-------|         |       |
       |-------|         |       |
       +-------+         +-------+

+----+
|    |
|    |
|    |
+----+
	 
*/
    public ScaleCloseAuto(RobotSide side) {

    	RobotStates.runningMode = RunningMode.AUTO;
    	this.autoName = "Scale Close on " + side.name() + " side.";
    	
        addParallel(new ElevatorToSwitch());
        addParallel(new ArmRetract());
        addSequential(new DriveStraight(AutoConstants.SideAutos.CloseScale.startToPoint, .75), 6);
        addParallel(new ElevatorToScaleHigh(), 2);
        addSequential(new TurnInPlace(AutoConstants.SideAutos.CloseScale.pointToScaleTurn, .5, side), 1.5);
        addSequential(new DriveStraight(AutoConstants.SideAutos.CloseScale.pointToScale, .5), 10);
        addParallel(new IntakeOpen());
        addSequential(new IntakeExhaust(AutoConstants.SideAutos.CloseScale.intakeSpeed, true), 7);



    }

    // Put looping checks/code in here
    public void looper() {

        smartDashPush();

    }

    // Smartdashboard output
    public void smartDashPush() {

    }

}
