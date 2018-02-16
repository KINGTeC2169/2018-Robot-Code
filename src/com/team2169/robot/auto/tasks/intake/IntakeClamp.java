package com.team2169.robot.auto.tasks.intake;

import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.RobotWantedStates.WantedIntakeClamp;
import com.team2169.robot.RobotWantedStates.WantedIntakeMode;
import com.team2169.robot.auto.tasks.Task;

import edu.wpi.first.wpilibj.Timer;

public class IntakeClamp extends Task {

	private double timeout;
	private Timer time;
	
	public IntakeClamp(double seconds) {

		timeout = seconds;
		
    }
	
	public IntakeClamp() {

		timeout = -1;
		
    }

    protected void initialize() {
    	
    	RobotWantedStates.wantedIntakeClamp = WantedIntakeClamp.CLAMP;
    	
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