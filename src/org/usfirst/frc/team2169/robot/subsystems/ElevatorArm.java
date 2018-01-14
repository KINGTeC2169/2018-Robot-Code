package org.usfirst.frc.team2169.robot.subsystems;

import org.usfirst.frc.team2169.robot.RobotStates;
import org.usfirst.frc.team2169.robot.RobotStates.armPos;
import org.usfirst.frc.team2169.robot.RobotStates.elevatorPos;

public class ElevatorArm extends Subsystem{

	//TODO Add support for Arm and Elevator Lift
	//Needs support for:
	//Macros (preset positions) that can be configured from Constants.java
	//Manual Macro override for Operator 
	//Public getter for exact arm position
	//Support for arm position and elevator positions enums
		//These macros should be in ranges because they will be used for rough CG calculations 
		//by the DriveTrain class to determine acceleration limits to prevent tipping.

	
	public void enumExamples() {
		
		//Make sure these enums are actively updated or depended on.
		//This method can be deleted
		
			//Arm Enums
			RobotStates.armPos = armPos.FULLY_RETRACTED;
			RobotStates.armPos = armPos.PARTIALLY_RETRACTED;
			RobotStates.armPos = armPos.GROUND;
			
			//Elevator Emums
			RobotStates.elevatorPos = elevatorPos.GROUND;
			RobotStates.elevatorPos = elevatorPos.SWITCH;
			RobotStates.elevatorPos = elevatorPos.SCALE_LOW;
			RobotStates.elevatorPos = elevatorPos.SCALE_MID;
			RobotStates.elevatorPos = elevatorPos.SCALE_HIGH;
			RobotStates.elevatorPos = elevatorPos.HANG;
			
			//Make this return a number between 0 and 1 representing a percent of maximum height of the elevator.
			//Ex. If elevator is halfway up, this number equals .5
			RobotStates.elevatorHeight = 0;
	
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
