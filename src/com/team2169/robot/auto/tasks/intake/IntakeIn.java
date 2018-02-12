package com.team2169.robot.auto.tasks.intake;

import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.RobotWantedStates.WantedIntakeMode;
import com.team2169.robot.auto.tasks.Task;

import edu.wpi.first.wpilibj.Timer;

public class IntakeIn extends Task {

	private double timeout;
	private Timer time;
	
	public IntakeIn(double seconds) {

		timeout = seconds;
		
    }
	
	public IntakeIn() {

		timeout = -1;
		
    }

    protected void initialize() {
    	
    	RobotWantedStates.wantedIntakeMode = WantedIntakeMode.INTAKE;
    	
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
