package com.team2169.robot.canCycles.cycles;

import com.team2169.robot.RobotStates;
import com.team2169.robot.auto.tasks.arm.ArmExtend;
import com.team2169.robot.auto.tasks.intake.IntakeDrop;
import com.team2169.robot.auto.tasks.intake.IntakeExhaust;
import com.team2169.robot.canCycles.CanCycle;

public class DropAndExhaust extends CanCycle {
	
    public DropAndExhaust() {
    	
    	addSequential(new ArmExtend(), 1);
    	addSequential(new IntakeDrop());
    	addSequential(new IntakeExhaust(true), 1);
   	
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
