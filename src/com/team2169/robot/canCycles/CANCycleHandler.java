package com.team2169.robot.canCycles;

import com.team2169.robot.canCycles.cycles.DropAndExhaust;
import com.team2169.robot.canCycles.cycles.SampleCANCycle;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CANCycleHandler {
	
	public static SampleCANCycle sampleCANCycle;
	public static DropAndExhaust dropAndExhaust;
	
	public CANCycleHandler() {
		sampleCANCycle = new SampleCANCycle();
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
