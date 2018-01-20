package org.usfirst.frc.team2169.robot.subsystems;

import org.usfirst.frc.team2169.robot.ActuatorMap;
import org.usfirst.frc.team2169.robot.RobotStates;
import org.usfirst.frc.team2169.robot.RobotWantedStates;
import org.usfirst.frc.team2169.robot.RobotStates.intakeMode;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

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
		
		//Make sure these enums are actively updated or depended on.
		//This method can be deleted
		
			switch(RobotWantedStates.wantedIntakeMode){
			
			case IDLE:
				//Check if safe
				//Stop Intakes
				RobotStates.intakeMode = intakeMode.IDLE;
				break;
				
			case INTAKE:
				//Check if safe
				//Run Intakes Normally
				RobotStates.intakeMode = intakeMode.INTAKE;
				break;
			
			case EXHAUST:
				//Check if safe
				//Run Intakes Backwards
				RobotStates.intakeMode = intakeMode.EXHAUST;
				break;

			default:
				//Stop Intakes
				break;
			
			}
			
			if(RobotWantedStates.intakeClamp) {
				
				//Retract Piston
				RobotStates.intakeClamp = true;
				
			}
			
			else {
				
				//Extend Pistons
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
