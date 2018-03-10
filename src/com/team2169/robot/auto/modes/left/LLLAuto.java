package com.team2169.robot.auto.modes.left;

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
import com.team2169.robot.auto.tasks.arm.ArmExtend;
import com.team2169.robot.auto.tasks.elevator.ElevatorToGround;
import com.team2169.robot.auto.tasks.elevator.ElevatorToScaleHigh;
import com.team2169.robot.auto.tasks.elevator.ElevatorToSwitch;
import com.team2169.robot.auto.tasks.intake.IntakeClampAction;
import com.team2169.robot.auto.tasks.intake.IntakeIdle;
import com.team2169.robot.auto.tasks.intake.IntakeNeutral;
import com.team2169.robot.auto.tasks.intake.IntakeUntilHeld;
import com.team2169.robot.canCycles.cycles.DropAndExhaust;

public class LLLAuto extends AutoMode {	
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

+----+
|    |
|    |
|    |
+----+
	 
*/
	
    public LLLAuto() {
    	
    	RobotStates.runningMode = RunningMode.AUTO;
    	addSequential(new ParallelTask(Arrays.asList(new Task[] {
                new FollowPath(Paths.LLLPaths.startToScale),
                new NestedPathTask(Arrays.asList(new Task[] {
                		new ArmConfig(),
                		new ElevatorToSwitch(),
                		new IntakeIdle(),
                		new IntakeClampAction()
                		
                }), 0, 50),
                new NestedPathTask(Arrays.asList(new Task[] {
                		
                		new ElevatorToScaleHigh()
                		
                }), 70, 100)
                
    	})), 10);
    	addSequential(new DropAndExhaust(), .5);
    	addSequential(new ParallelTask(Arrays.asList(new Task[] {
                new FollowPath(Paths.LLLPaths.scaleToBlock),
                new NestedPathTask(Arrays.asList(new Task[] {
                		
                		new ElevatorToGround(),
                		new IntakeIdle(),
                		new IntakeNeutral()
                		
                }), 0, 70),
                new NestedPathTask(Arrays.asList(new Task[] {
                		
                		new ArmExtend(),
                		new IntakeUntilHeld()
                		
                }), 75, 100)
                
    	})), 10);
    	addSequential(new IntakeClampAction());
    	addSequential(new ParallelTask(Arrays.asList(new Task[] {
                new FollowPath(Paths.LLLPaths.blockToSwitch),
                new IntakeClampAction(),
                new IntakeIdle(),
                new ElevatorToSwitch()
                
    	})), 10);
    	addSequential(new DropAndExhaust(), 0.5);
    	
    }

	// Put looping checks/code in here
	public void looper() {

		smartDashPush();

	}

	// Smartdashboard output
	public void smartDashPush() {

	}

}
