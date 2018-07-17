package com.team2169.util;

import com.team2169.robot.RobotStates;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShuffleBoardManager {

	SendableChooser<Boolean> driveTypeChooser = new SendableChooser<>();
	
    public void init() {

    	driveTypeChooser.addDefault("No", false);
    	driveTypeChooser.addObject("Yes", true);
    	SmartDashboard.putData(driveTypeChooser);
        connected();

    }

    public void auto() {

        connected();
        SmartDashboard.putNumber("Match Time", DriverStation.getInstance().getMatchTime());

    }

    public void teleOp() {

    	RobotStates.cheesyDrive = driveTypeChooser.getSelected();
        connected();
        SmartDashboard.putNumber("Match Time", DriverStation.getInstance().getMatchTime());

    }

    private void connected() {

        SmartDashboard.putBoolean("FMS Connected", DriverStation.getInstance().isFMSAttached());
        SmartDashboard.putBoolean("DS Connected", DriverStation.getInstance().isDSAttached());

    }
}
