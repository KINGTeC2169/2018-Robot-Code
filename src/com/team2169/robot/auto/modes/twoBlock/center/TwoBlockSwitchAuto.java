package com.team2169.robot.auto.modes.twoBlock.center;

import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.auto.AutoConstants.ElementSide;
import com.team2169.robot.auto.modes.AutoMode;


public class TwoBlockSwitchAuto extends AutoMode {
/*

	
       +-------+         +-------+
       |       |         |       |
       |       |         |       |
       |       |         |       |
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

    @SuppressWarnings("unused")
	public TwoBlockSwitchAuto(ElementSide side) {

        RobotStates.runningMode = RunningMode.AUTO;
        this.autoName = "Center Switch Auto on " + side.name() + " side.";
        boolean inversion = false;

        if(side == ElementSide.RIGHT) {
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
