package com.team2169.robot.auto.modes.scaleAutos.right;

import com.team2169.robot.auto.modes.AutoMode;

public class ScaleRLRAuto extends AutoMode {
/*

	
       +-------+         +-------+
       |       |         |-------|
       |       |         |-------|
       |	   |         |-------|
       +-------+         +-------+

       +-------+         +-------+
       |-------|         |       |
       |-------|         |       |
       |-------|         |       |
       +-------+         +-------+

                                   +-----+
                                   |     |
                                   |     |
                                   |     |
                                   +-----+

*/

    public ScaleRLRAuto() {


    }

    // Put looping checks/code in here
    public void looper() {

        smartDashPush();

    }

    // Smartdashboard output
    public void smartDashPush() {

    }

}
