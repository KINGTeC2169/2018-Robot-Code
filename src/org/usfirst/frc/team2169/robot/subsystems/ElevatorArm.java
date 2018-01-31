package org.usfirst.frc.team2169.robot.subsystems;

import org.usfirst.frc.team2169.robot.ActuatorMap;
import org.usfirst.frc.team2169.robot.Constants;
import org.usfirst.frc.team2169.robot.ControlMap;
import org.usfirst.frc.team2169.robot.RobotStates;
import org.usfirst.frc.team2169.robot.RobotWantedStates;
import org.usfirst.frc.team2169.robot.RobotStates.ArmPos;
import org.usfirst.frc.team2169.robot.RobotStates.ElevatorPos;
import org.usfirst.frc.team2169.robot.canCycles.CANCycleHandler;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DriverStation;

public class ElevatorArm extends Subsystem{
	
    private static ElevatorArm eInstance = null;

    public static ElevatorArm getInstance() {
        if (eInstance == null) {
            eInstance = new ElevatorArm();
        }
        return eInstance;
    }
	
	TalonSRX lift;
	TalonSRX liftSlave;
	TalonSRX arm;
	TalonSRX armSlave;
	
	public ElevatorArm() {
		
		//Lift Motors Setup
		RobotStates.elevatorInPosition = false;
		lift = new TalonSRX(ActuatorMap.liftMasterID);
		liftSlave = new TalonSRX(ActuatorMap.liftSlaveID);
		liftSlave.set(ControlMode.Follower, ActuatorMap.liftMasterID);
		lift.getLastError();
			//Elevator Height Configuration
			/* first choose the sensor */
			lift.configSelectedFeedbackSensor(FeedbackDevice.Analog, Constants.liftPIDLoopIdx, Constants.liftTimeoutMs);
			lift.setSensorPhase(true);
			lift.setInverted(false);
	
			/* Set relevant frame periods to be at least as fast as periodic rate */
			lift.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.liftTimeoutMs);
			lift.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.liftTimeoutMs);
	
			/* set the peak and nominal outputs */
			lift.configNominalOutputForward(0, Constants.liftTimeoutMs);
			lift.configNominalOutputReverse(0, Constants.liftTimeoutMs);
			lift.configPeakOutputForward(1, Constants.liftTimeoutMs);
			lift.configPeakOutputReverse(-1, Constants.liftTimeoutMs);
	
			/* set closed loop gains in slot0 - see documentation */
			lift.selectProfileSlot(Constants.liftSlotIdx, Constants.liftPIDLoopIdx);
			lift.config_kF(0, Constants.liftF, Constants.liftTimeoutMs);
			lift.config_kP(0, Constants.liftP, Constants.liftTimeoutMs);
			lift.config_kI(0, Constants.liftI, Constants.liftTimeoutMs);
			lift.config_kD(0, Constants.liftD, Constants.liftTimeoutMs);
			
			/* set acceleration and vcruise velocity - see documentation */
			lift.configMotionCruiseVelocity(15000, Constants.liftTimeoutMs);
			lift.configMotionAcceleration(6000, Constants.liftTimeoutMs);
			
			/* zero the sensor */
			lift.setSelectedSensorPosition(0, Constants.liftPIDLoopIdx, Constants.liftTimeoutMs);

		//Arm Motors Setup
		arm = new TalonSRX(ActuatorMap.armMasterID);
		armSlave = new TalonSRX(ActuatorMap.armSlaveID);
		armSlave.set(ControlMode.Follower, ActuatorMap.armMasterID);
	}
	
	void elevatorToPos(double pos) {
		lift.set(ControlMode.MotionMagic, pos);
	}
	
	public void getFinishedState() {
	
		if(lift.getClosedLoopError(Constants.liftPIDLoopIdx) < Constants.liftAllowedError || lift.getClosedLoopError(Constants.liftPIDLoopIdx) > -Constants.liftAllowedError) {
			RobotStates.elevatorInPosition = true;
		}
		RobotStates.elevatorInPosition = false;
	
	}
	
	public void elevatorHandler() {
		
		//Make sure these enums are actively updated or depended on.
		//This method can be deleted
		
		if(ControlMap.operatorOverrideActive()) {
			
			//Cancel all CANCycles related to Elevator/Arm
			CANCycleHandler.cancelArmElevatorCycles();
			
			
			if(RobotStates.debugMode) {
				DriverStation.reportWarning("Operator Override Active", false);
			}

			//Set power to arm based directly on operator input
			if(ControlMap.isArmOverrideActive()) {
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("Arm Override Active", false);
				}				
				arm.set(ControlMode.PercentOutput, ControlMap.armOverrideValue());
			}

			//Set power to elevator based directly on operator input			
			if(ControlMap.isElevatorOverrideActive()) {
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("Elevator Override Active", false);
				}	
				lift.set(ControlMode.PercentOutput, ControlMap.elevatorOverrideValue());	
			}
			
		}
		
		else {
			
			//Pull WantedState from ControlMap
			ControlMap.getWantedElevatorPos();
			
			//set robot's actual state to WantedState's value
			switch(RobotWantedStates.wantedElevatorPos){
			case GROUND:
				
				//CANCycle for Ground Position
				
				//If DebugMode, print info
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("Running Macro: Ground", false);
				}
				
				//Actuate the Motor
				elevatorToPos(Constants.groundElevatorEncoderPosition);
				
				//Set RobotStates
				RobotStates.armPos = ArmPos.EXTENDED;
				RobotStates.elevatorPos = ElevatorPos.GROUND;
				break;
				
			case HANG:
			
				//CANCycle Hang Position
				
				//If DebugMode, print info
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("Running Macro: Hang", false);
				}
				
				//Actuate the Motor
				elevatorToPos(Constants.hangElevatorEncoderPosition);
				
				//Set RobotStates
				RobotStates.armPos = ArmPos.FULLY_RETRACTED;
				RobotStates.elevatorPos = ElevatorPos.HANG;
				break;
			
			case SCALE_HIGH:
				
				//CANCycle for (High) Position
				
				//If DebugMode, print info
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("Running Macro: Scale High", false);
				}
				
				//Actuate the Motor
				elevatorToPos(Constants.scaleHighElevatorEncoderPosition);
				
				//Set Robot States
				RobotStates.armPos = ArmPos.EXTENDED;
				RobotStates.elevatorPos = ElevatorPos.SCALE_HIGH;
				break;
				
			case SCALE_LOW:
				
				//CANCycle for Scale (Low) Position
				
				//If DebugMode, print info
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("Running Macro: Scale Low", false);
				}
				
				//Actuate the Motor
				elevatorToPos(Constants.scaleLowElevatorEncoderPosition);

				//Set Robot States
				RobotStates.armPos = ArmPos.EXTENDED;
				RobotStates.elevatorPos = ElevatorPos.SCALE_LOW;
				break;
				
			case SCALE_MID:
				
				//CANCycle for Scale (Mid) Position

				//If DebugMode, print info
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("Running Macro: Scale Mid", false);
				}
				
				//Actuate the Motor
				elevatorToPos(Constants.scaleMidElevatorEncoderPosition);
				
				//Set Robot States
				RobotStates.armPos = ArmPos.EXTENDED;
				RobotStates.elevatorPos = ElevatorPos.SCALE_MID;
				break;
				
			case SWITCH:
		
				//If DebugMode, print info
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("Running Macro: Switch", false);
				}
				
				//Actuate the Motor
				elevatorToPos(Constants.scaleMidElevatorEncoderPosition);
				
				//Set Robot States
				RobotStates.armPos = ArmPos.EXTENDED;
				RobotStates.elevatorPos = ElevatorPos.SWITCH;
				break;
				
			default:
				break;

			}
			
		}

			//Return Elevator Height from yo-yo sensor
			RobotStates.elevatorHeight = lift.getSelectedSensorPosition(Constants.liftSlotIdx);
			getFinishedState();
			
	}

	@Override
	public void pushToDashboard() {
		
	}

	@Override
	public void zeroSensors() {
		
	}

	@Override
	public void stop() {
		
	}
	
}
