package com.team2169.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team2169.robot.*;
import com.team2169.robot.RobotStates.IntakeClamp;
import com.team2169.robot.RobotStates.IntakeMode;
import com.team2169.robot.auto.AutoConstants;
import com.team2169.util.DebugPrinter;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

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

    private Intake() {

        left = new TalonSRX(ActuatorMap.leftIntakeID);
        right = new TalonSRX(ActuatorMap.rightIntakeID);
        right.setInverted(true);
        clampSolenoid = new DoubleSolenoid(ActuatorMap.PCMPort, ActuatorMap.clampPortForward,
                ActuatorMap.clampPortReverse);
        RobotWantedStates.wantedIntakeClamp = IntakeClamp.CLAMP;


    }

    void intakeHandler() {

        // Handle Intake State
        switch (RobotWantedStates.wantedIntakeMode) {
        
            case IDLE:
            default:

                // Stop Intakes
                intakeManual(0);
                RobotStates.intakeMode = IntakeMode.IDLE;

                break;

            case MANUAL:
            	
            	//If the intake is being actively controlled, follow driver's instructions
            	if(Math.abs(ControlMap.intakeAmount()) >= .2){
            		intakeManual(ControlMap.intakeAmount());
            	}
            	
            	//Otherwise, provide 2v of negative power to hold in block
            	else {
            		intakeManual(-(20/12));
            	}
            	
            	break;
            	
            case INTAKE:

                // Run Intakes

                intakeManual(AutoConstants.desireIntakeSpeed);
                RobotStates.intakeMode = IntakeMode.INTAKE;
                break;

            case EXHAUST:

                // Run Intakes Backwards
            	intakeManual(-AutoConstants.desireIntakeSpeed);
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
                break;

        }
        pushToDashboard();

    }

    @Override
    public void pushToDashboard() {

        if (RobotStates.debugMode) {
            DebugPrinter.intakeDebug();
        }

    }

    private void intakeManual(double power) {
    	
        left.set(ControlMode.PercentOutput, power);
        right.set(ControlMode.PercentOutput, power);
    }
    
    @Override
    public void zeroSensors() {
        // TODO Auto-generated method stub

    }

    @Override
    public void stop() {
        intakeManual(0);

    }

}
