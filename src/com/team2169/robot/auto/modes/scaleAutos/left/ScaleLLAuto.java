package com.team2169.robot.auto.modes.scaleAutos.left;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.auto.AutoConstants;
import com.team2169.robot.auto.modes.AutoMode;
import com.team2169.robot.auto.tasks.arm.ArmRetract;
import com.team2169.robot.auto.tasks.drive.DriveStraight;
import com.team2169.robot.auto.tasks.drive.TurnInPlace;
import com.team2169.robot.auto.tasks.elevator.ElevatorToScaleHigh;
import com.team2169.robot.auto.tasks.elevator.ElevatorToSwitch;
import com.team2169.robot.auto.tasks.intake.IntakeExhaust;
import com.team2169.robot.auto.tasks.intake.IntakeOpen;

public class ScaleLLAuto extends AutoMode {
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

    public ScaleLLAuto() {

    	System.out.println("Running ScaleLL Auto");
    	
        RobotStates.runningMode = RunningMode.AUTO;
        addParallel(new ElevatorToSwitch());
        addParallel(new ArmRetract());
        addSequential(new DriveStraight(AutoConstants.LeftAutos.ScaleAutos.LeftScale.startToPoint, .5), 6);
        addParallel(new ElevatorToScaleHigh(), 2);
        addSequential(new TurnInPlace(.5, AutoConstants.LeftAutos.ScaleAutos.LeftScale.pointToScaleTurn), 1.5);
        addSequential(new DriveStraight(AutoConstants.LeftAutos.ScaleAutos.LeftScale.pointToScale, .5), 10);
        addParallel(new IntakeOpen());
        addSequential(new IntakeExhaust(AutoConstants.LeftAutos.ScaleAutos.LeftScale.intakeSpeed, true), 7);



    }

    // Put looping checks/code in here
    public void looper() {

        smartDashPush();

    }

    // Smartdashboard output
    public void smartDashPush() {

    }

}
