package org.usfirst.frc.team2169.robot.auto.canCycles;

import org.usfirst.frc.team2169.robot.auto.canCycles.cycles.SampleCANCycle;
import org.usfirst.frc.team2169.robot.auto.tasks.TestTask;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CANCycleHandler {
	
	public static SampleCANCycle sampleCANCycle;
	public static TestTask test;
	
	public CANCycleHandler() {
		sampleCANCycle = new SampleCANCycle();
		test = new TestTask();
	}
	
	public static void startCycle(CommandGroup command) {
		
		if(!command.isRunning()) {
			command.start();
		}
		
	}
	
	public static void cancelArmElevatorCycles() {
		//Do This for all Arm/Elevator CANCycles
		if(sampleCANCycle.isRunning()) {
			sampleCANCycle.cancel();			
		}
	}
	
	public static void cancelAllCycles() {
		//Do This for all CANCycles
		if(!sampleCANCycle.isRunning()) {
			sampleCANCycle.cancel();
		}
	}

}
