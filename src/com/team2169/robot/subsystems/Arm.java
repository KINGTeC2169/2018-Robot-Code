
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

	}

	public void armMacroLooper() {

		// Set robot's actual state to WantedState's value
		switch (RobotWantedStates.wantedArmPos) {
		case STOW:
			armSetOverrideLooper(0);
			RobotStates.armPos = ArmPos.STOW;
			break;
		case EXTENDED:
			armSetOverrideLooper(0);
			RobotStates.armPos = ArmPos.EXTENDED;
			break;
		case RETRACTED:
			armSetOverrideLooper(0);
			RobotStates.armPos = ArmPos.RETRACTED;
			break;
		case HOLD_POSITION:
			armSetOverrideLooper(0);
			RobotStates.armPos = ArmPos.HOLD_POSITION;
			break;
		case IDLE:
			armSetOverrideLooper(0);
			RobotStates.armPos = ArmPos.IDLE;
			break;
		case PASS:
			break;
		case OVERRIDE:
		default:
			armSetOverrideLooper(ControlMap.getOperatorStickValue());
			RobotStates.armPos = ArmPos.OVERRIDE;
			break;
		}

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
