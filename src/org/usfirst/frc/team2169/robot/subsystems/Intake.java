package org.usfirst.frc.team2169.robot.subsystems;

import org.usfirst.frc.team2169.robot.RobotStates;
import org.usfirst.frc.team2169.robot.RobotStates.intakeMode;

public class Intake extends Subsystem{
	
	//TODO Make Talon Objects
	//TODO Make method for intake and outake
	//TODO Add support for RobotMode Intake enum
	
	
	public void intake() {
	
		//Do it however you want, just make sure these are supported :)
		RobotStates.intakeMode = intakeMode.IDLE;
		RobotStates.intakeMode = intakeMode.INTAKE;
		RobotStates.intakeMode = intakeMode.OUTTAKE;
	
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
