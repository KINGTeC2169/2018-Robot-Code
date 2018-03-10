package com.team2169.robot.auto.modes.right;

import java.util.Arrays;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.auto.Paths;
import com.team2169.robot.auto.modes.AutoMode;
import com.team2169.robot.auto.tasks.FollowPathInMeters;
import com.team2169.robot.auto.tasks.NestedPathTask;
import com.team2169.robot.auto.tasks.ParallelTask;
import com.team2169.robot.auto.tasks.Task;
import com.team2169.robot.auto.tasks.arm.ArmConfig;
import com.team2169.robot.auto.tasks.arm.ArmExtend;
import com.team2169.robot.auto.tasks.elevator.ElevatorToGround;
import com.team2169.robot.auto.tasks.elevator.ElevatorToScaleHigh;
import com.team2169.robot.auto.tasks.elevator.ElevatorToSwitch;
import com.team2169.robot.auto.tasks.intake.IntakeClampAction;
import com.team2169.robot.auto.tasks.intake.IntakeDrop;
import com.team2169.robot.auto.tasks.intake.IntakeExhaust;
import com.team2169.robot.auto.tasks.intake.IntakeIdle;
import com.team2169.robot.auto.tasks.intake.IntakeNeutral;
import com.team2169.robot.auto.tasks.intake.IntakeUntilHeld;

public class RRRAutoCont extends AutoMode {	
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

                                   +-----+
                                   |     |
                                   |     |
                                   |     |
                                   +-----+

*/

	public RRRAutoCont() {

		RobotStates.runningMode = RunningMode.AUTO;
		addSequential(new ParallelTask(Arrays.asList(new Task[] {
    			new FollowPathInMeters(Paths.RRRPaths.Full),
    			new NestedPathTask(Arrays.asList(new Task[] {
                		new ArmConfig(),
                		new ElevatorToSwitch(),
                		new IntakeIdle(),
                		new IntakeClampAction()
                		
                }), 0, 10),
    			
    			new NestedPathTask(Arrays.asList(new Task[] {
    					new ElevatorToScaleHigh()
    			}), 15, 20),
    			
    			new NestedPathTask(Arrays.asList(new Task[] {
    					new IntakeDrop(),
    					new IntakeExhaust(true)
    			}), 25, 30),
    			new NestedPathTask(Arrays.asList(new Task[] {
    					new ElevatorToGround(),
    					new IntakeNeutral()
    			}), 35, 40),
    			new NestedPathTask(Arrays.asList(new Task[] {
    					new ArmExtend(),
    					new IntakeUntilHeld()
    			}), 45, 50),
    			new NestedPathTask(Arrays.asList(new Task[] {
    					new ElevatorToSwitch()
    			}), 55, 70),
    			new NestedPathTask(Arrays.asList(new Task[] {
    					new IntakeDrop(),
    					new IntakeExhaust(true)
    			}), 90, 100)
    	})), 15);
}

	// Put looping checks/code in here
	public void looper() {

		smartDashPush();

	}

	// Smartdashboard output
	public void smartDashPush() {

	}

}
