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
import com.team2169.robot.auto.tasks.arm.ArmExtend;
import com.team2169.robot.auto.tasks.arm.ArmRetract;
import com.team2169.robot.auto.tasks.elevator.ElevatorToGround;
import com.team2169.robot.auto.tasks.elevator.ElevatorToScaleHigh;
import com.team2169.robot.auto.tasks.elevator.ElevatorToSwitch;
import com.team2169.robot.auto.tasks.intake.IntakeClamp;
import com.team2169.robot.auto.tasks.intake.IntakeIdle;
import com.team2169.robot.auto.tasks.intake.IntakeIn;
import com.team2169.robot.auto.tasks.intake.IntakeNeutral;
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
                		
                		new ArmRetract(),
                		new ElevatorToGround(),
                		new IntakeIdle(),
                		new IntakeClamp()
                		
                }), 0, 49),
                new NestedPathTask(Arrays.asList(new Task[] {
                		
                		new ElevatorToScaleHigh()
                		
                }), 50, 100)
                
    	})), 10);
    	addSequential(new DropAndExhaust(), 1.5);
    	addSequential(new ParallelTask(Arrays.asList(new Task[] {
                new FollowPath(Paths.LLLPaths.scaleToBlock),
                new NestedPathTask(Arrays.asList(new Task[] {
                		
                		new ArmRetract(),
                		new ElevatorToGround(),
                		new IntakeIdle(),
                		new IntakeNeutral()
                		
                }), 0, 74),
                new NestedPathTask(Arrays.asList(new Task[] {
                		
                		new ArmExtend(),
                		new IntakeIn(false),
                		new IntakeNeutral()
                		
                }), 75, 100)
                
    	})), 10);
    	addSequential(new ParallelTask(Arrays.asList(new Task[] {
                new FollowPath(Paths.LLLPaths.blockToSwitch),
                new IntakeClamp(),
                new IntakeIdle(),
                new ElevatorToSwitch()
                
    	})), 10);
    	addSequential(new DropAndExhaust());
    	
    }

	// Put looping checks/code in here
	public void looper() {

		smartDashPush();

	}

	// Smartdashboard output
	public void smartDashPush() {

	}

}
