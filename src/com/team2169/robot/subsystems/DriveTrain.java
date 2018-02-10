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
import com.team2169.robot.subsystems.Subsystem;
import com.team2169.util.FlyByWireHandler;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends Subsystem{
	
    private static DriveTrain dInstance = null;

    public static DriveTrain getInstance() {
        if (dInstance == null) {
            dInstance = new DriveTrain();
        }
        return dInstance;
    }
	
	//Define null objects up here
	//Public because autonomous needs to access actuators
	//Static because there is only one of each subsystem
	public TalonSRX left;
	TalonSRX leftSlaveRev;
	TalonSRX leftSlaveFol;
	public TalonSRX right;
	TalonSRX rightSlaveFol;
	TalonSRX rightSlaveRev;
	DoubleSolenoid shifter;
    DoubleSolenoid ptoShift;
    
	public DriveTrain(){

		//Create the objects and set properties
		left = new TalonSRX(ActuatorMap.leftMasterDriveTalon);
		leftSlaveRev = new TalonSRX(ActuatorMap.leftSlaveRev);
		leftSlaveFol = new TalonSRX(ActuatorMap.leftSlaveFol);
		right = new TalonSRX(ActuatorMap.rightMasterDriveTalon);
		rightSlaveFol = new TalonSRX(ActuatorMap.rightSlaveFol);
		rightSlaveRev = new TalonSRX(ActuatorMap.rightSlaveRev);
		
		
		left.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
		right.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
		
		leftSlaveRev.set(ControlMode.Follower, ActuatorMap.leftMasterDriveTalon);
		leftSlaveFol.set(ControlMode.Follower, ActuatorMap.leftMasterDriveTalon);
		
		left.setInverted(true);
		leftSlaveFol.setInverted(true);
		
		rightSlaveFol.set(ControlMode.Follower, ActuatorMap.rightMasterDriveTalon);
		rightSlaveRev.set(ControlMode.Follower, ActuatorMap.rightMasterDriveTalon);    
		
		rightSlaveRev.setInverted(true);
		
		
		//Set Current Limits

		left.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		leftSlaveRev.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		leftSlaveFol.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		right.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		rightSlaveRev.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		rightSlaveFol.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);

		
		//Shifting Solenoids
		//shifter = new DoubleSolenoid(ActuatorMap.compressorPCMPort, ActuatorMap.dtSpeedShiftForward, ActuatorMap.dtSpeedShiftReverse);
		//ptoShift = new DoubleSolenoid(ActuatorMap.compressorPCMPort, ActuatorMap.ptoShiftForward, ActuatorMap.ptoShiftReverse);
		
		
	}
	
	
	public void driveHandler() {
		
		ControlMap.getWantedDriveOverride();
		
		switch(RobotWantedStates.wantedDriveOverride) {
		
			//Drive without Override
			case NONE: default:
				
				//Debugging Information
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("DriveTrain: No Override, Standard Driving", false);
				}
				
				//Acceleration Handler with Squared controls
				left.set(ControlMode.PercentOutput, ControlMap.leftTankStick(true) * FlyByWireHandler.getSafeSpeed());
				right.set(ControlMode.PercentOutput, ControlMap.rightTankStick(true) * FlyByWireHandler.getSafeSpeed());
				
				//Shift without override
				shift(false);
				
				//Set Robot State
				RobotStates.driveOverride = DriveOverride.NONE;
				
				break;
			
			//Drive with Override
			case OVERRIDE:
		
				//Debugging Information
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("DriveTrain: Override Mode", false);
				}
				
				//Drive with Override
				left.set(ControlMode.PercentOutput, ControlMap.leftTankStick(true));
				right.set(ControlMode.PercentOutput, ControlMap.rightTankStick(true));
				
				//Shift with override
				shift(true);
				
				//Set Robot State
				RobotStates.driveOverride = DriveOverride.OVERRIDE;
				
				break;

			//Hang
			case HANG:

				//Debugging Information
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("DriveTrain: Hang Mode", false);
				}
				
				//Set Wheels to Hang Power
				left.set(ControlMode.PercentOutput, Constants.climbPower);
				
				//Set Robot State
				RobotStates.driveOverride = DriveOverride.HANG;		

				break;
			
				
				
			//Single-Run Go into Hang Mode
			case WANTS_TO_HANG:

				//Debugging Information
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("DriveTrain: Wants To Hang Mode", false);
				}
				
				//Set Talon Modes for Driving
				right.set(ControlMode.Follower, ActuatorMap.leftMasterDriveTalon);
				rightSlaveFol.set(ControlMode.Follower, ActuatorMap.leftMasterDriveTalon);
				rightSlaveRev.set(ControlMode.Follower, ActuatorMap.leftMasterDriveTalon);
				
				//Dogshifter Extended
				//ptoShift.set(Value.kForward);
				RobotStates.ptoActive = true;

				//Set Robot State
				RobotStates.driveOverride = DriveOverride.WANTS_TO_HANG;
				
				break;
	
				
			//Single-Run Go into Drive Mode
			case WANTS_TO_DRIVE:
				
				//Debugging Information
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("DriveTrain: Wants To Drive Mode", false);
				}
				
				//Set Talon Modes for Driving
				rightSlaveFol.set(ControlMode.Follower, ActuatorMap.rightMasterDriveTalon);
				rightSlaveRev.set(ControlMode.Follower, ActuatorMap.rightMasterDriveTalon);
				
				//Dogshifter Retracted
				//ptoShift.set(Value.kReverse);
				RobotStates.ptoActive = false;

				//Set Robot State
				RobotStates.driveOverride = DriveOverride.WANTS_TO_DRIVE;
				
				break;
	
		}
		
	}
	
	void wantedClimbHander(boolean climb) {
		if(climb){
			
			

		}
			
		else if(!climb){
			
			//Set Right Slaves as slaves of Right Master
			
		}
	}
	
	void shift(boolean override) {
		//If the override is active, switch to the requested Drive Mode
		
		ControlMap.getWantedShift();
		
		//If Master Override is Active
		if(override) {
			
			if(RobotStates.debugMode) {
				DriverStation.reportWarning("DriveTrain Class: Shifting Override Active", false);
			}
			
			//If wanting to shift high
			if(RobotWantedStates.wantedDriveMode == WantedDriveMode.SHIFT_TO_HIGH) {
				
				//shifter.set(Constants.highGear);
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("High Gear (override)", false);
				}
				
				//Set Robot States
				RobotStates.driveMode = DriveMode.HIGH;
				RobotWantedStates.wantedDriveMode = WantedDriveMode.HIGH;
				
			}
			
			//If wanting to shift low
			if(RobotWantedStates.wantedDriveMode == WantedDriveMode.SHIFT_TO_LOW) {
				
				//shifter.set(Constants.lowGear);
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("Low Gear (override)", false);
				}
				
				//Set Robot States
				RobotStates.driveMode = DriveMode.LOW;
				RobotWantedStates.wantedDriveMode = WantedDriveMode.LOW;
				
			}
		}
		
		//If Master Override is not Active
		else {
			
			
			//If wanting to shift shift high, and it is safe
			if(RobotWantedStates.wantedDriveMode == WantedDriveMode.SHIFT_TO_HIGH && 
					FlyByWireHandler.determineShiftSafety(RobotWantedStates.wantedDriveMode)) {
				
				//shifter.set(Constants.highGear);
				
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("High Gear (safe)", false);
				}
				
				//Set Robot States
				RobotStates.driveMode = DriveMode.HIGH;
				RobotWantedStates.wantedDriveMode = WantedDriveMode.HIGH;
				
			}
			
			//If wanting to shift shift low, and it is safe
			else if(RobotWantedStates.wantedDriveMode == WantedDriveMode.SHIFT_TO_LOW && 
					FlyByWireHandler.determineShiftSafety(RobotWantedStates.wantedDriveMode)) {
				
				//shifter.set(Constants.lowGear);

				if(RobotStates.debugMode) {
					DriverStation.reportWarning("Low Gear (safe)", false);
				}

				//Set Robot States
				RobotStates.driveMode = DriveMode.LOW;
				RobotWantedStates.wantedDriveMode = WantedDriveMode.LOW;
				
			}
		
		}
	}
	
	
	@Override
	public void pushToDashboard() {
		SmartDashboard.putNumber("leftEnc", left.getSelectedSensorPosition(0));
		SmartDashboard.putNumber("rightEnc", right.getSelectedSensorPosition(0));
		//Put any SmartDash info here.
		
	}

	@Override
	public void zeroSensors() {
		
		//Zero sensors method
		
	}

	@Override
	public void stop() {
		
		//Code to make all moving parts halt goes here.
		
	}

}
