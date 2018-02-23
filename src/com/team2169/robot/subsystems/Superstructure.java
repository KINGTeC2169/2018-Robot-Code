package com.team2169.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.team2169.robot.ActuatorMap;
import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.RobotWantedStates.WantedArmPos;
import com.team2169.robot.RobotWantedStates.WantedIntakeMode;
import com.team2169.robot.RobotWantedStates.WantedMacro;
import com.team2169.robot.StateManager;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;

public class Superstructure {

	private static Superstructure sInstance = null;

	public static Superstructure getInstance() {
		if (sInstance == null) {
			sInstance = new Superstructure();
		}
		return sInstance;
	}

	public AHRS navX;
	public static DriveTrain drive;
	private Intake intake;
	private Platform platform;
	private ElevatorArm liftArm;
	private Compressor comp;

	public Superstructure() {

		drive = DriveTrain.getInstance();
		intake = Intake.getInstance();
		platform = Platform.getInstance();
		liftArm = ElevatorArm.getInstance();
		navX = new AHRS(SPI.Port.kMXP, (byte) 200);
		comp = new Compressor(ActuatorMap.PCMPort);

	}
	public void reinstance(){
		drive = DriveTrain.getInstance();
		intake = Intake.getInstance();
		platform = Platform.getInstance();
		liftArm = ElevatorArm.getInstance();
	}

	public void robotInit() {

		if (RobotStates.debugMode) {
			DriverStation.reportWarning("Starting Superstructure Init", false);
		}
		// Zero Sensors
		zeroAllSensors();

		// Set WantedStates
		RobotWantedStates.wantedIntakeMode = WantedIntakeMode.IDLE;
		RobotWantedStates.wantedArmPos = WantedArmPos.OVERRIDE;
		RobotWantedStates.wantedElevatorPos = WantedMacro.GROUND;
		StateManager.stateInit();

		if (RobotStates.debugMode) {
			DriverStation.reportWarning("Superstructure Init Finished", false);
		}
		
		comp.start();

	}

	public void subsystemLooper() {
		
		drive.pushToDashboard();
		drive.driveHandler();
		platform.platformHandler();
		intake.intakeHandler();
		liftArm.elevatorArmHandler();
		drive.pushToDashboard();
		// CANCycleHandler.startCycle(CANCycleHandler.sampleCANCycle);

	}

	// This is how you start a CANCycle
	// CANCycleHandler.sampleCANCycle.start();

	// This is how you cancel a CANCycle
	// CANCycleHandler.sampleCANCycle.cancel();

	void zeroAllSensors() {

		drive.zeroSensors();
		intake.zeroSensors();
		platform.zeroSensors();
		liftArm.zeroSensors();

	}

}
