package com.team2169.robot.auto.modes;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class AutoMode extends CommandGroup {

    public abstract void looper();

    public abstract void smartDashPush();
    
    public String autoName;
    
    public void printName() {
    	SmartDashboard.putString("Running Auto", autoName);
    	DriverStation.reportError("Game Message: " + DriverStation.getInstance().getGameSpecificMessage(), false);
    }

}
