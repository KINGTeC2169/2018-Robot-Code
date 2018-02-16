package com.team2169.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team2169.robot.ActuatorMap;
import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.RobotWantedStates.WantedIntakeClamp;
import com.team2169.robot.RobotStates.IntakeClamp;
import com.team2169.robot.RobotStates.IntakeMode;
import com.team2169.util.DebugPrinter;
import edu.wpi.first.wpilibj.Ultrasonic;

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
    
	Ultrasonic ultra;
	private TalonSRX left;
	private TalonSRX right;
	DoubleSolenoid clamp;
	
	public Intake() {
		ultra = new Ultrasonic(ActuatorMap.intakeUltrasonicOutputPort, ActuatorMap.intakeUltrasonicInputPort);
		left = new TalonSRX(ActuatorMap.leftIntakeID);
		right = new TalonSRX(ActuatorMap.rightIntakeID);
		right.setInverted(true);
		clamp = new DoubleSolenoid(ActuatorMap.compressorPCMPort, ActuatorMap.clampPortForward, ActuatorMap.clampPortReverse);
		RobotWantedStates.wantedIntakeClamp = WantedIntakeClamp.CLAMP;
		ultra.setAutomaticMode(true);
	}
	
	public void intakeManual(double power) {
		left.set(ControlMode.PercentOutput, power);
		right.set(ControlMode.PercentOutput, power);	
	}
	public double getBlockDistance(){
		return ultra.getRangeInches();
	}
	
	public void intakeHandler() {
		
			RobotStates.ultraWithinRange = (getBlockDistance() <= Constants.maxUltraTriggerDistance && getBlockDistance() >= Constants.minUltraTriggerDistance);

			if(RobotStates.operatorWantsUltrasonic && RobotStates.ultraWithinRange) {
				RobotWantedStates.wantedIntakeClamp = WantedIntakeClamp.CLAMP;
			}
			
			//Handle Intake State
			switch(RobotWantedStates.wantedIntakeMode){
			
			case IDLE: default:
				
				//Stop Intakes
				intakeManual(0);
				
				
				RobotStates.intakeMode = IntakeMode.IDLE;
				
				break;
				
			case INTAKE:
				
				//Run Intakes

				intakeManual(-Constants.intakeSpeed);
				

				RobotStates.intakeMode = IntakeMode.INTAKE;
				break;
			
			case EXHAUST:

				//Run Intakes Backwards
				intakeManual(Constants.intakeSpeed);
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
			
			pushToDashboard();
			
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
