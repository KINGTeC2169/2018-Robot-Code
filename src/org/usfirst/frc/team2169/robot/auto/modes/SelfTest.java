package org.usfirst.frc.team2169.robot.auto.modes;

import org.usfirst.frc.team2169.robot.RobotStates;
import org.usfirst.frc.team2169.robot.RobotStates.RunningMode;
import org.usfirst.frc.team2169.robot.auto.AutoManager;
import org.usfirst.frc.team2169.robot.auto.tasks.FollowPath;
import org.usfirst.frc.team2169.robot.auto.tasks.elevator.ElevatorToGround;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SelfTest extends CommandGroup {
	
	ElevatorToGround elevatorToGround;
	FollowPath follow1;
	
	void configure () {
		
		elevatorToGround.addTaskToCancel(follow1);
		
	}
	
    public SelfTest() {
    	
    addSequential(elevatorToGround);
    elevatorToGround.cancel();
    	
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
