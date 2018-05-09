package com.team2169.robot.auto.tasks.arm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.team2169.robot.Constants;
import com.team2169.robot.subsystems.Arm;
import com.team2169.util.PIDF;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ArmPID{ 
	
	private Arm arm;
	private PIDF pid;
	private double setpoint;
	
	public ArmPID(Arm arm_) {	
	
		pid = new PIDF(Constants.armData.p, Constants.armData.i, Constants.armData.d, Constants.armData.f);	
		arm = arm_;
		
	}
	
	public void setDesiredPosition(int pos) {
		
		setpoint = pos % 4096;
		if(setpoint < 0) {
			setpoint += 4096;
		}
		if(setpoint > 1023) {
			setpoint -= 4096;
		}
		pid.setSetpoint(setpoint);

	}

	
	public void loop() {
		pid.setP(Constants.armData.p);
		pid.setI(Constants.armData.i);
		pid.setD(Constants.armData.d);
		pid.setF(Constants.armData.f);
		double output = pid.getOutput(getPosition());
		arm.arm.set(ControlMode.PercentOutput, output);
	}

	public int getPosition() {
		int read = arm.getEncPosition();
		
		if (Constants.armData.sensorPhase) {
			read *= -1.0;
		}
			
		if (Constants.armData.inverted) {
			read *= -1.0;
		}	
		
		double foo = read % 4096;
		if(foo < 0) {
			foo += 4096;
		}
		if(foo > 1023) {
			foo -= 4096;
		}
		return (int) foo;
	}
	
	public void printToDashoard() {
		SmartDashboard.putNumber("Arm Amps: ", arm.arm.getOutputCurrent());
		SmartDashboard.putNumber("Raw Arm Position", arm.getEncPosition());
		SmartDashboard.putNumber("Arm Position: ", getPosition());
	}
}