package com.team2169.robot.auto.tasks.arm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team2169.robot.Constants;
import com.team2169.robot.subsystems.Arm;

public class ArmPID{ 
	
	TalonSRX arm;
	private double setpoint;
	
	public ArmPID(Arm arm_) {	
	
		arm = arm_.arm;
		
	}
	
	public void setDesiredPosition(int pos) {
		setpoint = pos % 1023;
	}

	public void loop() {
		System.out.println(Constants.armData.p * getError());
		arm.set(ControlMode.PercentOutput, Constants.armData.p * getError());
	}

	protected double getError() {
		int read = arm.getSelectedSensorPosition(0);
		/* flip pulse width to match selected sensor. */
		if (Constants.armData.sensorPhase) {
			read *= -1;
		}
			
		if (Constants.armData.inverted) {
			read *= -1;
		}	
		
		/* throw out the overflows, CTRE Encoder is 4096 units per rotation => 12 bitmask (0xFFF) */
		read = read % 1023;
		
		System.out.println(read);
		
		/* set the value back with no overflows */
		return setpoint - read;
	}
}