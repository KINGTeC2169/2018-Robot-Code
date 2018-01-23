package org.usfirst.frc.team2169.robot.auto.modes;

import org.usfirst.frc.team2169.robot.RobotStates;
import org.usfirst.frc.team2169.robot.RobotStates.RunningMode;
import org.usfirst.frc.team2169.robot.auto.AutoManager;

import edu.wpi.first.wpilibj.DriverStation;
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
    		
    		if(RobotStates.fieldSetup.equals("LR")) {
    			//Default Auto for LR Setup
    		}
    		
    		else if(RobotStates.fieldSetup.equals("LL")) {
    			//Default Auto for LL Setup
    		}
    		
    		else if(RobotStates.fieldSetup.equals("RL")) {
    			//Default Auto for RL Setup
    		}
    		
    		else if(RobotStates.fieldSetup.equals("RR")) {
    			//Default Auto for RR Setup
    		}
    		
    		else {
    			DriverStation.reportError("Auto failed to init, error in Field Data", true);
    		}
    		
    	}
    	
    	else if(selectedAuto == 1) {
    	
    		if(RobotStates.fieldSetup.equals("LR")) {
    			//Alternative Auto 1 for LR Setup
    		}
    		
    		else if(RobotStates.fieldSetup.equals("LL")) {
    			//Alternative Auto 1 for LL Setup
        	}
    		
    		else if(RobotStates.fieldSetup.equals("RL")) {
    			//Alternative Auto 1 for RL Setup
        	}
    		
    		else if(RobotStates.fieldSetup.equals("RR")) {
    			//Alternative Auto 1 for RR Setup
        	}
    		
    		else {
    			DriverStation.reportError("Auto failed to init, error in Field Data", true);
    		}
    	}
    	
    	else if(selectedAuto == 2) {
        	

    		if(RobotStates.fieldSetup.equals("LR")) {
    			//Alternative Auto 1 for LR Setup
    		}
    		
    		else if(RobotStates.fieldSetup.equals("LL")) {
    			//Alternative Auto 1 for LL Setup
        	}
    		
    		else if(RobotStates.fieldSetup.equals("RL")) {
    			//Alternative Auto 1 for RL Setup
        	}
    		
    		else if(RobotStates.fieldSetup.equals("RR")) {
    			//Alternative Auto 1 for RR Setup
        	}
    		
    		else {
    			DriverStation.reportError("Auto failed to init, error in Field Data", true);
    		}
    		
    	}
    	    	
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
