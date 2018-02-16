package com.team2169.robot.auto.tasks.intake;

import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.RobotWantedStates.WantedIntakeClamp;
import com.team2169.robot.RobotWantedStates.WantedIntakeMode;
import com.team2169.robot.auto.tasks.Task;

import edu.wpi.first.wpilibj.Timer;

public class IntakeNeutral extends Task {

	private double timeout;
	private Timer time;
	
	public IntakeNeutral(double seconds) {

		timeout = seconds;
		
    }
	
	public IntakeNeutral() {

		timeout = -1;
		
    }

    protected void initialize() {
    	
    	RobotWantedStates.wantedIntakeClamp = WantedIntakeClamp.NEUTRAL;
    	
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