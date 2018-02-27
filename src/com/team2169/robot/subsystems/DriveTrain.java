package com.team2169.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team2169.robot.ActuatorMap;
import com.team2169.robot.Constants;
import com.team2169.robot.ControlMap;
import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.RobotStates.DriveMode;
import com.team2169.robot.RobotStates.DriveOverride;
import com.team2169.robot.RobotWantedStates.WantedDriveMode;
import com.team2169.robot.RobotWantedStates.WantedDriveOverride;
import com.team2169.robot.subsystems.Subsystem;
import com.team2169.util.DebugPrinter;
import com.team2169.util.FlyByWireHandler;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends Subsystem {

	private static DriveTrain dInstance = null;

	public static DriveTrain getInstance() {
		if (dInstance == null) {
			dInstance = new DriveTrain();
		}
		return dInstance;
	}
	

	// Define null objects up here
	// Public because autonomous needs to access actuators
	// Static because there is only one of each subsystem
	public TalonSRX leftMaster;
	TalonSRX leftTop;
	TalonSRX leftFront;
	public TalonSRX rightMaster;
	TalonSRX rightFront;
	TalonSRX rightTop;
	DoubleSolenoid shifter;
	DoubleSolenoid ptoShift;

	public DriveTrain() {

		// Create the objects and set properties
		leftMaster = new TalonSRX(ActuatorMap.leftMasterDriveTalon);
		leftFront = new TalonSRX(ActuatorMap.leftFront);
		leftTop = new TalonSRX(ActuatorMap.leftTop);

		rightMaster = new TalonSRX(ActuatorMap.rightMasterDriveTalon);
		rightFront = new TalonSRX(ActuatorMap.rightFront);
		rightTop = new TalonSRX(ActuatorMap.rightTop);

		leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
		rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);

		leftTop.set(ControlMode.Follower, ActuatorMap.leftMasterDriveTalon);
		leftFront.set(ControlMode.Follower, ActuatorMap.leftMasterDriveTalon);

		leftMaster.setInverted(true);
		leftFront.setInverted(true);

		rightFront.set(ControlMode.Follower, ActuatorMap.rightMasterDriveTalon);
		rightTop.set(ControlMode.Follower, ActuatorMap.rightMasterDriveTalon);

		rightTop.setInverted(true);

		// Set Current Limits
		
		 leftMaster.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent,
		 Constants.driveTrainCurrentTimeout);
		 leftFront.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent,
		 Constants.driveTrainCurrentTimeout);
		 leftTop.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent,
		 Constants.driveTrainCurrentTimeout);
		 rightMaster.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent,
		 Constants.driveTrainCurrentTimeout);
		 rightFront.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent,
		 Constants.driveTrainCurrentTimeout);
		 rightTop.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent,
		 Constants.driveTrainCurrentTimeout);
		 

		// Shifting Solenoids
		shifter = new DoubleSolenoid(ActuatorMap.PCMPort, ActuatorMap.dtSpeedShiftForward,
				ActuatorMap.dtSpeedShiftReverse);
		ptoShift = new DoubleSolenoid(ActuatorMap.PCMPort, ActuatorMap.ptoShiftForward,
				ActuatorMap.ptoShiftReverse);

		RobotWantedStates.wantedDriveMode = WantedDriveMode.SHIFT_TO_LOW;
		RobotWantedStates.wantedDriveOverride = WantedDriveOverride.WANTS_TO_DRIVE;

	}

	public void driveHandler() {

		switch (RobotWantedStates.wantedDriveOverride) {

		// Drive without Override
		case NONE:
		default:

			// Debugging Information
			if (RobotStates.debugMode) {
				DriverStation.reportWarning("DriveTrain: No Override, Standard Driving", false);
			}

			// Acceleration Handler with Squared controls
			leftMaster.set(ControlMode.PercentOutput, ControlMap.leftTankStick(true));
			rightMaster.set(ControlMode.PercentOutput,ControlMap.rightTankStick(true));
			leftTop.set(ControlMode.PercentOutput, ControlMap.leftTankStick(true) * 0.91);
			rightTop.set(ControlMode.PercentOutput, ControlMap.rightTankStick(true) * 0.91);
			
			

			// Shift without override
			shift(false);

			// Set Robot State
			RobotStates.driveOverride = DriveOverride.NONE;

			break;

		// Drive with Override
		case OVERRIDE:

			// Debugging Information
			if (RobotStates.debugMode) {
				DriverStation.reportWarning("DriveTrain: Override Mode", false);
			}

			// Drive with Override
			leftMaster.set(ControlMode.PercentOutput, ControlMap.leftTankStick(true));
			rightMaster.set(ControlMode.PercentOutput, ControlMap.rightTankStick(true));
			leftTop.set(ControlMode.PercentOutput, ControlMap.leftTankStick(true) * 0.91);
			rightTop.set(ControlMode.PercentOutput, ControlMap.rightTankStick(true) * 0.91);
			// Shift with override
			shift(true);

			// Set Robot State
			RobotStates.driveOverride = DriveOverride.OVERRIDE;

			break;

		// Hang
		case HANG:

			// Debugging Information
			if (RobotStates.debugMode) {
				DriverStation.reportWarning("DriveTrain: Hang Mode", false);
			}

			// Set Wheels to Hang Power
			leftMaster.set(ControlMode.PercentOutput, Constants.climbPower);

			// Set Robot State
			RobotStates.driveOverride = DriveOverride.HANG;

			break;

		// Single-Run Go into Hang Mode
		case WANTS_TO_HANG:

			// Debugging Information
			if (RobotStates.debugMode) {
				DriverStation.reportWarning("DriveTrain: Wants To Hang Mode", false);
			}

			// Set Talon Modes for Driving
			rightMaster.set(ControlMode.Follower, ActuatorMap.leftMasterDriveTalon);
			rightFront.set(ControlMode.Follower, ActuatorMap.leftMasterDriveTalon);
			rightTop.set(ControlMode.Follower, ActuatorMap.leftMasterDriveTalon);

			// Dogshifter Extended
			// ptoShift.set(Value.kForward);
			RobotStates.ptoActive = true;

			// Set Robot State
			RobotStates.driveOverride = DriveOverride.WANTS_TO_HANG;

			break;

		// Single-Run Go into Drive Mode
		case WANTS_TO_DRIVE:

			// Debugging Information
			if (RobotStates.debugMode) {
				DriverStation.reportWarning("DriveTrain: Wants To Drive Mode", false);
			}

			// Set Talon Modes for Driving
			rightFront.set(ControlMode.Follower, ActuatorMap.rightMasterDriveTalon);
			rightTop.set(ControlMode.Follower, ActuatorMap.rightMasterDriveTalon);

			// Dogshifter Retracted
			// ptoShift.set(Value.kReverse);
			RobotStates.ptoActive = false;

			// Set Robot State
			RobotStates.driveOverride = DriveOverride.WANTS_TO_DRIVE;

			break;

		}

	}

	void wantedClimbHander(boolean climb) {
		if (climb) {

		}

		else if (!climb) {

			// Set Right Slaves as slaves of Right Master

		}
	}

	void shift(boolean override) {
		// If the override is active, switch to the requested Drive Mode

		// If Master Override is Active
		if (override) {

			if (RobotStates.debugMode) {
				DriverStation.reportWarning("DriveTrain Class: Shifting Override Active", false);
			}

			// If wanting to shift high
			if (RobotWantedStates.wantedDriveMode == WantedDriveMode.SHIFT_TO_HIGH) {

				shifter.set(Constants.highGear);

				// Set Robot States
				RobotStates.driveMode = DriveMode.HIGH;
				RobotWantedStates.wantedDriveMode = WantedDriveMode.HIGH;

			}

			// If wanting to shift low
			if (RobotWantedStates.wantedDriveMode == WantedDriveMode.SHIFT_TO_LOW) {

				shifter.set(Constants.lowGear);

				// Set Robot States
				RobotStates.driveMode = DriveMode.LOW;
				RobotWantedStates.wantedDriveMode = WantedDriveMode.LOW;

			}
		}

		// If Master Override is not Active
		else {

			// If wanting to shift shift high, and it is safe
			if (RobotWantedStates.wantedDriveMode == WantedDriveMode.SHIFT_TO_HIGH
					&& FlyByWireHandler.determineShiftSafety(RobotWantedStates.wantedDriveMode)) {

				shifter.set(Constants.highGear);

				if (RobotStates.debugMode) {
					DriverStation.reportWarning("High Gear (safe)", false);
				}

				// Set Robot States
				RobotStates.driveMode = DriveMode.HIGH;
				RobotWantedStates.wantedDriveMode = WantedDriveMode.HIGH;

			}

			// If wanting to shift shift low, and it is safe
			else if (RobotWantedStates.wantedDriveMode == WantedDriveMode.SHIFT_TO_LOW
					&& FlyByWireHandler.determineShiftSafety(RobotWantedStates.wantedDriveMode)) {

				shifter.set(Constants.lowGear);

				if (RobotStates.debugMode) {
					DriverStation.reportWarning("Low Gear (safe)", false);
				}

				// Set Robot States
				RobotStates.driveMode = DriveMode.LOW;
				RobotWantedStates.wantedDriveMode = WantedDriveMode.LOW;

			}

		}
	}

	@Override
	public void pushToDashboard() {

		// Put any SmartDash info here.
		DebugPrinter.driveTrainDebug();
		SmartDashboard.putNumber("Left Encoder Value: ",
				leftMaster.getSelectedSensorPosition(Constants.leftDriveData.slotIDx));
		SmartDashboard.putNumber("Right Encoder Value: ",
				rightMaster.getSelectedSensorPosition(Constants.rightDriveData.slotIDx));
		
		SmartDashboard.putNumber("RightTop", rightTop.getOutputCurrent());
		SmartDashboard.putNumber("RightMaster", rightMaster.getOutputCurrent());
		SmartDashboard.putNumber("RightFront", rightFront.getOutputCurrent());
		SmartDashboard.putNumber("RightToVolts", rightTop.getMotorOutputVoltage());
		SmartDashboard.putNumber("RightMasterVolts", rightMaster.getMotorOutputVoltage());
		SmartDashboard.putNumber("RightFrontVolts", rightFront.getMotorOutputVoltage());
		
		SmartDashboard.putNumber("LeftTop", leftTop.getOutputCurrent());
		SmartDashboard.putNumber("LeftMaster", leftTop.getOutputCurrent());
		SmartDashboard.putNumber("LeftFront", leftTop.getOutputCurrent());
		SmartDashboard.putNumber("LeftTopVolts", leftTop.getMotorOutputVoltage());
		SmartDashboard.putNumber("LeftMasterVolts", leftTop.getMotorOutputVoltage());
		SmartDashboard.putNumber("LeftFrontVolts", leftTop.getMotorOutputVoltage());
	}

	@Override
	public void zeroSensors() {

		// Zero sensors method

	}

	@Override
	public void stop() {

		// Code to make all moving parts halt goes here.
		leftMaster.set(ControlMode.Disabled, 1);
		rightMaster.set(ControlMode.Disabled, 1);

	}

}
