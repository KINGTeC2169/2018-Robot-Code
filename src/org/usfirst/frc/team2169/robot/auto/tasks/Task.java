package org.usfirst.frc.team2169.robot.auto.tasks;

import java.util.List;
import edu.wpi.first.wpilibj.command.Command;

public abstract class Task extends Command {

	boolean cancelled;
	List<Command> tasks;
	
    public Task() {
    
    	cancelled = false;
    	
    	// Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    public void cancelTasks() {
	    if(!cancelled) {	
	    	for(Command t:tasks) {
	    		
	    		t.cancel();
	    		
	    	}
	    	cancelled = true;
	    }
    }
    
    public void addTaskToCancel(Command task) {
    	tasks.add(task);
    }
    
}
