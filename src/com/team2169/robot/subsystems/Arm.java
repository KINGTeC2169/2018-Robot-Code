
package com.team2169.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team2169.robot.*;
import com.team2169.robot.RobotStates.ArmPos;
import com.team2169.util.TalonMaker;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Arm {
	
	private static Arm aInstance = null;

	public static Arm getInstance() {
		if (aInstance == null) {
			aInstance = new Arm();
		}
		return aInstance;
	}

	// Create Talons
	public TalonSRX arm;
	public int lastPosition;
	Timer timer;

	public Arm() {

		//Create Talon
		timer = new Timer();
		
		// Define Lift Talons
		arm = new TalonSRX(ActuatorMap.armID);
		arm = prepArmTalon(arm);
		
		//Wait 20ms to let the encoder catch up
		try {
			Thread.sleep(20);
		} catch (InterruptedException e) {
		}
		
		lastPosition = arm.getSelectedSensorPosition(0);

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

	private void runToPosition(int pos) {
		arm.set(ControlMode.Position, pos);
		lastPosition = arm.getSelectedSensorPosition(0);
	}
	
	private void holdInPosition() {
		arm.set(ControlMode.Position, lastPosition);
	}
	
	private void armSetOverrideLooper(double joystickValue) {
		arm.set(ControlMode.PercentOutput, joystickValue);
	}
	
	public void pushToDashboard() {
		SmartDashboard.putNumber("Arm Position", arm.getSelectedSensorPosition(0));
		SmartDashboard.putString("Arm State", RobotWantedStates.wantedArmPos.name());
	}

	public void stop() {
		arm.set(ControlMode.PercentOutput, 0);
	}
	
	public TalonSRX prepArmTalon(TalonSRX talon) {
		
		Constants.setArmDataFromConstants();
		// Apply Talon Settings
		talon = TalonMaker.prepTalon(arm, Constants.armData);
		talon.configSelectedFeedbackSensor(FeedbackDevice.Analog, 0, 10);
		talon.setStatusFramePeriod(StatusFrame.Status_1_General, 5, 10);
		talon.setNeutralMode(NeutralMode.Brake);
		return talon;
		
	}

}
