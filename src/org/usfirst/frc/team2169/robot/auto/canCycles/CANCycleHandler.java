package org.usfirst.frc.team2169.robot.auto.canCycles;

import org.usfirst.frc.team2169.robot.auto.canCycles.cycles.SampleCANCycle;
import org.usfirst.frc.team2169.robot.auto.tasks.TestTask;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CANCycleHandler {
	
	public SampleCANCycle sampleCANCycle;
	public TestTask test;
	
	public CANCycleHandler() {
		sampleCANCycle = new SampleCANCycle();
		test = new TestTask();
	}
	
	public static void startCycle(CommandGroup command) {
		
		if(!command.isRunning()) {
			command.start();
		}
		
	}
	
	public void cancelAllCycles() {
		//Do This for all CANCycles
		if(!sampleCANCycle.isRunning()) {
			sampleCANCycle.cancel();
		}
	}

}
