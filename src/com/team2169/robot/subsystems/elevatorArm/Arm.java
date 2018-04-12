
package com.team2169.robot.subsystems.elevatorArm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.StatusFrame;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team2169.robot.*;
import com.team2169.robot.RobotStates.ArmPos;
import com.team2169.robot.auto.tasks.arm.ArmPID;
import com.team2169.util.TalonMaker;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Arm {

	Timer timer;
	ArmPID pid;

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

	public Arm() {

		timer = new Timer();
		// Define Lift Talons
		arm = new TalonSRX(ActuatorMap.armID);
		// Pull Constants Data for Arm
		Constants.setArmDataFromConstants();

		// Create Arm PID Object
		pid = new ArmPID();

		// Apply Talon Settings
		arm = TalonMaker.prepTalonForMotionProfiling(arm, Constants.armData);
		arm.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		arm.setStatusFramePeriod(StatusFrame.Status_1_General, 5, 10);
		arm.setNeutralMode(NeutralMode.Brake);

	}

	public void armMacroLooper() {

		if (ControlMap.getArmZero()) {
			RobotWantedStates.wantedArmPos = ArmPos.OVERRIDE;
			arm.setSelectedSensorPosition(0, 0, 10);
		}

		// Set robot's actual state to WantedState's value
		switch (RobotWantedStates.wantedArmPos) {
		case STOW:
			pid.setDesiredPosition(Constants.stowArmEncoderPosition);
			pid.enable();
			lastPosition = (int) pid.getEncPosition();
			RobotStates.armPos = ArmPos.STOW;
			break;
		case EXTENDED:
			pid.setDesiredPosition(Constants.extendedArmEncoderPosition);
			pid.enable();
			lastPosition = (int) pid.getEncPosition();
			RobotStates.armPos = ArmPos.EXTENDED;
			break;
		case RETRACTED:
			pid.setDesiredPosition(Constants.retractedArmEncoderPosition);
			pid.enable();
			lastPosition = (int) pid.getEncPosition();
			RobotStates.armPos = ArmPos.RETRACTED;
			break;
		case HOLD_POSITION:
			pid.setDesiredPosition(lastPosition);
			pid.enable();
			RobotStates.armPos = ArmPos.HOLD_POSITION;
			break;
		case IDLE:
			pid.disable();
			armSetOverrideLooper(0);
			RobotStates.armPos = ArmPos.IDLE;
			break;
		case PASS:
			break;
		case OVERRIDE:
		default:
			pid.disable();
			armSetOverrideLooper(ControlMap.getOperatorStickValue());
			RobotStates.armPos = ArmPos.OVERRIDE;
			break;
		}

	}

	public void pushToDashboard() {
		SmartDashboard.putNumber("Arm Error", pid.getPIDSetpoint() - pid.getEncPosition());
		SmartDashboard.putNumber("Arm Setpoint", pid.getPIDSetpoint());
		SmartDashboard.putNumber("Arm Encoder Position", pid.getEncPosition());

	}

	private void armSetOverrideLooper(double joystickValue) {
		arm.set(ControlMode.PercentOutput, joystickValue);
	}


	
	public void zeroSensors() {
		arm.setSelectedSensorPosition(0, Constants.armData.slotIDx, Constants.armData.timeoutMs);
	}

	public void stop() {
		arm.set(ControlMode.PercentOutput, 0);
	}

}
