package com.team2169.util;

import com.team2169.robot.Constants;
import com.team2169.robot.Robot;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

@SuppressWarnings("deprecation")
public class ShuffleBoardManager {

	static NetworkTable dash = NetworkTable.getTable("SmartDashboard");

	public static void setDouble(String key, double D) {
		dash.putDouble(key, D);
	}

	public static double getDouble(String key) {
		return dash.getNumber(key, -1);
	}

	public static void getPathfinderConstants() {
		Constants.accelerationGain = getDouble("accelerationGain");
		Constants.timeStep = getDouble("timeStep");
		Constants.maxVelocity = getDouble("maxVelocity");
		Constants.maxAcceleration = getDouble("maxAcceleration");
		Constants.maxJerk = getDouble("maxAcceleration");
		Constants.pathfinderP = getDouble("pathfinderP");
		Constants.pathfinderI = getDouble("pathfinderI");
		Constants.pathfinderD = getDouble("pathfinderD");
	}

	public void init(boolean fms) {

		setDouble("accelerationGain", Constants.accelerationGain);
		setDouble("timeStep", Constants.timeStep);
		setDouble("maxVelocity", Constants.maxVelocity);
		setDouble("maxAcceleration", Constants.maxAcceleration);
		setDouble("maxJerk", Constants.maxJerk);
		setDouble("pathfinderP", Constants.pathfinderP);
		setDouble("pathfinderI", Constants.pathfinderI);
		setDouble("pathfinderD", Constants.pathfinderD);

		if (fms) {
			// FMS Is Attached
			connected();
			SmartDashboard.putNumber("Battery Voltage", Robot.fms.batteryVoltage());
		} else {
			// FMS Is Not Attached
			connected();
			SmartDashboard.putNumber("Battery Voltage", Robot.fms.batteryVoltage());
		}

	}

	public void auto(boolean fms) {

		if (fms) {
			// FMS Is Attached
			batteryData();
			connected();
			SmartDashboard.putNumber("Match Time", Robot.fms.matchTime());
		} else {
			// FMS Is Not Attached
			batteryData();
			connected();
			SmartDashboard.putNumber("Match Time", Robot.fms.matchTime());
		}
	}

	public void teleOp(boolean fms) {

		if (fms) {
			// FMS Is Attached
			batteryData();
			connected();
			SmartDashboard.putNumber("Match Time", Robot.fms.matchTime());
		} else {
			// FMS Is Not Attached
			batteryData();
			connected();
			SmartDashboard.putNumber("Match Time", Robot.fms.matchTime());
		}

	}

	// Local Methods
	void batteryData() {

		SmartDashboard.putNumber("Battery Voltage", Robot.fms.batteryVoltage());
		SmartDashboard.putBoolean("BrownOut Alert", !Robot.fms.browningOut());

	}

	void connected() {

		SmartDashboard.putBoolean("FMS Connected", Robot.fms.fmsActive());
		SmartDashboard.putBoolean("DS Connected", Robot.fms.isDriverStationAttached());

	}

}
