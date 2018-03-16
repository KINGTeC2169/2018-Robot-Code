package com.team2169.robot.canCycles;

import com.team2169.robot.RobotStates;
import com.team2169.robot.canCycles.cycles.DropAndExhaust;

public class CANCycleHandler {


    public static DropAndExhaust dropAndExhaust;

    public static void createCycles() {
    	dropAndExhaust = new DropAndExhaust();
    }

    public static void cancelArmElevatorCycles() {
        // Do This for all Arm/Elevator CANCycles
        if (dropAndExhaust.isRunning()) {
            dropAndExhaust.cancel();
        }
        RobotStates.canCycleMode = false;

    }
}
