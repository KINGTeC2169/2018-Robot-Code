package com.team2169.util;
import com.team2169.robot.ActuatorMap;
import com.team2169.robot.Constants;
import com.team2169.robot.RobotStates;
import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.DigitalInput;

public class ArduinoManager {
		public DigitalInput StatusSignal;
		public PWM ArduinoPWM;
	
	public  ArduinoManager(){
		StatusSignal = new DigitalInput(ActuatorMap.prepArduinoDIOPort);
		ArduinoPWM = new PWM(ActuatorMap.arduinoComPort);
		
	}
	
	public boolean isConnected(){
		return StatusSignal.get();
	}
	
	public void arduinoHandler(){
		RobotStates.arduinoConnected = this.isConnected();
		
		if (RobotStates.blockHeld){
			ArduinoPWM.setRaw(Constants.arduinoBlockHeldSignal);
		} else {
			ArduinoPWM.setRaw(Constants.arduinoMainSignal);
		}
		
		
	}
	
}
