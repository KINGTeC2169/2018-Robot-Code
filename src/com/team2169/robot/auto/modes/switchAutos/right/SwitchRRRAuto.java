package com.team2169.robot.auto.modes.switchAutos.right;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.auto.modes.AutoMode;
import com.team2169.robot.auto.tasks.arm.ArmExtend;
import com.team2169.robot.auto.tasks.drive.DriveStraight;
import com.team2169.robot.auto.tasks.drive.TurnInPlace;
import com.team2169.robot.auto.tasks.elevator.ElevatorToSwitch;
import com.team2169.robot.auto.tasks.intake.IntakeExhaust;

import edu.wpi.first.wpilibj.command.WaitCommand;

public class SwitchRRRAuto extends AutoMode {
/*

	
       +-------+         +-------+
       |       |         |-------|
       |       |         |-------|
       |       |         |-------|
       +-------+         +-------+
     	
       +-------+         +-------+
       |       |         |-------|
       |       |         |-------|
       |       |         |-------|
       +-------+         +-------+

                                   +-----+
                                   |     |
                                   |     |
                                   |     |
                                   +-----+

*/

    public SwitchRRRAuto() {

        RobotStates.runningMode = RunningMode.AUTO;
        addSequential(new DriveStraight(106, .5));
        addSequential(new TurnInPlace(.5, -90), 2);
        addParallel(new ElevatorToSwitch(), 2);
        addParallel(new ArmExtend());
        addSequential(new WaitCommand(1));
        addSequential(new IntakeExhaust(true), 2);

    }

    // Put looping checks/code in here
    public void looper() {

        smartDashPush();

    }

    // Smartdashboard output
    public void smartDashPush() {

    }

}
