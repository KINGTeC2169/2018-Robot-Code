package com.team2169.robot.auto.modes.scaleAutos.right;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.auto.modes.AutoMode;

public class ScaleRRRAuto extends AutoMode {
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

    public ScaleRRRAuto() {

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
