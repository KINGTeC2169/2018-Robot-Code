package com.team2169.robot.subsystems;

import com.team2169.robot.ActuatorMap;
import com.team2169.robot.ControlMap;
import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotWantedStates;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;

public class Platform extends Subsystem{

    private static Platform hInstance = null;

    public static Platform getInstance() {
        if (hInstance == null) {
            hInstance = new Platform();
        }
        return hInstance;
    }
	
	Solenoid platformRelease;
	
	public Platform() {
		platformRelease = new Solenoid(ActuatorMap.platformReleasePiston);
		platformRelease.set(false);
		RobotWantedStates.platformRelease = false;
	}
	
	public void platformHandler() {
	
		ControlMap.getWantedPlatform();
		if(RobotWantedStates.platformRelease){
			releasePlatform(true);
		}
		else {
			releasePlatform(false);
		}
		
	}
	
	public void releasePlatform(boolean releaseState) {
		platformRelease.set(releaseState);
		if(releaseState && RobotStates.debugMode) {
			DriverStation.reportWarning("Hanger: Platform realased", false);
		}
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