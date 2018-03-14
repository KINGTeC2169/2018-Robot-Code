package com.team2169.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team2169.robot.*;
import com.team2169.robot.RobotStates.IntakeClamp;
import com.team2169.robot.RobotStates.IntakeMode;
import com.team2169.util.DebugPrinter;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Ultrasonic;

import java.util.ArrayList;
import java.util.List;

public class Intake extends Subsystem {

    private static Intake iInstance = null;

    public static Intake getInstance() {
        if (iInstance == null) {
            iInstance = new Intake();
        }
        return iInstance;
    }

    private ArrayList<Double> blockHeldHistory;
    private Ultrasonic ultra;
    private TalonSRX left;
    private TalonSRX right;
    private DoubleSolenoid dropSolenoid;
    private DoubleSolenoid clampSolenoid;
    private int i = 0;
    private int a = 0;
    private boolean exhaustFromDrop = false;

    private Intake() {
        ultra = new Ultrasonic(ActuatorMap.intakeUltrasonicOutputPort, ActuatorMap.intakeUltrasonicInputPort);
        left = new TalonSRX(ActuatorMap.leftIntakeID);
        right = new TalonSRX(ActuatorMap.rightIntakeID);
        right.setInverted(true);
        dropSolenoid = new DoubleSolenoid(ActuatorMap.PCMPort, ActuatorMap.dropPortForward,
                ActuatorMap.dropPortReverse);
        clampSolenoid = new DoubleSolenoid(ActuatorMap.PCMPort, ActuatorMap.clampPortForward,
                ActuatorMap.clampPortReverse);
        RobotWantedStates.wantedIntakeClamp = IntakeClamp.CLAMP;

        ultra.setAutomaticMode(true);
        blockHeldHistory = new ArrayList<>();

    }

    private void intakeManual(double power, double twist) {
        if (Math.abs(twist) > .25) {
            left.set(ControlMode.PercentOutput, (twist * Math.abs(power)) * (4 / 3));
            right.set(ControlMode.PercentOutput, (-twist * Math.abs(power)) * (4 / 3));
        }
        left.set(ControlMode.PercentOutput, power);
        right.set(ControlMode.PercentOutput, power);
    }

    private double getBlockDistance() {
        return ultra.getRangeInches();
    }

    private void storeBlockHistory(boolean currentBlockPosition) {
        blockHeldHistory.remove(0);
        if (currentBlockPosition) blockHeldHistory.add(1.0);
        else blockHeldHistory.add(0.0);
    }

    private boolean getBlockHistoryAverage() {
        double add = 0;
        int pos = 0;
        for (double i : blockHeldHistory) {
            add = add + (i * pos + 1);
            pos++;
        }
        return add >= Constants.WeightedAverageRequirement;
    }

    private boolean getAverage(List<Double> list) {
        double add = 0;
        for (double i : list) {
            add = add + i;
        }
        return add >= ((list.size() + 1) / 2);
    }


    @SuppressWarnings("unused")
    private void ultrasonicHandler() {
        RobotStates.blockHeld = (RobotStates.intakeClamp == IntakeClamp.CLAMP) && RobotStates.ultraAverage;
        RobotStates.ultraWithinRange = (getBlockDistance() <= Constants.maxUltraTriggerDistance
                && getBlockDistance() >= Constants.minUltraTriggerDistance);
        storeBlockHistory(RobotStates.ultraWithinRange);
        RobotStates.ultraAverage = getBlockHistoryAverage();

        if ((!getAverage(blockHeldHistory.subList(0, 9))) && ((getAverage(blockHeldHistory.subList(10, 19))))) {
            RobotStates.blockRecent = RobotStates.blockRecentState.JUST_ENTERED;
        } else if ((getAverage(blockHeldHistory.subList(0, 9))) && ((!getAverage(blockHeldHistory.subList(10, 19))))) {
            RobotStates.blockRecent = RobotStates.blockRecentState.JUST_LEFT;
        } else {
            RobotStates.blockRecent = RobotStates.blockRecentState.NO_CHANGE;
        }
    }

    void intakeHandler() {
        //ultrasonicHandler();

        if (RobotStates.operatorWantsUltrasonic && RobotStates.ultraAverage) {
            RobotWantedStates.wantedIntakeClamp = IntakeClamp.CLAMP;
        }

        if (exhaustFromDrop) {
            RobotWantedStates.wantedIntakeMode = IntakeMode.EXHAUST;
        }

        // Handle Intake State
        switch (RobotWantedStates.wantedIntakeMode) {

            case IDLE:
            default:

                // Stop Intakes
                intakeManual(0, 0);
                RobotStates.intakeMode = IntakeMode.IDLE;

                break;

            case INTAKE:

                // Run Intakes

                intakeManual(Constants.intakeSpeed, ControlMap.getOperatorTwistValue());
                RobotStates.intakeMode = IntakeMode.INTAKE;
                break;

            case EXHAUST:

                // Run Intakes Backwards
                intakeManual(-Constants.intakeSpeed, ControlMap.getOperatorTwistValue());
                RobotStates.intakeMode = IntakeMode.EXHAUST;
                break;

        }


        // Handle Wanted Clamp State
        switch (RobotWantedStates.wantedIntakeClamp) {


            case NEUTRAL:
            default:

                a = 0;
                if (i < 5) {
                    i++;
                    clampSolenoid.set(Value.kForward);
                    dropSolenoid.set(Value.kReverse);
                    break;
                } else {
                    clampSolenoid.set(Value.kReverse);
                    dropSolenoid.set(Value.kReverse);
                    RobotStates.intakeClamp = IntakeClamp.NEUTRAL;
                    break;
                }

                // Set Clamp to Neutral


            case CLAMP:

                // Set Clamp to Clamped
                clampSolenoid.set(Value.kForward);
                dropSolenoid.set(Value.kReverse);
                RobotStates.intakeClamp = IntakeClamp.CLAMP;
                a = 0;
                i = 0;
                break;

            case DROP:

                i = 0;
                // Set Clamp to Drop
                if (a < 6) {
                    exhaustFromDrop = true;
                    a++;
                } else {
                    exhaustFromDrop = false;
                }
                clampSolenoid.set(Value.kReverse);
                dropSolenoid.set(Value.kForward);
                RobotStates.intakeClamp = IntakeClamp.DROP;
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

    @Override
    public void zeroSensors() {
        // TODO Auto-generated method stub

    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub

    }

}
