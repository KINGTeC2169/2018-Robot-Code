package org.usfirst.frc.team2169.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;

public class Superstructure {
	
	public static AHRS navX;
	public static DriveTrain drive;
	
	
	public Superstructure(){
		
		drive = new DriveTrain();
		navX = new AHRS(SPI.Port.kMXP, (byte)200);
	
	}
	
	//This is how you start a CANCycle
	//CANCycleHandler.sampleCANCycle.start();
	
	//This is how you cancel a CANCycle
	//CANCycleHandler.sampleCANCycle.cancel();
	
}
