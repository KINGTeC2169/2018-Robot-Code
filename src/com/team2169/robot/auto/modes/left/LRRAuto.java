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
import com.team2169.robot.auto.tasks.arm.ArmRetract;
import com.team2169.robot.auto.tasks.elevator.ElevatorToGround;
import com.team2169.robot.auto.tasks.elevator.ElevatorToScaleHigh;
import com.team2169.robot.auto.tasks.elevator.ElevatorToSwitch;
import com.team2169.robot.auto.tasks.intake.IntakeClamp;
import com.team2169.robot.auto.tasks.intake.IntakeIdle;
import com.team2169.robot.auto.tasks.intake.IntakeUntilHeld;
import com.team2169.robot.canCycles.cycles.DropAndExhaust;

public class LRRAuto extends AutoMode {	
/*

	
       +-------+         +-------+
       |       |         |-------|
       |       |         |-------|
       |       |         |-------|
       +-------+         +-------+
     	
       +-------+         +-------+
       |       |         |-------|
       |       |         |-------|
       |       |         |-------|
       +-------+         +-------+

+----+
|    |
|    |
|    |
+----+

*/

	public LRRAuto() {

		RobotStates.runningMode = RunningMode.AUTO;
		addSequential(new ParallelTask(Arrays.asList(new Task[] {
            new FollowPath(Paths.LRRPaths.startToScale),
            new NestedPathTask(Arrays.asList(new Task[] {
            		new IntakeClamp(),
            		new ArmRetract(),
            		new ElevatorToGround(),
            		new IntakeIdle(),
            
            		
            }), 0, 49),
            new NestedPathTask(Arrays.asList(new Task[] {
            		
            		new ElevatorToScaleHigh()
            		
            }), 50, 100)
            
		})), 10);
		addSequential(new DropAndExhaust(), 1.25);
		addSequential(new ParallelTask(Arrays.asList(new Task[]{
				new FollowPath(Paths.LRRPaths.scaleToBlock),
				new NestedPathTask(Arrays.asList(new Task[] {
						new ElevatorToGround(),
	            }), 20, 49),
				new NestedPathTask(Arrays.asList(new Task[] {
	            		
	            		new IntakeUntilHeld()
	            		
	            }), 50, 100)
				
		})), 10);
		addSequential(new ElevatorToSwitch(), 2);
		addSequential(new FollowPath(Paths.LRRPaths.blockToSwitch));
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
