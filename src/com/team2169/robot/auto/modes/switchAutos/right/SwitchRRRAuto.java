package com.team2169.robot.auto.modes.switchAutos.right;

import com.team2169.robot.RobotStates;
import com.team2169.robot.auto.modes.AutoMode;

public class SwitchRRRAuto extends AutoMode {
/*

	
       +-------+         +-------+
       |       |         |-------|
       |       |         |-------|
       |       |         |-------|
       +-------+         +-------+
     	
       +-------+         +-------+
       |       |         |-------|
       |       |         |-------|
       |       |         |-------|
       +-------+         +-------+

                                   +-----+
                                   |     |
                                   |     |
                                   |     |
                                   +-----+

*/

    public SwitchRRRAuto() {

        RobotStates.runningMode = RobotStates.RunningMode.AUTO;


    }

    // Put looping checks/code in here
    public void looper() {

        smartDashPush();

    }

    // Smartdashboard output
    public void smartDashPush() {

    }

}
