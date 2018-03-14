package com.team2169.robot.canCycles;

import com.team2169.robot.RobotStates;
import com.team2169.robot.canCycles.cycles.DropAndExhaust;
import edu.wpi.first.wpilibj.command.CommandGroup;

import java.util.ArrayList;
import java.util.List;

public class CANCycleHandler {


    public static DropAndExhaust dropAndExhaust;
    private static List<CommandGroup> allTasks;

    CANCycleHandler() {
        allTasks = new ArrayList<>();
    }

    public static void createCycles() {
        dropAndExhaust = new DropAndExhaust();
        allTasks.add(dropAndExhaust);
    }

    public static void cancelArmElevatorCycles() {
        // Do This for all Arm/Elevator CANCycles
        if (dropAndExhaust.isRunning()) {
            dropAndExhaust.cancel();
        }
        RobotStates.canCycleMode = false;

    }

    public static void cancelAllCycles() {
        // Do This for all CANCycles
        for (CommandGroup cycle : allTasks) {
            if (cycle.isRunning()) {
                cycle.cancel();
            }
        }
    }
}
