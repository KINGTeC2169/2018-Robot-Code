package org.usfirst.frc.team2169.robot.subsystems;

//import org.usfirst.frc.team2169.robot.ActuatorMap;
import org.usfirst.frc.team2169.robot.auto.canCycles.CANCycleHandler;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.SPI;

public class Superstructure {
	
	public static AHRS navX;
	CANCycleHandler canHandler;
	public static DriveTrain drive;
	static Compressor comp;
	 
	
	public Superstructure(){
		
		//comp = new Compressor(ActuatorMap.compressorPCMPort);
		drive = new DriveTrain();
		navX = new AHRS(SPI.Port.kMXP, (byte)200);
		canHandler = new CANCycleHandler();
		
	}

	public void robotInit() {

		//comp.start();
		
	}
	
	public void teleOpLoop() {
		
		//CANCycleHandler.startCycle(CANCycleHandler.sampleCANCycle);
		
	}
	
	//This is how you start a CANCycle
	//CANCycleHandler.sampleCANCycle.start();
	
	//This is how you cancel a CANCycle
	//CANCycleHandler.sampleCANCycle.cancel();
	
}
