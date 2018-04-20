package com.team2169.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team2169.robot.*;
import com.team2169.robot.RobotStates.IntakeClamp;
import com.team2169.robot.RobotStates.IntakeMode;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake extends Subsystem {

    private static Intake iInstance = null;

    public static Intake getInstance() {
        if (iInstance == null) {
            iInstance = new Intake();
        }
        return iInstance;
    }

    private TalonSRX left;
    private TalonSRX right;
    private DoubleSolenoid clampSolenoid;
    private boolean lastIntook = false;

    private Intake() {

        left = new TalonSRX(ActuatorMap.leftIntakeID);
        right = new TalonSRX(ActuatorMap.rightIntakeID);
        right.setInverted(true);
        clampSolenoid = new DoubleSolenoid(ActuatorMap.PCMPort, ActuatorMap.clampPortForward,
                ActuatorMap.clampPortReverse);
        RobotWantedStates.wantedIntakeClamp = IntakeClamp.CLAMP;


    }

    void intakeHandler() {

        // Handle Intake Wheels' State
        switch (RobotWantedStates.wantedIntakeMode) {
        
            case IDLE:
            default:

                // Stop Intakes
                intakeManual(0);
                RobotStates.intakeMode = IntakeMode.IDLE;
                if(lastIntook) {
            		intakeManual(Constants.intakeHoldVoltage/12.0);
            	}
                break;

            case MANUAL:
            	
            	//If the intake is being actively controlled, follow driver's instructions
            	if(Math.abs(ControlMap.intakeAmount()) >= ControlMap.operatorDeadband){
            		intakeManual(ControlMap.intakeAmount());
            	}
            	else if(lastIntook) {
            		intakeManual(Constants.intakeHoldVoltage/12.0);
            	}
            	else {
            		intakeManual(0);
            	}
            	
            	if(ControlMap.intakeAmount() >= ControlMap.operatorDeadband) {
            		lastIntook = true;
            	}
            	else if(ControlMap.intakeAmount() <= -ControlMap.operatorDeadband) {
            		lastIntook = false;
            	}
            	//Otherwise, provide 2v of negative power to hold in block

            	
            	break;
            	
            case INTAKE:
            	lastIntook = true;
                // Run Intakes
                intakeManual(Constants.intakeSpeed);
                RobotStates.intakeMode = IntakeMode.INTAKE;
                break;

            case EXHAUST:
            	lastIntook = false;
                // Run Intakes Backwards
            	intakeManual(-Constants.intakeSpeed);
                RobotStates.intakeMode = IntakeMode.EXHAUST;
                break;

        }


        // Handle Wanted Clamp State
        switch (RobotWantedStates.wantedIntakeClamp) {

            case CLAMP:

                // Set Clamp to Clamped
                clampSolenoid.set(Value.kReverse);
                RobotStates.intakeClamp = IntakeClamp.CLAMP;
                break;

            case OPEN:

                clampSolenoid.set(Value.kForward);
                RobotStates.intakeClamp = IntakeClamp.OPEN;
                lastIntook = false;
                break;

        }

    }

    @Override
    public void pushToDashboard() {

    	SmartDashboard.putNumber("Intake Left Current", left.getOutputCurrent());
    	SmartDashboard.putNumber("Intake Right Current", right.getOutputCurrent());

    }

    private void intakeManual(double power) {
    	
        left.set(ControlMode.PercentOutput, power);
        right.set(ControlMode.PercentOutput, power);
    }
    

	@Override
	public void zeroSensors() {}
    
    @Override
    public void stop() {
        intakeManual(0);

    }

}
