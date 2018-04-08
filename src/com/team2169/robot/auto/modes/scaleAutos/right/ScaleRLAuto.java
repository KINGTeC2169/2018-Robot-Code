package com.team2169.robot.auto.modes.scaleAutos.right;

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

public class ScaleRLAuto extends AutoMode {
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

                                   +-----+
                                   |     |
                                   |     |
                                   |     |
                                   +-----+
	 
*/

    public ScaleRLAuto() {
        RobotStates.runningMode = RunningMode.AUTO;
        this.autoName = "Scale RL";
        addParallel(new ArmExtend());
        addParallel(new ElevatorToSwitch());
        addSequential(new DriveStraight(AutoConstants.RightAutos.ScaleAutos.LeftScale.startToPoint, .75));       
        addSequential(new TurnInPlace(.4, AutoConstants.RightAutos.ScaleAutos.LeftScale.pointToPoint2Turn), 3);
        addSequential(new DriveStraight(AutoConstants.RightAutos.ScaleAutos.LeftScale.pointToPoint2, .75));
        addParallel(new ElevatorToScaleHigh());
        addSequential(new TurnInPlace(.4, AutoConstants.RightAutos.ScaleAutos.LeftScale.point2ToScaleTurn), 3);
        addSequential(new DriveStraight(AutoConstants.RightAutos.ScaleAutos.LeftScale.point2ToScale, .25));
        addParallel(new IntakeOpen());
        addSequential(new IntakeExhaust(AutoConstants.RightAutos.ScaleAutos.LeftScale.intakeSpeed, true), 2);
    }

    // Put looping checks/code in here
    public void looper() {

        smartDashPush();

    }

    // Smartdashboard output
    public void smartDashPush() {

    }

}
