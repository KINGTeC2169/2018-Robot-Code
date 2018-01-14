package org.usfirst.frc.team2169.robot.auto.modes;

import org.usfirst.frc.team2169.robot.RobotStates;
import org.usfirst.frc.team2169.robot.RobotStates.runningMode;
import org.usfirst.frc.team2169.robot.auto.AutoManager;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class RedLeftAuto extends CommandGroup {
	
	int selectedAuto;

	//Getter Method to get auto mode from AutoManager
	public void selectMode(int mode) {

    	selectedAuto = mode;
    	
	}
	
    public RedLeftAuto() {

    	if(selectedAuto == 0) {
    		
    		//Run Default Auto
    		//Code for default auto goes here
    		
    	}
    	
    	else if(selectedAuto == 1) {
    	
    		//Run Auto 2
    		//Code for auto 2 goes here
    		
    	}
    	
    	else if(selectedAuto == 2) {
        	
    		//Run Auto 3
    		//Code for auto 3 goes here
    		
    	}
    	
    	
    }
    
    public void looper() {
    	
    	//Put looping checks/code in here
    	
    	smartDashPush();
    	RobotStates.runningMode = runningMode.AUTO;
    	
    }
    
    public void smartDashPush() {
    	
    	//Put Smartdashboard output

    	SmartDashboard.putString("Running Auto: ", AutoManager.autoName);
    	
    }
}
