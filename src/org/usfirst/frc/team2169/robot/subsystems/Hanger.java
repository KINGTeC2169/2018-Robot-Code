package org.usfirst.frc.team2169.robot.subsystems;

import org.usfirst.frc.team2169.robot.ActuatorMap;
import org.usfirst.frc.team2169.robot.ControlMap;
import org.usfirst.frc.team2169.robot.RobotWantedStates;

import edu.wpi.first.wpilibj.Solenoid;

public class Hanger extends Subsystem{

    private static Hanger hInstance = null;

    public static Hanger getInstance() {
        if (hInstance == null) {
            hInstance = new Hanger();
        }
        return hInstance;
    }
	
	Solenoid platformRelease;
	
	public Hanger() {
		platformRelease = new Solenoid(ActuatorMap.platformReleasePiston);
		platformRelease.set(false);
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
