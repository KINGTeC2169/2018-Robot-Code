package com.team2169.robot.auto.modes.switchAutos.right;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.auto.modes.AutoMode;

public class SwitchRRLAuto extends AutoMode {
/*

	
       +-------+         +-------+
       |-------|         |       |
       |-------|         |       |
       |-------|         |       |
       +-------+         +-------+
       	
       +-------+         +-------+
       |       |         |-------|
       |       |         |-------|
       |	   |         |-------|
       +-------+         +-------+

                                   +-----+
                                   |     |
                                   |     |
                                   |     |
                                   +-----+
	 
*/

    public SwitchRRLAuto() {

        RobotStates.runningMode = RunningMode.AUTO;
       

    }

    // Put looping checks/code in here
    public void looper() {

        smartDashPush();

    }

    // Smartdashboard output
    public void smartDashPush() {

    }

}
