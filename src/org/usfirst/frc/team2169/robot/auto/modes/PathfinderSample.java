package org.usfirst.frc.team2169.robot.auto.modes;

import org.usfirst.frc.team2169.robot.RobotStates;

import org.usfirst.frc.team2169.robot.RobotStates.runningMode;
import org.usfirst.frc.team2169.robot.auto.AutoManager;
import org.usfirst.frc.team2169.robot.auto.Paths;
import org.usfirst.frc.team2169.robot.auto.tasks.FollowPath;
import org.usfirst.frc.team2169.robot.subsystems.SampleSubsystem;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PathfinderSample extends CommandGroup {
	
	FollowPath follow;
	
	
	int selectedAuto;
	int i = 0;	

	
	//Getter Method to get auto mode from AutoManager
	public void selectMode(int mode) {

    	selectedAuto = mode;
    	
	}
	
    public PathfinderSample() {

    	follow = new FollowPath(Paths.example, SampleSubsystem.left, SampleSubsystem.right, SampleSubsystem.gyro);
    	if(selectedAuto == 0) {
    		
    		
    		
    		addSequential(follow);
    		System.out.println("Auto 0 Starting");
    		
    		
    	}
    	
    	else if(selectedAuto == 1) {
    	
    		System.out.println("Auto 1 Starting");
    	
    		//Do Nothing
    		
    	}
    	
    	
    	else if(selectedAuto == 2) {
        	
    		System.out.println("Auto 2 Starting");
    		
    		//Do Nothing
    		
    	}
    	
    	
    }
    
    public void looper() {
    	
    	smartDashPush();
    	RobotStates.runningMode = runningMode.AUTO;
    	
    }
    
    public void smartDashPush() {
    	
    	SmartDashboard.putString("Running Auto: ", AutoManager.autoName);
    	
    	//Put Smartdashboard output

    	
    }
}
