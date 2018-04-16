package com.team2169.robot.auto.modes.twoBlock.side;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.auto.AutoConstants.RobotSide;
import com.team2169.robot.auto.modes.AutoMode;

public class TwoBlockScaleFarAuto extends AutoMode {
/*

	
       +-------+         +-------+
       |       |         |-------|
       |       |         |-------|
       |	   |         |-------|
       +-------+         +-------+

       +-------+         +-------+
       |       |         |       |
       |       |         |       |
       |       |         |       |
       +-------+         +-------+

+----+
|    |
|    |
|    |
+----+
	 
*/

    @SuppressWarnings("unused")
	public TwoBlockScaleFarAuto(RobotSide side) {
        
        RobotStates.runningMode = RunningMode.AUTO;
        this.autoName = "Far 2 Block Auto on " + side.name() + " side.";
        boolean inversion = false;

        if(side == RobotSide.RIGHT) {
        	inversion = true;
        }
        
    }

    // Put looping checks/code in here
    public void looper() {

        smartDashPush();

    }

    // Smartdashboard output
    public void smartDashPush() {

    }

}
