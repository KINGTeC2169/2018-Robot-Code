package org.usfirst.frc.team2169.util;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class FMSManager {
	
	DriverStation fms;
	
	public FMSManager(DriverStation fms_) {
		
		fms = fms_;
		
	}
		
		@SuppressWarnings("deprecation")
		double batteryVoltage() {
			return fms.getBatteryVoltage();
		}
		@SuppressWarnings("deprecation")
		boolean browningOut() {
			return fms.isBrownedOut();
		}
		boolean fmsActive() {
			return fms.isFMSAttached();
		}
		Alliance alliance() {
			return fms.getAlliance();
		}
		String allianceName() {
			return fms.getAlliance().toString();
		}
		boolean isDriverStationAttached() {
			return fms.isDSAttached();
		}
		double matchTime() {
			return fms.getMatchTime();
		}
		double remainingTimeAuto() {
			return 15 - fms.getMatchTime();
		}
		double remainingTimeTeleOp() {
			return 150 - fms.getMatchTime();
		}

}
