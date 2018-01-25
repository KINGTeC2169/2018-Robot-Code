package org.usfirst.frc.team2169.robot.auto.canCycles.cycles;

import org.usfirst.frc.team2169.robot.auto.tasks.Task;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class SampleCANCycle extends CommandGroup {
	
	//Define Subsystems
	Task task;
	Task task1;
	
    public SampleCANCycle() {
    	
    	//Put Sequentials and Parallels here
    	task.addTaskToCancel(task1);
    }
    
    
    public void smartDashPush() {
    	
    	//Put Smartdashboard output

    }
}
