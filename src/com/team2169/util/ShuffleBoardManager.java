package com.team2169.util;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ShuffleBoardManager {

    public void init() {

        connected();

    }

    public void auto() {

        connected();
        SmartDashboard.putNumber("Match Time", DriverStation.getInstance().getMatchTime());

    }

    public void teleOp() {

        connected();
        SmartDashboard.putNumber("Match Time", DriverStation.getInstance().getMatchTime());

    }

    private void connected() {

        SmartDashboard.putBoolean("FMS Connected", DriverStation.getInstance().isFMSAttached());
        SmartDashboard.putBoolean("DS Connected", DriverStation.getInstance().isDSAttached());

    }
}
