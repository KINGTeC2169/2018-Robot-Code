package com.team2169.robot.canCycles;

import java.util.List;

import com.team2169.robot.canCycles.cycles.DropAndExhaust;
import com.team2169.robot.canCycles.cycles.SampleCANCycle;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CANCycleHandler {
	
	public static SampleCANCycle sampleCANCycle;
	public static DropAndExhaust dropAndExhaust;
	static List<CommandGroup> elevatorTasks;
	static List<CommandGroup> allTasks;
	
	
	public CANCycleHandler() {
		sampleCANCycle = new SampleCANCycle();
		dropAndExhaust = new DropAndExhaust();
		elevatorTasks.add(dropAndExhaust);
		allTasks.add(dropAndExhaust);
	}
	
	public static void startCycle(CommandGroup command) {
		
		if(!command.isRunning()) {
			command.start();
		}
		
	}
	
	public static void cancelArmElevatorCycles() {
		//Do This for all Arm/Elevator CANCycles
		for(CommandGroup cycle: elevatorTasks) {
			if(cycle.isRunning()) {
				cycle.cancel();			
			}
		}
		
	}
	
	public static void cancelAllCycles() {
		//Do This for all CANCycles
		for(CommandGroup cycle: allTasks) {
			if(cycle.isRunning()) {
				cycle.cancel();			
			}
		}
	}
}
