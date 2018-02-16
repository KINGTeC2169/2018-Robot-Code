package com.team2169.robot.canCycles.cycles;

import java.util.Arrays;

import com.team2169.robot.RobotStates;
import com.team2169.robot.auto.tasks.ParallelTask;
import com.team2169.robot.auto.tasks.Task;
import com.team2169.robot.auto.tasks.arm.ArmDeploy;
import com.team2169.robot.auto.tasks.elevator.ElevatorToGround;
import com.team2169.robot.auto.tasks.intake.IntakeExhaust;
import com.team2169.robot.canCycles.CanCycle;

public class DropAndExhaust extends CanCycle {
	
    public DropAndExhaust() {
    	
    	addSequential(new ArmDeploy(), 5);
    	addSequential(new IntakeExhaust(true), .25);
    	addSequential(new ParallelTask(Arrays.asList(new Task[] {
                new IntakeExhaust(true),
                }), .5));
   	
    }
    
    protected void initialize() {
    	RobotStates.canCycleMode = true;
    }
    
    protected void interrupted() {
    	end();
    }
    
    protected void end() {
    	RobotStates.canCycleMode = false;
    }
    
    public void smartDashPush() {

    }
    
}
