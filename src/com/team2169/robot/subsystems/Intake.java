package com.team2169.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team2169.robot.ActuatorMap;
import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.RobotStates.IntakeClamp;
import com.team2169.robot.RobotStates.IntakeMode;
import com.team2169.util.DebugPrinter;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

import com.team2169.robot.Constants;

public class Intake extends Subsystem{
	
    private static Intake iInstance = null;

    public static Intake getInstance() {
        if (iInstance == null) {
            iInstance = new Intake();
        }
        return iInstance;
    }
	
	private TalonSRX left;
	private TalonSRX right;
	DoubleSolenoid clamp;
	
	public Intake() {
	
		left = new TalonSRX(ActuatorMap.leftIntakeID);
		right = new TalonSRX(ActuatorMap.rightIntakeID);
		right.setInverted(true);
		clamp = new DoubleSolenoid(ActuatorMap.compressorPCMPort, ActuatorMap.clampPortForward, ActuatorMap.clampPortReverse);
	}
	
	public void intakeManual(double power, boolean active) {
		if(active) {
			left.set(ControlMode.PercentOutput, power);
			right.set(ControlMode.PercentOutput, power);	
		}
		else {
			left.set(ControlMode.PercentOutput, 0);
			right.set(ControlMode.PercentOutput, 0);	
		}
		
	}
	
	public void intakeHandler() {
		
			//Handle Intake State
			switch(RobotWantedStates.wantedIntakeMode){
			
			case IDLE: default:
				
				//Stop Intakes
				intakeManual(0, true);
				RobotStates.intakeMode = IntakeMode.IDLE;
				break;
				
			case INTAKE:
				
				//Run Intakes
				intakeManual(-Constants.intakeSpeed, true);
				RobotStates.intakeMode = IntakeMode.INTAKE;
				break;
			
			case EXHAUST:

				//Run Intakes Backwards
				intakeManual(Constants.intakeSpeed, true);
				RobotStates.intakeMode = IntakeMode.EXHAUST;
				break;

			}	
			
			//Handle Wanted Clamp State
			switch(RobotWantedStates.wantedIntakeClamp){
			
			case NEUTRAL: default:
				
				//Set Clamp to Neutral
				clamp.set(Value.kOff);
				RobotStates.intakeClamp = IntakeClamp.NEUTRAL;
				break;
				
			case CLAMP:
				
				//Set Clamp to Clamped
				clamp.set(Value.kForward);
				RobotStates.intakeClamp = IntakeClamp.CLAMP;
				break;
			
			case DROP:

				//Set Clamp to Drop
				clamp.set(Value.kReverse);
				RobotStates.intakeClamp = IntakeClamp.DROP;
				break;

			}	
		}

	@Override
	public void pushToDashboard() {
		
		if(RobotStates.debugMode) {
			DebugPrinter.intakeDebug();
		}
		
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
