package com.team2169.robot.auto.tasks.arm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.team2169.robot.RobotStates.ArmPos;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.auto.tasks.Task;
import com.team2169.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.Timer;

@SuppressWarnings("unused")
public class ArmExtend extends Task {

    private Timer timer;
    private double timeOut = .5;
    private Arm arm;

    public ArmExtend() {
    	
    	arm = Arm.getInstance();
        timer = new Timer();

    }

    // Called just before this Command runs the first time
    protected void initialize() {

        timer.start();
        RobotWantedStates.wantedArmPos = ArmPos.PASS;
        arm.arm.set(ControlMode.PercentOutput, .5);

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    	System.out.println("Running Arm" + timer.get());
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {

        return timer.get() >= timeOut;

    }

    // Called once after isFinished returns true
    protected void end() {

    	arm.arm.set(ControlMode.PercentOutput, 0);
        System.out.println("Timer Task Finished");

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
