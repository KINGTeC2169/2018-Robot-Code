package com.team2169.robot.auto.modes.scaleAutos.left;

import com.team2169.robot.auto.modes.AutoMode;

public class ScaleLRLAuto extends AutoMode {
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

+----+
|    |
|    |
|    |
+----+
	 
*/

    public ScaleLRLAuto() {


    }

    // Put looping checks/code in here
    public void looper() {

        smartDashPush();

    }

    // Smartdashboard output
    public void smartDashPush() {

    }

}
