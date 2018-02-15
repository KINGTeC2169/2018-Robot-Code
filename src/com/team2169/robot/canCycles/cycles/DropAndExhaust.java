package com.team2169.robot.canCycles.cycles;

import com.team2169.robot.RobotStates;
import com.team2169.robot.auto.tasks.arm.ArmDeploy;
import com.team2169.robot.auto.tasks.intake.IntakeExhaust;
import com.team2169.robot.canCycles.CanCycle;

public class DropAndExhaust extends CanCycle {
	
    public DropAndExhaust() {
    	
    }
    
    public void start() {
    	
    	RobotStates.canCycleMode = true;
    	addSequential(new ArmDeploy());
    	addSequential(new IntakeExhaust(.25));
   	
    }
    
    public void smartDashPush() {

    }
    
}
