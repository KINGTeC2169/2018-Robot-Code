package com.team2169.robot.auto.tasks.arm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team2169.robot.Constants;
import com.team2169.robot.subsystems.Arm;
import com.team2169.util.PIDF;

public class ArmPID{ 
	
	private TalonSRX arm;
	private PIDF pid;
	private double setpoint;
	
	public ArmPID(Arm arm_) {	
	
		pid = new PIDF(Constants.armData.p, Constants.armData.i, Constants.armData.d, Constants.armData.f);	
		arm = arm_.arm;
		
	}
	
	public void setDesiredPosition(int pos) {
		
		setpoint = pos % 1023;
		pid.setSetpoint(setpoint);

	}

	public void loop() {
		double output = pid.getOutput(getPosition());
		arm.set(ControlMode.PercentOutput, output);
	}

	protected double getPosition() {
		int read = arm.getSelectedSensorPosition(0);
		
		if (Constants.armData.sensorPhase) {
			read *= -1;
		}
			
		if (Constants.armData.inverted) {
			read *= -1;
		}	
		
		double foo = read % 1023;
		return foo;
	}
}