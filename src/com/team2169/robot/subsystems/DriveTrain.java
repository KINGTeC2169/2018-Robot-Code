package com.team2169.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import com.team2169.robot.*;
import com.team2169.robot.RobotStates.DriveMode;
import com.team2169.robot.RobotStates.DriveType;
import com.team2169.util.DebugPrinter;
import com.team2169.util.FlyByWireHandler;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends Subsystem {

	private static DriveTrain dInstance = null;

	public static DriveTrain getInstance() {
		if (dInstance == null) {
			dInstance = new DriveTrain();
		}
		return dInstance;
	}

	public TalonSRX left;
	private TalonSRX leftTop;
	private TalonSRX leftFront;
	public TalonSRX right;
	private TalonSRX rightFront;
	private TalonSRX rightTop;
	private DoubleSolenoid shifter;
	private DoubleSolenoid ptoShift;
	public AHRS navX;

	private DriveTrain() {

		// Create IMU
		navX = new AHRS(SPI.Port.kMXP, (byte) 200);

		// Create the Talon objects and set properties
		left = new TalonSRX(ActuatorMap.leftMasterDriveTalon);
		leftFront = new TalonSRX(ActuatorMap.leftFront);
		leftTop = new TalonSRX(ActuatorMap.leftTop);
		right = new TalonSRX(ActuatorMap.rightMasterDriveTalon);
		rightFront = new TalonSRX(ActuatorMap.rightFront);
		rightTop = new TalonSRX(ActuatorMap.rightTop);

		// Configure Mag Encoders
		left.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
		right.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);

		// Set Left Slaves
		leftTop.set(ControlMode.Follower, ActuatorMap.leftMasterDriveTalon);
		leftFront.set(ControlMode.Follower, ActuatorMap.leftMasterDriveTalon);

		// Set Right Slaves
		rightFront.set(ControlMode.Follower, ActuatorMap.rightMasterDriveTalon);
		rightTop.set(ControlMode.Follower, ActuatorMap.rightMasterDriveTalon);

		// Set Inversions
		left.setInverted(true);
		leftFront.setInverted(true);
		rightTop.setInverted(true);

		// Set Current Limits
		left.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		leftFront.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		leftTop.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		right.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		rightFront.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		rightTop.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);

		//Enable Ramping
		enableRamping();
		
		// Create Shifters
		shifter = new DoubleSolenoid(ActuatorMap.PCMPort, ActuatorMap.dtSpeedShiftForward,
				ActuatorMap.dtSpeedShiftReverse);
		ptoShift = new DoubleSolenoid(ActuatorMap.PCMPort, ActuatorMap.ptoShiftForward, ActuatorMap.ptoShiftReverse);
		
		//Set Initial States
		RobotWantedStates.wantedDriveMode = DriveMode.LOW;
		RobotWantedStates.wantedDriveType = DriveType.NORMAL_DRIVING;

	}

	void driveHandler() {

		switch (RobotWantedStates.wantedDriveType) {

		// Drive without Override
		case NORMAL_DRIVING:
		default:

			shift();

			// Drive with Acceleration Handler
			left.set(ControlMode.PercentOutput, ControlMap.leftTankStick(false) * FlyByWireHandler.getSafeSpeed());
			right.set(ControlMode.PercentOutput, ControlMap.rightTankStick(false) * FlyByWireHandler.getSafeSpeed());

			RobotStates.driveTrainOverride = false;
			RobotStates.driveType = DriveType.NORMAL_DRIVING;

			break;

		// Drive with Override
		case OVERRIDE_DRIVING:

			shift();

			// Drive with Override
			left.set(ControlMode.PercentOutput, ControlMap.leftTankStick(false));
			right.set(ControlMode.PercentOutput, ControlMap.rightTankStick(false));

			RobotStates.driveTrainOverride = true;
			RobotStates.driveType = DriveType.OVERRIDE_DRIVING;

			break;

		case HANG:

			shift();

			// Drive with Acceleration Handler
			left.set(ControlMode.PercentOutput, ControlMap.leftTankStick(false));
			right.set(ControlMode.PercentOutput, ControlMap.rightTankStick(false));

			RobotStates.driveTrainOverride = false;
			RobotStates.driveType = DriveType.HANG;

			break;

		case EXTERNAL_DRIVING:
			break;

		}

	}

	private void shift() {

		// Handle DriveTrain Shifting Status

		// Shifting High
		if (RobotWantedStates.wantedDriveMode == DriveMode.HIGH) {
			shifter.set(Constants.highGear);
			// Set Robot States
			RobotStates.driveMode = DriveMode.HIGH;
			RobotWantedStates.wantedDriveMode = DriveMode.HIGH;
		}
		// Shifting Low
		else if (RobotWantedStates.wantedDriveMode == DriveMode.LOW) {
			shifter.set(Constants.lowGear);
			// Set Robot States
			RobotStates.driveMode = DriveMode.LOW;
			RobotWantedStates.wantedDriveMode = DriveMode.LOW;
		}

		// Handle PTO Status

		// PTO Active
		if (RobotWantedStates.wantedDriveType == DriveType.HANG) {
			ptoShift.set(Value.kForward);
			RobotStates.ptoActive = true;
		}
		// PTO Inactive
		else {
			ptoShift.set(Value.kReverse);
			RobotStates.ptoActive = false;
		}
	}

	private void enableRamping() {
		left.configOpenloopRamp(Constants.driveTrainRampRate, 0);
		left.configClosedloopRamp(Constants.driveTrainRampRate, 0);
		right.configOpenloopRamp(Constants.driveTrainRampRate, 0);
		right.configClosedloopRamp(Constants.driveTrainRampRate, 0);
	}

	@SuppressWarnings("unused")
	private void disableRamping() {
		left.configOpenloopRamp(0, 0);
		left.configClosedloopRamp(0, 0);
		right.configOpenloopRamp(0, 0);
		right.configClosedloopRamp(0, 0);
	}

	private void resetEncoders() {
		left.setSelectedSensorPosition(0, Constants.leftDriveData.pidLoopIDx, Constants.leftDriveData.timeoutMs);
		right.setSelectedSensorPosition(0, Constants.rightDriveData.pidLoopIDx, Constants.rightDriveData.timeoutMs);
	}

	public double getAngle() {
		return -navX.getAngle();
	}

	public void resetGyro() {
		navX.reset();
	}

	
	@Override
	public void pushToDashboard() {

		SmartDashboard.putNumber("Gyro", getAngle());
		DebugPrinter.driveTrainDebug();
		SmartDashboard.putNumber("Left Encoder Value: ",
				left.getSelectedSensorPosition(Constants.leftDriveData.slotIDx));
		SmartDashboard.putNumber("Right Encoder Value: ",
				right.getSelectedSensorPosition(Constants.rightDriveData.slotIDx));

	}

	@Override
	public void zeroSensors() {

		resetGyro();
		resetEncoders();
	}

	@Override
	public void stop() {
		// Code to make all moving parts halt goes here.
		left.set(ControlMode.Disabled, 1);
		right.set(ControlMode.Disabled, 1);
		resetGyro();
		resetEncoders();
	}
}
