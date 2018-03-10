package com.team2169.robot.subsystems;

import com.team2169.robot.ActuatorMap;
import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.ArmPos;
import com.team2169.robot.RobotStates.IntakeMode;
import com.team2169.robot.RobotStates.Macro;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.StateManager;
import com.team2169.util.DebugPrinter;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
public class Superstructure {

	private static Superstructure sInstance = null;

	public static Superstructure getInstance() {
		if (sInstance == null) {
			sInstance = new Superstructure();
		}
		return sInstance;
	}

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
		comp = new Compressor(ActuatorMap.PCMPort);

	}

	public void reinstance() {
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
		RobotWantedStates.wantedIntakeMode = IntakeMode.IDLE;
		RobotWantedStates.wantedArmPos = ArmPos.OVERRIDE;
		RobotWantedStates.wantedElevatorPos = Macro.GROUND;
		StateManager.stateInit();

		if (RobotStates.debugMode) {
			DriverStation.reportWarning("Superstructure Init Finished", false);
		}

		this.startCompressor();

	}
	public void startCompressor(){
		comp.start();
	}
	
	public void updateGyro() {
		RobotStates.gyroAngle = drive.getAngle();
		SmartDashboard.putNumber("Gyro", RobotStates.gyroAngle);
	}



	public void subsystemLooper() {
		drive.pushToDashboard();
		drive.driveHandler();
		// platform.platformHandler();
		intake.intakeHandler();
		liftArm.elevatorArmHandler();
		DebugPrinter.elevatorDebug();
		drive.pushToDashboard();

	}
	
	public void zeroAllSensors() {

		drive.zeroSensors();
		intake.zeroSensors();
		platform.zeroSensors();
		liftArm.zeroSensors();

	}

}
