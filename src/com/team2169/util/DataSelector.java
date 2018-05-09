package com.team2169.util;

import com.team2169.robot.Constants;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

@SuppressWarnings("deprecation")
public class DataSelector {

	static NetworkTable table;
	
	public static void pushValues() {
		
		table = NetworkTable.getTable("SmartDashboard");
		table.putValue("P", Constants.armData.p);
		table.putValue("I", Constants.armData.i);
		table.putValue("D", Constants.armData.d);
		table.putValue("F", Constants.armData.f);
		
	}
	
	public static void looper() {
		Constants.armData.p = table.getDouble("P", Constants.armData.p);
		Constants.armData.i = table.getDouble("I", Constants.armData.i);
		Constants.armData.d = table.getDouble("D", Constants.armData.d);
		Constants.armData.f = table.getDouble("F", Constants.armData.f);
	}
	
}
