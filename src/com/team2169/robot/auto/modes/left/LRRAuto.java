package com.team2169.robot.auto.modes.left;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.auto.modes.AutoMode;

public class LRRAuto extends AutoMode {

    public LRRAuto() {
    	
    	RobotStates.runningMode = RunningMode.AUTO;
    	//This is where you put tasks    	
    	
    }
    
    //Put looping checks/code in here
    public void looper() {
    	
    	smartDashPush();
    	
    }
    
    //Smartdashboard output
    public void smartDashPush() {
    	
    	
    	
    }
    
}
