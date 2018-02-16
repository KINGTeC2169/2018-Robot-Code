package com.team2169.robot.auto.tasks.intake;

import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.RobotWantedStates.WantedIntakeClamp;
import com.team2169.robot.RobotWantedStates.WantedIntakeMode;
import com.team2169.robot.auto.tasks.Task;

import edu.wpi.first.wpilibj.Timer;

public class IntakeDrop extends Task {

	private double timeout;
	private Timer time;
	
	public IntakeDrop(double seconds) {

		timeout = seconds;
		
    }
	
	public IntakeDrop() {

		timeout = -1;
		
    }

    protected void initialize() {
    	
    	RobotWantedStates.wantedIntakeClamp = WantedIntakeClamp.DROP;
    	
    }

	@Override
	protected boolean isFinished() {
		if(timeout != -1) {
			return time.get() >= timeout;	
		}
		return true;
		
	}
	
    protected void end() {
    	
    }
}