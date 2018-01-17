package org.usfirst.frc.team2169.robot.auto.canCycles.cycles;

import org.usfirst.frc.team2169.robot.auto.tasks.BlankTask;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SampleCANCycle extends CommandGroup {
	
	//Define Subsystems
	
    public SampleCANCycle() {
    	
    	//Put Sequentials and Parallels here
    	addSequential(new BlankTask());
    	
    }
    
    
    public void smartDashPush() {
    	
    	//Put Smartdashboard output

    }
}
