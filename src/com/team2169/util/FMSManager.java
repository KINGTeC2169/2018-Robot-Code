package com.team2169.util;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class FMSManager {
	
	DriverStation fms;
	
	public FMSManager(DriverStation fms_) {
		
		fms = fms_;
		
	}
		
		@SuppressWarnings("deprecation")
		public double batteryVoltage() {
			return fms.getBatteryVoltage();
		}
		@SuppressWarnings("deprecation")
		public boolean browningOut() {
			return fms.isBrownedOut();
		}
		public boolean fmsActive() {
			return fms.isFMSAttached();
		}
		public Alliance alliance() {
			return fms.getAlliance();
		}
		public String allianceName() {
			return fms.getAlliance().toString();
		}
		public boolean isDriverStationAttached() {
			return fms.isDSAttached();
		}
		public double matchTime() {
			return fms.getMatchTime();
		}
		public double remainingTimeAuto() {
			return 15 - fms.getMatchTime();
		}
		public double remainingTimeTeleOp() {
			return 200 - fms.getMatchTime();
		}

}
