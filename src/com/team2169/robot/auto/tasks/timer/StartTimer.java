package com.team2169.robot.auto.tasks.timer;

import com.team2169.robot.auto.tasks.Task;

import edu.wpi.first.wpilibj.Timer;

@SuppressWarnings("unused")
public class StartTimer extends Task {

    private Timer timer_;

    public StartTimer(Timer timer) {

        timer_ = timer;

    }

    // Called just before this Command runs the first time
    protected void initialize() {

        timer_.start();

    }

	@Override
	protected boolean isFinished() {
		return false;
	}

}
