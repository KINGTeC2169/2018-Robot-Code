package com.team2169.robot.auto.tasks.arm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team2169.robot.Constants;
import com.team2169.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.command.PIDCommand;

public class ArmPID extends PIDCommand { 
	
	TalonSRX arm;
	
	public ArmPID() {	
		super("Arm", Constants.armData.p, Constants.armData.i, Constants.armData.d);

		arm = Arm.getInstance().arm;
		getPIDController().setF(Constants.armData.f);
		getPIDController().setOutputRange(-1, 1);
		
		getPIDController().setAbsoluteTolerance(Constants.armData.allowedError);
		getPIDController().setContinuous(true);
	}
	
	public void setDesiredPosition(int pos) {
		this.setSetpoint(pos);
	}

	protected void initDefaultCommand() {}
	
	public void enable() {
		getPIDController().enable();
	}
	
	public void disable() {
		getPIDController().disable();
	}

	public double getEncPosition() {
		return getPosition();
	}
	
	public double getPIDSetpoint() {
		return getSetpoint();
	}
	
	@Override
	protected double returnPIDInput() {
		int read = arm.getSensorCollection().getPulseWidthPosition();
		/* flip pulse width to match selected sensor. */
		if (Constants.armData.sensorPhase) {
			read *= -1;
		}
			
		if (Constants.armData.inverted) {
			read *= -1;
		}	
		
		/* throw out the overflows, CTRE Encoder is 4096 units per rotation => 12 bitmask (0xFFF) */
		read = read & 0xFFF;
		
		/* set the value back with no overflows */
		return read;
	}

	@Override
	protected void usePIDOutput(double output) {
		
		arm.set(ControlMode.PercentOutput, output);
		
	}

	@Override
	protected boolean isFinished() {
		return false;
	}
}