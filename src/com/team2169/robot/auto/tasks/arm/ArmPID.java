package com.team2169.robot.auto.tasks.arm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.team2169.robot.Constants;
import com.team2169.robot.subsystems.Arm;
import com.team2169.util.PIDF;

public class ArmPID{ 
	
	private Arm arm;
	private PIDF pid;
	private double setpoint;
	
	public ArmPID(Arm arm_) {	
	
		pid = new PIDF(Constants.armData.p, Constants.armData.i, Constants.armData.d, Constants.armData.f);	
		arm = arm_;
		
	}
	
	public void setDesiredPosition(int pos) {
		
		setpoint = pos % 1023;
		if(setpoint < 0) {
			setpoint += 1023;
		}
		pid.setSetpoint(setpoint);

	}

	public void loop() {
		double output = pid.getOutput(getPosition());
		arm.arm.set(ControlMode.PercentOutput, output);
	}

	protected double getPosition() {
		int read = arm.getEncPosition();
		
		if (Constants.armData.sensorPhase) {
			read *= -1;
		}
			
		if (Constants.armData.inverted) {
			read *= -1;
		}	
		
		double foo = read % 1023;
		if(foo < 0) {
			foo += 1023;
		}
		return foo;
	}
}