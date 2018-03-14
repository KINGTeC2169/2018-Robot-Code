package com.team2169.robot.auto.modes.pathfinderAutos;

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

import java.util.Arrays;

public class RLLAutoCont extends AutoMode {	
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

    public RLLAutoCont() {

        RobotStates.runningMode = RunningMode.AUTO;
        addSequential(new ParallelTask(Arrays.asList(new FollowPath(Paths.RLLPaths.startToScale, false),
                new NestedPathTask(Arrays.asList(new IntakeClampAction(),
                        new ArmConfig(),
                        new ElevatorToSwitch(),
                        new IntakeIdle()), 0, 50),
                new NestedPathTask(Arrays.asList(new Task[]{

                        new ElevatorToScaleHigh(),

                }), 80, 100))), 10);
        addSequential(new DropAndExhaust(), 0.25);
        addSequential(new ParallelTask(Arrays.asList(new FollowPath(Paths.RLLPaths.scaleToBlock, false),
                new NestedPathTask(Arrays.asList(new IntakeIdle(),
                        new IntakeNeutral(),
                        new ElevatorToGround(),
                        new ArmExtend()), 20, 49),
                new NestedPathTask(Arrays.asList(new Task[]{

                        new IntakeUntilHeld()

                }), 50, 100))), 10);
        addSequential(new IntakeIdle(), 2);
        addSequential(new IntakeClampAction(), 2);
        addSequential(new ElevatorToSwitch(), 2);
        addSequential(new FollowPath(Paths.RLLPaths.blockToSwitch, false));
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
