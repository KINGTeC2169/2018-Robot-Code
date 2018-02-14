package com.team2169.robot.canCycles.cycles;

import com.team2169.robot.auto.tasks.arm.ArmDeploy;
import com.team2169.robot.auto.tasks.intake.IntakeExhaust;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DropAndExhaust extends CommandGroup {
	
	//Define Subsystems
	
    public DropAndExhaust() {

    	addSequential(new ArmDeploy());
    	addSequential(new IntakeExhaust(.25));
    	//Put Sequentials and Parallels here
    	
    }
    
    
    public void smartDashPush() {
    	
    	//Put Smartdashboard output

    }
}
