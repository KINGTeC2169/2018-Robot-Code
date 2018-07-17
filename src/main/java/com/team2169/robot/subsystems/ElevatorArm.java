package com.team2169.robot.subsystems;

import com.team2169.robot.Constants;
import com.team2169.robot.RobotStates;

public class ElevatorArm extends Subsystem {

    private Arm arm;

    private Elevator elevator;

    private static ElevatorArm eInstance = null;

    public static ElevatorArm getInstance() {
        if (eInstance == null) {
            eInstance = new ElevatorArm();
        }
        return eInstance;
    }

    private ElevatorArm() {

        arm = new Arm();
        elevator = new Elevator();

        RobotStates.elevatorInPosition = false;
        Constants.setElevatorDataFromConstants();

    }

    void elevatorArmHandler() {

        // Arm Handler
        arm.armMacroLooper();

        // Elevator Handler
        elevator.elevatorMacroLooper();

    }

    @Override
    public void pushToDashboard() {

        arm.pushToDashboard();
        elevator.pushToDashboard();
        
    }

    @Override
    public void zeroSensors() {

        elevator.zeroSensors();

    }

    @Override
    public void stop() {

        elevator.stop();
        arm.stop();

    }

}
