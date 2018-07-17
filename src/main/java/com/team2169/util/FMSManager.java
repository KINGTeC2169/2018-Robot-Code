package com.team2169.util;

import edu.wpi.first.wpilibj.DriverStation;

public class FMSManager {

    DriverStation fms;

    public FMSManager(DriverStation fms_) {

        fms = fms_;

    }

    public boolean fmsActive() {
        return fms.isFMSAttached();
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

    public String getGameMessage() {
        return fms.getGameSpecificMessage();
    }

}
