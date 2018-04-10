package com.team2169.robot.auto.modes.oneBlock.side;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.auto.AutoConstants;
import com.team2169.robot.auto.AutoConstants.RobotSide;
import com.team2169.robot.auto.modes.AutoMode;
import com.team2169.robot.auto.tasks.arm.ArmExtend;
import com.team2169.robot.auto.tasks.drive.DriveStraight;
import com.team2169.robot.auto.tasks.drive.TurnInPlace;
import com.team2169.robot.auto.tasks.elevator.ElevatorToSwitch;
import com.team2169.robot.auto.tasks.intake.IntakeExhaust;

public class SwitchCloseAuto extends AutoMode {
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

    public SwitchCloseAuto(RobotSide side) {
        
    	RobotStates.runningMode = RunningMode.AUTO;
        this.autoName = "Side Switch Auto on " + side.name() + " side.";
        
        addParallel(new ArmExtend());
        addParallel(new ElevatorToSwitch());
        addSequential(new DriveStraight(AutoConstants.SideAutos.Switch.startToPoint, .5), 15);   
        addSequential(new TurnInPlace(AutoConstants.SideAutos.Switch.pointToSwitchTurn, .5, side), 2);
        addSequential(new DriveStraight(AutoConstants.SideAutos.Switch.pointToSwitch, .5), 2);
        addSequential(new IntakeExhaust(AutoConstants.SideAutos.Switch.intakeSpeed, true), 7);
        
    }

    // Put looping checks/code in here
    public void looper() {

        smartDashPush();

    }

    // Smartdashboard output
    public void smartDashPush() {

    }

}
