package com.team2169.robot.auto.tasks.timer;

import com.team2169.robot.auto.tasks.Task;

import edu.wpi.first.wpilibj.Timer;

@SuppressWarnings("unused")
public class StopTimer extends Task {

    private Timer timer_;

    public StopTimer(Timer timer) {

        timer_ = timer;

    }

    // Called just before this Command runs the first time
    protected void initialize() {

    	System.out.println("Timer: " + timer_.get());
        timer_.stop();

    }

	@Override
	protected boolean isFinished() {
		return false;
	}

}
