package org.usfirst.frc.team2169.robot.subsystems;

import org.usfirst.frc.team2169.robot.ActuatorMap;
import org.usfirst.frc.team2169.robot.Constants;
import org.usfirst.frc.team2169.robot.ControlMap;
import org.usfirst.frc.team2169.robot.RobotStates;
import org.usfirst.frc.team2169.robot.RobotStates.DriveMode;
import org.usfirst.frc.team2169.robot.RobotStates.DriveOverride;
import org.usfirst.frc.team2169.robot.RobotWantedStates;
import org.usfirst.frc.team2169.robot.RobotWantedStates.WantedDriveMode;
import org.usfirst.frc.team2169.robot.subsystems.Subsystem;
import org.usfirst.frc.team2169.util.FlyByWireHandler;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DriverStation;

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
	TalonSRX leftSlave1;
	TalonSRX leftSlave2;
	public TalonSRX right;
	TalonSRX rightSlave1;
	TalonSRX rightSlave2;
	DoubleSolenoid shifter;
    DoubleSolenoid ptoShift;
    
	public DriveTrain(){

		//Create the objects and set properties
		left = new TalonSRX(ActuatorMap.leftMasterDriveTalon);
		leftSlave1 = new TalonSRX(ActuatorMap.leftSlave1DriveTalon);
		leftSlave2 = new TalonSRX(ActuatorMap.leftSlave2DriveTalon);
		right = new TalonSRX(ActuatorMap.rightMasterDriveTalon);
		rightSlave1 = new TalonSRX(ActuatorMap.rightSlave1DriveTalon);
		rightSlave2 = new TalonSRX(ActuatorMap.rightSlave2DriveTalon);
		
		leftSlave1.setInverted(true);
		leftSlave2.setInverted(true);
		right.setInverted(true);
		
		//Set Current Limits

		/* Commented out because they throw errors if there aren't feedback devices plugged in.
		left.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		leftSlave1.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		leftSlave2.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		right.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		rightSlave1.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		rightSlave2.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		*/
		
		//Shifting Solenoids
		shifter = new DoubleSolenoid(ActuatorMap.compressorPCMPort, ActuatorMap.dtSpeedShiftForward, ActuatorMap.dtSpeedShiftReverse);
		ptoShift = new DoubleSolenoid(ActuatorMap.compressorPCMPort, ActuatorMap.ptoShiftForward, ActuatorMap.ptoShiftReverse);
		
		
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

			//Single-Run Go into Drive Mode
			case WANTS_TO_DRIVE:
				
				//Debugging Information
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("DriveTrain: Wants To Drive Mode", false);
				}
				
				//Set Talon Modes for Driving
				rightSlave1.set(ControlMode.Follower, ActuatorMap.rightMasterDriveTalon);
				rightSlave2.set(ControlMode.Follower, ActuatorMap.rightMasterDriveTalon);
				
				//Dogshifter Retracted
				ptoShift.set(Value.kReverse);
				RobotStates.ptoActive = false;

				//Set Robot State
				RobotStates.driveOverride = DriveOverride.WANTS_TO_DRIVE;
				
				break;
			
			//Single-Run Go into Hang Mode
			case WANTS_TO_HANG:

				//Debugging Information
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("DriveTrain: Wants To Hang Mode", false);
				}
				
				//Set Talon Modes for Driving
				right.set(ControlMode.Follower, ActuatorMap.leftMasterDriveTalon);
				rightSlave1.set(ControlMode.Follower, ActuatorMap.leftMasterDriveTalon);
				rightSlave2.set(ControlMode.Follower, ActuatorMap.leftMasterDriveTalon);
				
				//Dogshifter Extended
				ptoShift.set(Value.kForward);
				RobotStates.ptoActive = true;

				//Set Robot State
				RobotStates.driveOverride = DriveOverride.WANTS_TO_HANG;
				
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
