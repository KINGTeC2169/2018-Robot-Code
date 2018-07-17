package com.team2169.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team2169.robot.*;
import com.team2169.robot.RobotStates.ArmPos;
import com.team2169.robot.auto.tasks.arm.ArmPID;
import com.team2169.util.TalonMaker;

import edu.wpi.first.wpilibj.AnalogInput;

public class Arm {
	
	private static Arm aInstance = null;

	public static Arm getInstance() {
		if (aInstance == null) {
			aInstance = new Arm();
		}
		return aInstance;
	}

	// Create Talons
	
	private AnalogInput enc;
	public TalonSRX arm;
	private int lastPosition;
	private ArmPID armPID;

	public Arm() {

		// Define Lift Talons
		arm = new TalonSRX(ActuatorMap.armID);
		arm = prepArmTalon(arm);
		enc = new AnalogInput(ActuatorMap.armEncoderPort);
		
		
		//Wait 20ms to let the encoder catch up
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
		}
		
		lastPosition = arm.getSelectedSensorPosition(0);
		armPID = new ArmPID(this);

	}

	public void armMacroLooper() {
		
		// Set robot's actual state to WantedState's value
		switch (RobotWantedStates.wantedArmPos) {
		case STOW:
			runToPosition(Constants.stowArmEncoderPosition);
			RobotStates.armPos = ArmPos.STOW;
			break;
		case EXTEND:
			runToPosition(Constants.extendedArmEncoderPosition);
			RobotStates.armPos = ArmPos.EXTEND;
			break;
		case RETRACT:
			runToPosition(Constants.retractedArmEncoderPosition);
			RobotStates.armPos = ArmPos.RETRACT;
			break;
		case HOLD_POSITION:
			holdInPosition();
			RobotStates.armPos = ArmPos.HOLD_POSITION;
			break;
		case IDLE:
			armSetOverrideLooper(0);
			RobotStates.armPos = ArmPos.IDLE;
			break;
		case OVERRIDE:
		default:
			armSetOverrideLooper(ControlMap.getOperatorStickValue());
			RobotStates.armPos = ArmPos.OVERRIDE;
			break;
		}

	}

	public int getEncPosition() {
		return enc.getAverageValue();
	}
	
	private void runToPosition(int pos) {
		armPID.loop();
		armPID.setDesiredPosition(pos);
		lastPosition = armPID.getPosition();
	}
	
	private void holdInPosition() {
		armPID.loop();
		armPID.setDesiredPosition(lastPosition);
	}
	
	private void armSetOverrideLooper(double joystickValue) {
		arm.set(ControlMode.PercentOutput, joystickValue);
		lastPosition = armPID.getPosition();
	}
	
	public void pushToDashboard() {
		armPID.printToDashoard();
	}

	public void stop() {
		arm.set(ControlMode.PercentOutput, 0);
	}
	
	public TalonSRX prepArmTalon(TalonSRX talon) {
		
		Constants.setArmDataFromConstants();
		talon = TalonMaker.prepTalon(arm, Constants.armData);
		talon.configSelectedFeedbackSensor(FeedbackDevice.None, 0, 10);
		talon.setNeutralMode(NeutralMode.Brake);
		return talon;
		
	}

}
