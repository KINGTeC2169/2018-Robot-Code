package com.team2169.robot.auto.modes.oneBlock.center;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.auto.AutoConstants;
import com.team2169.robot.auto.AutoConstants.ElementSide;
import com.team2169.robot.auto.modes.AutoMode;
import com.team2169.robot.auto.tasks.arm.ArmRetract;
import com.team2169.robot.auto.tasks.drive.DriveStraight;
import com.team2169.robot.auto.tasks.drive.TurnInPlace;
import com.team2169.robot.auto.tasks.elevator.ElevatorToSwitch;
import com.team2169.robot.auto.tasks.intake.IntakeExhaust;
import com.team2169.robot.auto.tasks.intake.IntakeOpen;


public class SwitchAuto extends AutoMode {
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

    public SwitchAuto(ElementSide side) {

        RobotStates.runningMode = RunningMode.AUTO;
        this.autoName = "Center Switch Auto on " + side.name() + " side.";
        boolean inversion = false;

        if(side == ElementSide.RIGHT) {
        	inversion = true;
        }
        
        addParallel(new ArmRetract());
        addParallel(new ElevatorToSwitch());
        addSequential(new DriveStraight(AutoConstants.CenterAutos.OneBlock.startToPoint, .5));
        addSequential(new TurnInPlace(AutoConstants.CenterAutos.OneBlock.pointToSwitchTurn, .5, inversion), 1.5);
        addSequential(new DriveStraight(AutoConstants.CenterAutos.OneBlock.pointToPoint2, .5));
        addSequential(new TurnInPlace(-AutoConstants.CenterAutos.OneBlock.pointToSwitchTurn, .5, inversion), 1.5);
        addSequential(new DriveStraight(AutoConstants.CenterAutos.OneBlock.pointToSwitch, .5));
        addParallel(new IntakeOpen());
        addSequential(new IntakeExhaust(AutoConstants.CenterAutos.OneBlock.intakeSpeed, true), 2);
        
    }

    // Put looping checks/code in here
    public void looper() {

        smartDashPush();

    }

    // Smartdashboard output
    public void smartDashPush() {

    }

}
