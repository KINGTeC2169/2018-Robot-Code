package org.usfirst.frc.team2169.robot.subsystems;

import org.usfirst.frc.team2169.robot.ActuatorMap;
import org.usfirst.frc.team2169.robot.ControlMap;
import org.usfirst.frc.team2169.robot.RobotStates;
import org.usfirst.frc.team2169.robot.RobotStates.IntakeMode;
import org.usfirst.frc.team2169.robot.RobotWantedStates;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DriverStation;

public class Intake extends Subsystem{
	
    private static Intake iInstance = null;

    public static Intake getInstance() {
        if (iInstance == null) {
            iInstance = new Intake();
        }
        return iInstance;
    }
	
	TalonSRX left;
	TalonSRX right;
	DoubleSolenoid clamp;
	
	public Intake() {
	
		left = new TalonSRX(ActuatorMap.leftIntakeID);
		right = new TalonSRX(ActuatorMap.rightIntakeID);
		right.setInverted(true);
		clamp = new DoubleSolenoid(ActuatorMap.compressorPCMPort, ActuatorMap.clampPortForward, ActuatorMap.clampPortReverse);
	}
	
	void intake(double power) {
		left.set(ControlMode.PercentOutput, power);
		right.set(ControlMode.PercentOutput, power);
		
	}
	
	public void intakeHandler() {
		
			//Get WantedState from ControlMap
			ControlMap.getWantedIntake();
		
			//Set Intakes to whatever drivers want
			switch(RobotWantedStates.wantedIntakeMode){
			
			case IDLE: default:
				//Stop Intakes
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("Intakes Idle", false);
				}
				intake(0);
				RobotStates.intakeMode = IntakeMode.IDLE;
				break;
				
			case INTAKE:
				//Run Intakes Normally
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("Intakes Intaking", false);
				}
				intake(-1);
				RobotStates.intakeMode = IntakeMode.INTAKE;
				break;
			
			case EXHAUST:
				//Run Intakes Backwards
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("Intakes Exhaust", false);
				}
				intake(1);
				RobotStates.intakeMode = IntakeMode.EXHAUST;
				break;

			}
			
			if(RobotWantedStates.intakeClamp) {
				
				//Retract Piston
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("Intakes Clamped", false);
				}
				clamp.set(Value.kReverse);
				RobotStates.intakeClamp = true;
				
			}
			
			else if(!RobotWantedStates.intakeClamp) {
				
				//Extend Pistons
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("Intakes Unclamped", false);
				}
				clamp.set(Value.kForward);
				RobotStates.intakeClamp = false;
				
			}
		
		}

	@Override
	public void pushToDashboard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void zeroSensors() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	
}
