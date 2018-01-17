package org.usfirst.frc.team2169.robot.auto.canCycles;

public class CANCycleHandler {
	
	public static SampleCANCycle sampleCANCycle;
	
	public CANCycleHandler() {
		sampleCANCycle = new SampleCANCycle();
	}
	
	public static void cancelAllCycles() {
		//Do This for all CANCycles
		if(!sampleCANCycle.isRunning()) {
			sampleCANCycle.cancel();
		}
	}

}
