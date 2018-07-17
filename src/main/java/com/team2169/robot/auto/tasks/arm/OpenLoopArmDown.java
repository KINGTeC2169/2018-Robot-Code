package com.team2169.robot.auto.tasks.arm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.team2169.robot.auto.tasks.Task;
import com.team2169.robot.subsystems.Arm;

public class OpenLoopArmDown extends Task {

	Arm arm;
	
    public OpenLoopArmDown() {

    	arm = Arm.getInstance();
    	
    }

    protected void initialize() {

    	

    }

    protected void execute() {

        arm.arm.set(ControlMode.PercentOutput, .5);

    }
    
    @Override
    protected boolean isFinished() {
        return isTimedOut();
    }

    protected void end() {

    	arm.arm.set(ControlMode.PercentOutput, 0);
    	
    }
}
