package com.team2169.util;

import com.team2169.robot.ActuatorMap;
import com.team2169.robot.Constants;
import com.team2169.robot.RobotStates;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PWM;

public class ArduinoManager {
    private DigitalInput StatusSignal;
    private PWM ArduinoPWM;

    public ArduinoManager() {
        StatusSignal = new DigitalInput(ActuatorMap.prepArduinoDIOPort);
        ArduinoPWM = new PWM(ActuatorMap.arduinoComPort);

    }

    private boolean isConnected() {
        return StatusSignal.get();
    }

    public void arduinoHandler() {
        RobotStates.arduinoConnected = this.isConnected();

        if (RobotStates.blockHeld) {
            ArduinoPWM.setRaw(Constants.arduinoBlockHeldSignal);
        } else {
            ArduinoPWM.setRaw(Constants.arduinoMainSignal);
        }


    }

}
