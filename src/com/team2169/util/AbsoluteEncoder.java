package com.team2169.util;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AbsoluteEncoder {

	private AnalogInput enc;
	private PIDF pidf;
	private double lastPos;
	private boolean inverted = false;
	private boolean wrap = false;
	private int holdCounter = 0;
	
	public static enum EncoderHealth {
		HEALTHY, UNHEALTHY, UNKNOWN
	}

	public EncoderHealth encHealth;

	public AbsoluteEncoder(int port) {

		enc = new AnalogInput(port);
		pidf = new PIDF(0, 0, 0, 0);
		encHealth = EncoderHealth.UNKNOWN;

	}

	public AbsoluteEncoder(int port, double p, double i, double d) {

		enc = new AnalogInput(port);
		pidf = new PIDF(p, i, d);
		encHealth = EncoderHealth.UNKNOWN;

	}

	public AbsoluteEncoder(int port, double p, double i, double d, double f) {

		enc = new AnalogInput(port);
		pidf = new PIDF(p, i, d, f);
		encHealth = EncoderHealth.UNKNOWN;

	}

	public void setPIDF(double p, double i, double d, double f) {

		pidf.setP(p);
		pidf.setP(i);
		pidf.setP(d);
		pidf.setP(f);

	}

	public void setPID(double p, double i, double d) {

		pidf.setP(p);
		pidf.setP(i);
		pidf.setP(d);

	}

	public void setInversed(boolean phase) {
		inverted = phase;
	}

	public void setWrap(boolean wrapping) {
		wrap = wrapping;
	}
	
	public void setSetpoint(double setpoint) {
		if (inverted) {
			setpoint *= -1.0;
		}

		double foo = setpoint % 4096;
		if (foo < 0) {
			/*
			 * If the number is negative, figure out how many rotations negative it is,
			 * multiply that by the # of ticks needed for 1 rotation, and add that to the
			 * output. This shouldn't ever happen due to the nature of abs. encoders, but
			 * I've seen weird things, so here it is, just to be safe.
			 */
			foo += 4096 * (Math.floor(setpoint / 4096));
		}
		if (wrap & foo > 1023) {
			foo -= 4096;
		}
		
		pidf.setSetpoint(setpoint);
		System.out.println("Set Setpoint to: " + pidf.setpoint);
	}

	public double getOutput() {
		return pidf.getOutput(getPosition());
	}

	public double getOutput(double setpoint) {
		return pidf.getOutput(getPosition(), setpoint);
	}

	public double getOutput(int setpoint) {
		return pidf.getOutput(getPosition(), (double) setpoint);
	}

	public double getPosition() {

		int read = enc.getAverageValue();
		if (read == 0) {
			return lastPos;
		} else {

			if (inverted) {
				read *= -1.0;
			}

			double foo = read % 4096;
			if (foo < 0) {
				/*
				 * If the number is negative, figure out how many rotations negative it is,
				 * multiply that by the # of ticks needed for 1 rotation, and add that to the
				 * output. This shouldn't ever happen due to the nature of abs. encoders, but
				 * I've seen weird things, so here it is, just to be safe.
				 */
				foo += 4096 * (Math.floor(read / 4096));
			}
			if (wrap & foo > 1023) {
				foo -= 4096;
			}
			
			System.out.println("Pos: " + foo);
			lastPos = (int) foo;
			return (int) foo;

		}
		
	}
	
	public boolean inRange(int tolerance) {
		
		return (Math.abs(getPosition() - pidf.setpoint)) < tolerance;
		
	}

	public void printToDashboard(String name) {
		SmartDashboard.putNumber(name + " Raw Position: ", enc.getAverageValue());
		SmartDashboard.putNumber(name + " Position: ", getPosition());
	}

	public void closedLoopCycle(TalonSRX arm) {
		if(!inRange(10)) {
			System.out.println("Out of Range!");
			holdCounter++;
		}
		else {
			System.out.println("In Range!");
			holdCounter = 0;
		}
		
		if(holdCounter > 4) {
			arm.set(ControlMode.PercentOutput, getOutput());
		}
		else {
			arm.set(ControlMode.PercentOutput, 0);
		}
	}

}
