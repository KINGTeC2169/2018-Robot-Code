package com.team2169.robot.auto.tasks.intake;

import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.RobotWantedStates.WantedIntakeMode;
import com.team2169.robot.auto.tasks.Task;

import edu.wpi.first.wpilibj.Timer;

public class IntakeExhaust extends Task {

	private double timeout;
	private Timer time;
	
	public IntakeExhaust(double seconds) {

		timeout = seconds;
		
    }
	
	public IntakeExhaust() {

		timeout = -1;
		
    }

    protected void initialize() {
    	
    	RobotWantedStates.wantedIntakeMode = WantedIntakeMode.EXHAUST;
    	
    }

	@Override
	protected boolean isFinished() {
		if(timeout != -1) {
			return time.get() >= timeout;	
		}
		return true;
		
	}
	
    protected void end() {
		
    	if(timeout == -1) {
			RobotWantedStates.wantedIntakeMode = WantedIntakeMode.IDLE;
		}
    	
    }
}
