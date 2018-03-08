package com.team2169.robot.auto.modes.right;

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

public class RLRAuto extends AutoMode {
/*
	
	
       +-------+         +-------+
       |       |         |-------|
       |       |         |-------|
       |	   |         |-------|
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

	public RLRAuto() {

		RobotStates.runningMode = RunningMode.AUTO;  
    	addSequential(new ParallelTask(Arrays.asList(new Task[]{
    			new FollowPath(Paths.RLRPaths.startToScale),
    			new NestedPathTask(Arrays.asList(new Task[] {
    					new IntakeClampAction(),
    					new ArmConfig(),
    					new ElevatorToSwitch(),
    					new IntakeIdle()
    					
    			}), 0, 49),
    			new NestedPathTask(Arrays.asList(new Task[] {
    					new ElevatorToScaleHigh()
    			}), 80, 100)
    	})), 10);
    	addSequential(new DropAndExhaust(), .5);
    	addSequential(new ParallelTask(Arrays.asList(new Task[] {
    			new FollowPath(Paths.RLRPaths.scaleToBlock),
    			new NestedPathTask(Arrays.asList(new Task [] {
    					new IntakeIdle(),
    					new IntakeNeutral(),
    					new ElevatorToGround()
    					
    			}), 20, 49),
    			new NestedPathTask(Arrays.asList(new Task [] {
    					new ArmExtend(),
    					new IntakeUntilHeld()
    			}), 50, 100)
    	})), 10);
    	
    	addSequential(new ParallelTask(Arrays.asList(new Task[] {
    			new FollowPath(Paths.RLRPaths.blockToScale),
    			new NestedPathTask(Arrays.asList(new Task [] {
    					new IntakeClampAction(),
    					new IntakeIdle(),
    					new ElevatorToSwitch()
    			}), 0, 49),
    	})),5);
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
