package com.team2169.robot.auto.modes.switchAutos.right;

import com.team2169.robot.auto.modes.AutoMode;

public class SwitchRLRAuto extends AutoMode {
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

    public SwitchRLRAuto() {


    }

    // Put looping checks/code in here
    public void looper() {

        smartDashPush();

    }

    // Smartdashboard output
    public void smartDashPush() {

    }

}
