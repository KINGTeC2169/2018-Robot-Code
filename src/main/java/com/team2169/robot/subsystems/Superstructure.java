package com.team2169.robot.subsystems;

import com.team2169.robot.ActuatorMap;
import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.ArmPos;
import com.team2169.robot.RobotStates.IntakeMode;
import com.team2169.robot.RobotStates.Macro;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.StateManager;
import com.team2169.util.DataSelector;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;

public class Superstructure {

    private static Superstructure sInstance = null;

    public static Superstructure getInstance() {
        if (sInstance == null) {
            sInstance = new Superstructure();
        }
        return sInstance;
    }

    public DriveTrain drive;
    private Intake intake;
    private ElevatorArm liftArm;
    private Compressor comp;
    private Climber climber;

    public Superstructure() {

    	comp = new Compressor(ActuatorMap.PCMPort);
        drive = DriveTrain.getInstance();
        intake = Intake.getInstance();
        liftArm = ElevatorArm.getInstance();
        climber = new Climber();

    }

    public void robotInit() {

    	DataSelector.pushValues();
    	
        if (RobotStates.debugMode) {
            DriverStation.reportWarning("Starting Superstructure Init", false);
        }
        // Zero Sensors
        zeroAllSensors();

        // Set WantedStates
        RobotWantedStates.wantedIntakeMode = IntakeMode.IDLE;
        RobotWantedStates.wantedArmPos = ArmPos.RETRACT;
        RobotWantedStates.wantedElevatorPos = Macro.PASS;
        StateManager.stateInit();

        if (RobotStates.debugMode) {
            DriverStation.reportWarning("Superstructure Init Finished", false);
        }

        this.startCompressor();

    }

    public void startCompressor() {
        comp.start();
    }

	public void subsystemLooper() {
    	
    	DataSelector.looper();
    	
        drive.driveHandler();
        intake.intakeHandler();
        liftArm.elevatorArmHandler();
        climber.climberHandler();
        
        smartDashboardLooper();

        RobotStates.loopCount++;
        
    }

    private void smartDashboardLooper() {
    	drive.pushToDashboard();
    	liftArm.pushToDashboard();
    }
    
    public void zeroAllSensors() {
        drive.zeroSensors();
        intake.zeroSensors();
        liftArm.zeroSensors();

    }

    public void stop() {
        drive.stop();
        liftArm.stop();
        intake.stop();
    }

}
