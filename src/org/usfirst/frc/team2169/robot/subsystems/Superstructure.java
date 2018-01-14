package org.usfirst.frc.team2169.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;

public class Superstructure {
	
	public static AHRS navX;
	
	public Superstructure(){
		
		navX = new AHRS(SPI.Port.kMXP, (byte)200);

	}
	
}
