package com.team2169.robot.auto.modes;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.auto.Paths;
import com.team2169.robot.auto.tasks.FollowPath;

public class SelfTest extends AutoMode {
	
    public SelfTest() {
    	
    	//addSequential(new TimeTask(5, "Waiting To Start"));
    	addSequential(new FollowPath(Paths.example));
    	
    }

    
    public void looper() {
    	
    	//Put looping checks/code in here
    	
    	smartDashPush();
    	RobotStates.runningMode = RunningMode.AUTO;
    }
    
    public void smartDashPush() {
    	
    	//Put Smartdashboard output
    	
    	//SmartDashboard.putString("Running Auto: ", AutoManager.autoName);
    }
}
		