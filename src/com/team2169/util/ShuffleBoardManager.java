package com.team2169.util;

import com.team2169.robot.Robot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

@SuppressWarnings("deprecation")
public class ShuffleBoardManager {

    public void init() {

        connected();

    }

    public void auto() {

        connected();
        SmartDashboard.putNumber("Match Time", Robot.fms.matchTime());

    }

    public void teleOp() {

        connected();
        SmartDashboard.putNumber("Match Time", Robot.fms.matchTime());

    }

    private void connected() {

        SmartDashboard.putBoolean("FMS Connected", Robot.fms.fmsActive());
        SmartDashboard.putBoolean("DS Connected", Robot.fms.isDriverStationAttached());

    }
}
