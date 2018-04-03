package com.team2169.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team2169.robot.ActuatorMap;
import com.team2169.robot.ControlMap;
import com.team2169.robot.RobotStates.HookState;
import com.team2169.robot.RobotWantedStates;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climber extends Subsystem {

    private static Climber hInstance = null;
    TalonSRX release;

    public static Climber getInstance() {
        if (hInstance == null) {
            hInstance = new Climber();
        }
        return hInstance;
    }

    public Climber() {
    	
    	release = new TalonSRX(ActuatorMap.hookDeplyID);
    	release.configPeakOutputForward(1, 10);
    	release.configPeakOutputReverse(-1, 10);
    	release.setNeutralMode(NeutralMode.Brake);
        RobotWantedStates.climberRelease = false;
    }

    public void climberHandler() {

        ControlMap.getWantedHookRelease();
        
        //release.set(ControlMode.PercentOutput, ControlMap.leftTankStick(false));
        SmartDashboard.putNumber("Hook Encoder Amount", release.getSelectedSensorPosition(0));
        
        System.out.println(RobotWantedStates.hookRelease.name());
        
        
        if(RobotWantedStates.hookRelease == HookState.EXTEND) {
        	release.set(ControlMode.PercentOutput, .5);
        }
        else if(RobotWantedStates.hookRelease == HookState.RETRACT) {
        	System.out.println("SENDING PACKET!!!!!!!!!11");
        	release.set(ControlMode.PercentOutput, -.5);
        }
        else {
        	release.set(ControlMode.PercentOutput, 0);
        }
        
    }

    @Override
    public void pushToDashboard() {

    }

    @Override
    public void zeroSensors() {

    }

    @Override
    public void stop() {

    }

}
