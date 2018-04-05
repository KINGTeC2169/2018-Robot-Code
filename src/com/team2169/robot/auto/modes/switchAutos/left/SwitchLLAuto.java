package com.team2169.robot.auto.modes.switchAutos.left;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.auto.AutoConstants;
import com.team2169.robot.auto.modes.AutoMode;
import com.team2169.robot.auto.tasks.arm.ArmRetract;
import com.team2169.robot.auto.tasks.drive.DriveStraight;
import com.team2169.robot.auto.tasks.drive.TurnInPlace;
import com.team2169.robot.auto.tasks.elevator.ElevatorToSwitch;
import com.team2169.robot.auto.tasks.intake.IntakeExhaust;

public class SwitchLLAuto extends AutoMode {
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

    public SwitchLLAuto() {

        RobotStates.runningMode = RunningMode.AUTO;
        this.autoName = "Switch LL";
        addParallel(new ArmRetract());
        addParallel(new ElevatorToSwitch());
        addSequential(new DriveStraight(AutoConstants.LeftAutos.SwitchAutos.LeftSwitch.startToPoint, .5), 10);   
        addSequential(new TurnInPlace(.5, AutoConstants.LeftAutos.SwitchAutos.LeftSwitch.pointToSwitchTurn), 2);
        addSequential(new DriveStraight(AutoConstants.LeftAutos.SwitchAutos.LeftSwitch.pointToSwitch, .5), 2);
        addSequential(new IntakeExhaust(AutoConstants.LeftAutos.SwitchAutos.LeftSwitch.intakeSpeed, true), 2);
        
    }

    // Put looping checks/code in here
    public void looper() {

        smartDashPush();

    }

    // Smartdashboard output
    public void smartDashPush() {

    }

}
