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
import com.team2169.robot.auto.tasks.timer.StartTimer;
import com.team2169.robot.auto.tasks.timer.StopTimer;

import edu.wpi.first.wpilibj.Timer;

public class SwitchCloseAuto extends AutoMode {
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

	Timer time = new Timer();
	
    public SwitchCloseAuto(RobotSide side) {
        
    	RobotStates.runningMode = RunningMode.AUTO;
        this.autoName = "Side Switch Auto on " + side.name() + " side.";
        
        //Start the timer, extend the arm, and move elevator into switch
        addParallel(new StartTimer(time));
        addParallel(new ArmExtend());
        addParallel(new ElevatorToSwitch());
        addSequential(new DriveStraight(AutoConstants.SideAutos.OneBlockAutos.CloseSwitch.startToPoint, .75), 15);   
        
        //Turn to the switch and drive into it
        addSequential(new TurnInPlace(AutoConstants.SideAutos.OneBlockAutos.CloseSwitch.pointToSwitchTurn, .5, side), 3);
        addSequential(new DriveStraight(AutoConstants.SideAutos.OneBlockAutos.CloseSwitch.pointToSwitch, .75), 2);
        
        //Exhaust the block and stop the timer
        addSequential(new IntakeExhaust(AutoConstants.SideAutos.OneBlockAutos.CloseSwitch.intakeSpeed, true), 7);
        addSequential(new StopTimer(time));
    }

    // Put looping checks/code in here
    public void looper() {

        smartDashPush();

    }

    // Smartdashboard output
    public void smartDashPush() {

    }

}
