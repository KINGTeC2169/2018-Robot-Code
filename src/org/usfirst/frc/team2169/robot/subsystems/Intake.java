package org.usfirst.frc.team2169.robot.subsystems;

import org.usfirst.frc.team2169.robot.ActuatorMap;
import org.usfirst.frc.team2169.robot.ControlMap;
import org.usfirst.frc.team2169.robot.RobotStates;
import org.usfirst.frc.team2169.robot.RobotStates.IntakeMode;
import org.usfirst.frc.team2169.robot.RobotWantedStates;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;

public class Intake extends Subsystem{

	//TODO Add support for Arm and Elevator Lift
	//Needs support for:
	//Macros (preset positions) that can be configured from Constants.java
	//Manual Macro override for Operator 
	//Public getter for exact arm position
	//Support for arm position and elevator positions enums
		//These macros should be in ranges because they will be used for rough CG calculations 
		//by the DriveTrain class to determine acceleration limits to prevent tipping.
	
	
	TalonSRX left;
	TalonSRX right;
	Solenoid clamp; 
	
	public Intake() {
		
		//Lift Motors Setup
		left = new TalonSRX(ActuatorMap.leftIntakeID);
		right = new TalonSRX(ActuatorMap.rightIntakeID);
		right.setInverted(true);
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
				RobotStates.intakeMode = IntakeMode.IDLE;
				break;
				
			case INTAKE:
				//Run Intakes Normally
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("Intakes Intaking", false);
				}
				RobotStates.intakeMode = IntakeMode.INTAKE;
				break;
			
			case EXHAUST:
				//Run Intakes Backwards
				if(RobotStates.debugMode) {
					DriverStation.reportWarning("Intakes Exhaust", false);
				}
				RobotStates.intakeMode = IntakeMode.EXHAUST;
				break;

			}
			
			if(RobotWantedStates.intakeClamp) {
				
				//Retract Piston
				//if(RobotStates.debugMode) {
					DriverStation.reportWarning("Intakes Clamped", false);
				//}
				RobotStates.intakeClamp = true;
				
			}
			
			else if(!RobotWantedStates.intakeClamp) {
				
				//Extend Pistons
				//if(RobotStates.debugMode) {
					DriverStation.reportWarning("Intakes Unclamped", false);
				//}
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
