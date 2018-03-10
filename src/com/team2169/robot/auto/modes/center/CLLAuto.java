package com.team2169.robot.auto.modes.center;

import java.util.Arrays;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.auto.Paths;
import com.team2169.robot.auto.modes.AutoMode;
import com.team2169.robot.auto.tasks.FollowPath;
import com.team2169.robot.auto.tasks.NestedPathTask;
import com.team2169.robot.auto.tasks.ParallelTask;
import com.team2169.robot.auto.tasks.Task;
import com.team2169.robot.auto.tasks.arm.ArmConfig;
import com.team2169.robot.auto.tasks.elevator.ElevatorToSwitch;
import com.team2169.robot.auto.tasks.intake.IntakeClampAction;
import com.team2169.robot.auto.tasks.intake.IntakeIdle;
import com.team2169.robot.canCycles.cycles.DropAndExhaust;

public class CLLAuto extends AutoMode {	
/*
	
	
       +-------+         +-------+
       |-------|         |       |
       |-------|         |       |
       |-------|         |       |
       +-------+         +-------+

       +-------+         +-------+
       |-------|         |       |
       |-------|         |       |
       |-------|         |       |
       +-------+         +-------+
   
                 +-----+      
                 |     |      
                 |     |      
                 |     |      
                 +-----+      
	 
*/
	
	public CLLAuto() {

		RobotStates.runningMode = RunningMode.AUTO;
		addSequential(new ParallelTask(Arrays.asList(new Task[] {
                new FollowPath(Paths.CLLPaths.startToSwitch),
                new NestedPathTask(Arrays.asList(new Task[] {
                		new ArmConfig(),
                		new ElevatorToSwitch(),
                		new IntakeIdle(),
                		new IntakeClampAction()
                		
                }), 0, 50),        
    	})), 10);
		addSequential(new DropAndExhaust(), .5);

	}

	// Put looping checks/code in here
	public void looper() {

		smartDashPush();

	}

	// Smartdashboard output
	public void smartDashPush() {

	}

}
