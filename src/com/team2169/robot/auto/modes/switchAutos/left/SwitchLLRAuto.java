package com.team2169.robot.auto.modes.switchAutos.left;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.auto.AutoConstants;
import com.team2169.robot.auto.modes.AutoMode;
import com.team2169.robot.auto.tasks.arm.ArmExtend;
import com.team2169.robot.auto.tasks.arm.ArmRetract;
import com.team2169.robot.auto.tasks.drive.DriveStraight;
import com.team2169.robot.auto.tasks.drive.TurnInPlace;
import com.team2169.robot.auto.tasks.elevator.ElevatorToSwitch;
import com.team2169.robot.auto.tasks.intake.IntakeExhaust;

public class SwitchLLRAuto extends AutoMode {
/*

	
       +-------+         +-------+
       |       |         |-------|
       |       |         |-------|
       |	   |         |-------|
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

    public SwitchLLRAuto() {

        RobotStates.runningMode = RunningMode.AUTO;
        
        addParallel(new ArmRetract());
        addParallel(new DriveStraight(AutoConstants.LeftAutos.SwitchAutos.LeftSwitch.startToPoint, .5));
        addSequential(new TurnInPlace(.75, AutoConstants.LeftAutos.SwitchAutos.LeftSwitch.pointToSwitchTurn, 2));
        addParallel(new DriveStraight(AutoConstants.LeftAutos.SwitchAutos.LeftSwitch.pointToSwitch, .5));
        addParallel(new ElevatorToSwitch());
        addParallel(new ArmExtend());
        addSequential(new IntakeExhaust(true), 3);
    
    }

    // Put looping checks/code in here
    public void looper() {

        smartDashPush();

    }

    // Smartdashboard output
    public void smartDashPush() {

    }

}
