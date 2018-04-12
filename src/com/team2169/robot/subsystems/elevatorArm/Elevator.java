package com.team2169.robot.subsystems.elevatorArm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.team2169.robot.*;
import com.team2169.robot.RobotStates.Macro;
import com.team2169.robot.RobotStates.RunningMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Elevator {

    // Create Talons
    private TalonSRX elevator;
    private DigitalInput topLimit;
    private DigitalInput bottomLimit;
    private int heightPos;

    public Elevator() {

        // Define Lift Talons
        elevator = new TalonSRX(ActuatorMap.elevatorMasterID);
        TalonSRX elevatorSlave = new TalonSRX(ActuatorMap.elevatorSlaveID);
        elevatorSlave.set(ControlMode.Follower, ActuatorMap.elevatorMasterID);
        elevator.configPeakCurrentLimit(30, Constants.elevatorData.timeoutMs);
        elevatorSlave.configPeakCurrentLimit(30, Constants.elevatorData.timeoutMs);
        elevator.configAllowableClosedloopError(Constants.elevatorData.slotIDx, Constants.elevatorData.allowedError, Constants.elevatorData.timeoutMs);
        elevatorSlave.configAllowableClosedloopError(Constants.elevatorData.slotIDx, Constants.elevatorData.allowedError, Constants.elevatorData.timeoutMs);
        elevator.configPeakCurrentLimit(35, Constants.elevatorData.timeoutMs);
        elevatorSlave.configPeakCurrentLimit(35, Constants.elevatorData.timeoutMs);
        elevator.configPeakCurrentDuration(500, Constants.elevatorData.timeoutMs);
        elevatorSlave.configPeakCurrentDuration(500, Constants.elevatorData.timeoutMs);

        elevator.configPeakOutputForward(.5, 10);
        elevator.configPeakOutputReverse(-.5, 10);
        elevator.configClosedloopRamp(.75, 10);
        elevatorSlave.configClosedloopRamp(.75, 10);

        topLimit = new DigitalInput(ActuatorMap.elevatorTopLimitID);
        bottomLimit = new DigitalInput(ActuatorMap.elevatorBottomLimitID);

        // Pull Constants Data for Elevator
        //Constants.setElevatorDataFromConstants();

        // Apply Talon Settings
        //elevator = TalonMaker.prepTalonForMotionProfiling(elevator, Constants.elevatorData);

    }

    public void elevatorMacroLooper() {


        SmartDashboard.putBoolean("topLimit", topLimit.get());
        SmartDashboard.putBoolean("bottomLimit", bottomLimit.get());
        getLimits();

        SmartDashboard.putString("Elevator State", RobotWantedStates.wantedElevatorPos.name());
        
        switch (RobotWantedStates.wantedElevatorPos) {

            case OVERRIDE:
            default:
                elevatorManual(ControlMap.getOperatorStickValue());

                // Set RobotStates
                RobotStates.elevatorPos = Macro.OVERRIDE;
                break;

            case HOLD_POSITION:

                holdInPosition();

                // Set RobotStates
                RobotStates.elevatorPos = Macro.HOLD_POSITION;
                break;

            case GROUND:

                // CANCycle for Ground Position
                //Coming Down
                if (heightPos > 0) {
                    setPID(0);
                }
                //Going Up
                else if (heightPos < 0) {
                    setPID(1);
                }

                heightPos = 0;

                // Actuate the Motor
                elevatorToPos(Constants.groundElevatorEncoderPosition);

                // Set RobotStates
                RobotStates.elevatorPos = Macro.GROUND;
                break;

            case HANG:

                // CANCycle Hang Position

                //Coming Down
                if (heightPos > 5) {
                    setPID(0);
                }
                //Going Up
                else if (heightPos < 5) {
                    setPID(1);
                }

                heightPos = 5;

                // Actuate the Motor
                elevatorToPos(Constants.hangElevatorEncoderPosition);

                // Set RobotStates
                RobotStates.elevatorPos = Macro.HANG;
                break;

            case SCALE_HIGH:

                // CANCycle for (High) Position
                //Coming Down
                if (heightPos > 4) {
                    setPID(0);
                }
                //Going Up
                else if (heightPos < 4) {
                    setPID(1);
                }

                heightPos = 4;
                // Actuate the Motor
                elevatorToPos(Constants.scaleHighElevatorEncoderPosition);

                // Set Robot States
                RobotStates.elevatorPos = Macro.SCALE_HIGH;
                break;

            case SCALE_MID:

                // CANCycle for Scale (Mid) Position
                //Coming Down
                if (heightPos > 3) {
                    setPID(0);
                }
                //Going Up
                else if (heightPos < 3) {
                    setPID(1);
                }

                heightPos = 3;

                // Actuate the Motor
                elevatorToPos(Constants.scaleMidElevatorEncoderPosition);

                // Set Robot States
                RobotStates.elevatorPos = Macro.SCALE_MID;
                break;

            case SCALE_LOW:

                // CANCycle for Scale (Low) Position

                //Coming Down
                if (heightPos > 2) {
                    setPID(0);
                }
                //Going Up
                else if (heightPos < 2) {
                    setPID(1);
                }

                heightPos = 2;

                // Actuate the Motor
                elevatorToPos(Constants.scaleLowElevatorEncoderPosition);

                // Set Robot States
                RobotStates.elevatorPos = Macro.SCALE_LOW;
                break;

            case SWITCH:

                // CANCycle for Switch

                //Coming Down
                if (heightPos > 1) {
                    setPID(0);
                }
                //Going Up
                else if (heightPos < 1) {
                    setPID(1);

                }

                heightPos = 1;

                // Actuate the Motor
                elevatorToPos(Constants.switchElevatorEncoderPosition);

                // Set Robot States
                RobotStates.elevatorPos = Macro.SWITCH;
                break;
            
            case PASS:
            	break;
                
             

        }
        
        if(ControlMap.resetElevator()) {
        	resetElevatorPosition();
        }
        
        SmartDashboard.putNumber("output to motor", elevator.getMotorOutputPercent());
        SmartDashboard.putNumber("Elevator Error", elevator.getClosedLoopError(0));
        SmartDashboard.putNumber("Height Pos", heightPos);
        SmartDashboard.putNumber("Elevator Setpoint",
                elevator.getSelectedSensorPosition(Constants.elevatorData.slotIDx));

    }

    private void elevatorManual(double power) {
    	elevator.set(ControlMode.PercentOutput, -power);

    }

    private void resetElevatorPosition() {
    	elevator.setSelectedSensorPosition(0, 0, Constants.elevatorData.timeoutMs);
    }
    
    private void elevatorToPos(int pos) {
        elevator.set(ControlMode.Position, pos);
        int position = elevator.getSelectedSensorPosition(Constants.elevatorData.slotIDx);
        SmartDashboard.putNumber("Elevator Setpoint", position);
        getElevatorFinishedState();
    }

    private void holdInPosition() {
        //elevator.set(ControlMode.Position, position);
        elevator.set(ControlMode.PercentOutput, 0);

    }

    private void getLimits() {

        // Upper Limit Switch Active
        if (!bottomLimit.get()) {
            resetElevatorPosition();
        } 
        else if(RobotStates.runningMode == RunningMode.AUTO) {
        	elevator.configPeakOutputReverse(-.5, Constants.elevatorData.timeoutMs);
        }
        else {
            elevator.configPeakOutputReverse(-1, Constants.elevatorData.timeoutMs);
        }

        // Lower Limit Switch Active
        if (!topLimit.get()) {
        } 
        else if(RobotStates.runningMode == RunningMode.AUTO) {
        	elevator.configPeakOutputReverse(.5, Constants.elevatorData.timeoutMs);
        }
        else {
            elevator.configPeakOutputForward(1, Constants.elevatorData.timeoutMs);
        }
    }


    private void setPID(int direction) {

		/*
		//Going Down
		if(direction == 0) {
			elevator.config_kP(0, table.getNumber("p0", 0), 10);
			elevator.config_kI(0, table.getNumber("i0", 0), 10);
			elevator.config_kD(0, table.getNumber("d0", 0), 10);
			elevator.config_kF(0, table.getNumber("f0", 0), 10);
			System.out.println(table.getNumber("p0", -1));
			System.out.println(table.getNumber("i0", -1));
			System.out.println(table.getNumber("d0", -1));
			System.out.println(table.getNumber("f0", -1));
			SmartDashboard.putString("Direction", "Going Down");
		}
		
		//Going Up
		else if(direction == 1) {
			elevator.config_kP(0, table.getNumber("p1", 0), 10);
			elevator.config_kI(0, table.getNumber("i1", 0), 10);
			elevator.config_kD(0, table.getNumber("d1", 0), 10);
			elevator.config_kF(0, table.getNumber("f1", 0), 10);
			System.out.println(table.getNumber("p1", -1));
			System.out.println(table.getNumber("i1", -1));
			System.out.println(table.getNumber("d1", -1));
			System.out.println(table.getNumber("f1", -1));
			SmartDashboard.putString("Direction", "Going Up");
		}
		*/

        //Going Down
        if (direction == 0) {
            elevator.config_kP(0, .15, 10);
            elevator.config_kI(0, 0, 10);
            elevator.config_kD(0, .25, 10);
            elevator.config_kF(0, .005, 10);
            SmartDashboard.putString("Direction", "Going Down");
        }

        //Going Up
        else if (direction == 1) {
            elevator.config_kP(0, .35, 10);
            elevator.config_kI(0, 0, 10);
            elevator.config_kD(0, .15, 10);
            elevator.config_kF(0, .015, 10);
            SmartDashboard.putString("Direction", "Going Up");
        }


        elevator.valueUpdated();
    }

    private void getElevatorFinishedState() {

        if (elevator.getClosedLoopError(Constants.elevatorData.pidLoopIDx) < Constants.elevatorData.allowedError
                || elevator
                .getClosedLoopError(Constants.elevatorData.pidLoopIDx) > -Constants.elevatorData.allowedError) {
            RobotStates.elevatorInPosition = true;
        }

        RobotStates.elevatorInPosition = false;

    }

    public void zeroSensors() {
        elevator.setSelectedSensorPosition(0, Constants.elevatorData.slotIDx, Constants.elevatorData.timeoutMs);
    }

    public void stop() {
        elevator.set(ControlMode.PercentOutput, 0);
    }

}
