package com.team2169.robot.auto.tasks;

import edu.wpi.first.wpilibj.Timer;

@SuppressWarnings("unused")
public class TimeTask extends Task {

    private Timer timer;
    private double timeOut;
    private String name;

    public TimeTask(double timeOut_, String nameBoi) {

        name = nameBoi;
        timeOut = timeOut_;
        timer = new Timer();

    }

    // Called just before this Command runs the first time
    protected void initialize() {

        timer.start();

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        System.out.println(name + " " + timer.get());

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

        return timer.get() >= timeOut;

    }

    // Called once after isFinished returns true
    protected void end() {

        System.out.println("Timer Task Finished");

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
