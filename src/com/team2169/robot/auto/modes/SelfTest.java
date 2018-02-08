package com.team2169.robot.auto.modes;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.auto.AutoManager;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SelfTest extends AutoMode {
	
	void configure () {
		
	}
	
    public SelfTest() {
    	
    	
    }

    
    public void looper() {
    	
    	//Put looping checks/code in here
    	
    	smartDashPush();
    	RobotStates.runningMode = RunningMode.AUTO;
    }
    
    public void smartDashPush() {
    	
    	//Put Smartdashboard output
    	
    	SmartDashboard.putString("Running Auto: ", AutoManager.autoName);
    }
}
