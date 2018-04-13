package com.team2169.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team2169.robot.ActuatorMap;
import com.team2169.robot.Constants;
import com.team2169.robot.ControlMap;
import com.team2169.robot.RobotStates.HookState;
import com.team2169.robot.RobotWantedStates;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climber extends Subsystem {

    private static Climber hInstance = null;
    public static Climber getInstance() {
        if (hInstance == null) {
            hInstance = new Climber();
        }
        return hInstance;
    }
    
    TalonSRX release;

    public Climber() {
    	
    	release = new TalonSRX(ActuatorMap.hookDeplyID);
    	release.setNeutralMode(NeutralMode.Brake);
        RobotWantedStates.climberRelease = false;
    }

    public void climberHandler() {

        ControlMap.getWantedHookRelease();
        
        if(RobotWantedStates.hookRelease == HookState.EXTEND) {
        	release.set(ControlMode.PercentOutput, Constants.hookDeploySpeed);
        	RobotWantedStates.climberRelease = true;
        }
        else if(RobotWantedStates.hookRelease == HookState.RETRACT) {
        	release.set(ControlMode.PercentOutput, -Constants.hookDeploySpeed);
        	RobotWantedStates.climberRelease = false;
        }
        else {
        	release.set(ControlMode.PercentOutput, 0);
        }
        
    }

    @Override
    public void pushToDashboard() {
        SmartDashboard.putNumber("Hook Encoder Amount", release.getSelectedSensorPosition(0));
    }

    @Override
    public void zeroSensors() {

    }

    @Override
    public void stop() {

    }

}
