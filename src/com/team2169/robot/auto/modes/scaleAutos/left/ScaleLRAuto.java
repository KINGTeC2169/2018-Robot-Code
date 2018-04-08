package com.team2169.robot.auto.modes.scaleAutos.left;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.auto.AutoConstants;
import com.team2169.robot.auto.modes.AutoMode;
import com.team2169.robot.auto.tasks.arm.ArmExtend;
import com.team2169.robot.auto.tasks.drive.DriveStraight;
import com.team2169.robot.auto.tasks.drive.TurnInPlace;
import com.team2169.robot.auto.tasks.elevator.ElevatorToScaleHigh;
import com.team2169.robot.auto.tasks.elevator.ElevatorToSwitch;
import com.team2169.robot.auto.tasks.intake.IntakeExhaust;
import com.team2169.robot.auto.tasks.intake.IntakeOpen;

public class ScaleLRAuto extends AutoMode {
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

    public ScaleLRAuto() {
    	
    	this.autoName = "Scale LR";
    	
        RobotStates.runningMode = RunningMode.AUTO;
        addParallel(new ArmExtend());
        addParallel(new ElevatorToSwitch());
        addSequential(new DriveStraight(AutoConstants.LeftAutos.ScaleAutos.RightScale.startToPoint, .75), 4);       
        addSequential(new TurnInPlace(.5, AutoConstants.LeftAutos.ScaleAutos.RightScale.pointToPoint2Turn), 2);
        addSequential(new DriveStraight(AutoConstants.LeftAutos.ScaleAutos.RightScale.pointToPoint2, .75), 4);
        addParallel(new ElevatorToScaleHigh());
        addSequential(new TurnInPlace(.4, AutoConstants.LeftAutos.ScaleAutos.RightScale.point2ToScaleTurn), 2);
        addSequential(new DriveStraight(AutoConstants.LeftAutos.ScaleAutos.RightScale.point2ToScale, .25), 4);
        addParallel(new IntakeOpen());
        addSequential(new IntakeExhaust(AutoConstants.LeftAutos.ScaleAutos.RightScale.intakeSpeed, true), 2);
    }

    // Put looping checks/code in here
    public void looper() {

        smartDashPush();

    }

    // Smartdashboard output
    public void smartDashPush() {

    }

}
