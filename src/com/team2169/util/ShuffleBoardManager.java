package com.team2169.util;

import com.team2169.robot.Robot;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

@SuppressWarnings("deprecation")
public class ShuffleBoardManager {

	NetworkTable dash = NetworkTable.getTable("SmartDashboard");

	public void setDouble(String key, double D){
		dash.putDouble(key, D);
	}

	public void getDouble(String key){
		dash.getNumber(key, 0);
	}
	
	
	public void init(boolean fms) {	
		setDouble("maxVelocity", 0);
		setDouble("pathFinderD", 0);
		setDouble("accelerationGain", 0);
		setDouble("timeStep", 0);
		setDouble("pathfinderP", 0);
		setDouble("pathfinderI", 0);
		setDouble("maxAcceleration", 0);
		setDouble("maxJerk", 0);
		
		
		
		if(fms) {
			//FMS Is Attached
			connected();
			SmartDashboard.putNumber("Battery Voltage", Robot.fms.batteryVoltage());
		}
		else {
			//FMS Is Not Attached
			connected();
			SmartDashboard.putNumber("Battery Voltage", Robot.fms.batteryVoltage());
		}

	}
	
	public void auto(boolean fms) {
	
		if(fms) {
			//FMS Is Attached
			batteryData();
			connected();
			SmartDashboard.putNumber("Match Time", Robot.fms.matchTime());
		}
		else {
			//FMS Is Not Attached
			batteryData();
			connected();
			SmartDashboard.putNumber("Match Time", Robot.fms.matchTime());
		}
	}
	
	public void teleOp(boolean fms) {
		
		if(fms) {
			//FMS Is Attached
			batteryData();
			connected();
			SmartDashboard.putNumber("Match Time", Robot.fms.matchTime());
		}
		else {
			//FMS Is Not Attached
			batteryData();
			connected();
			SmartDashboard.putNumber("Match Time", Robot.fms.matchTime());
		}
		
	}
	
		//Local Methods
		void batteryData() {
			
			SmartDashboard.putNumber("Battery Voltage", Robot.fms.batteryVoltage());
			SmartDashboard.putBoolean("BrownOut Alert", !Robot.fms.browningOut());
			
			
		}
		
		void connected() {
		
			SmartDashboard.putBoolean("FMS Connected", Robot.fms.fmsActive());
			SmartDashboard.putBoolean("DS Connected", Robot.fms.isDriverStationAttached());
		
		}
	
}
