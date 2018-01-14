package org.usfirst.frc.team2169.util;

import com.kauailabs.navx.frc.AHRS;

public class SensorFusionManager {
	
	AHRS gyro;
	
	public SensorFusionManager(AHRS gyro_){
		
		gyro = gyro_;
		
	}

}
