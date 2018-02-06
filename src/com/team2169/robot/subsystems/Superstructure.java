package com.team2169.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.RobotWantedStates.WantedArmPos;
import com.team2169.robot.RobotWantedStates.WantedElevatorPos;
import com.team2169.robot.RobotWantedStates.WantedIntakeMode;
import com.team2169.robot.canCycles.CANCycleHandler;

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
	CANCycleHandler canHandler;
	DriveTrain drive;
	Compressor comp;
	Intake intake;
	Platform platform;
	ElevatorArm liftArm;
	
	public Superstructure(){
		
		//comp = new Compressor(ActuatorMap.compressorPCMPort);
		drive = new DriveTrain();
		intake = new Intake();
		platform = new Platform();
		liftArm = new ElevatorArm();
		navX = new AHRS(SPI.Port.kMXP, (byte)200);
		canHandler = new CANCycleHandler();
		
	}

	public void robotInit() {

		if(RobotStates.debugMode) {
			DriverStation.reportWarning("Starting Superstructure Init", false);
		}
		RobotWantedStates.wantedIntakeMode = WantedIntakeMode.IDLE;
		RobotWantedStates.wantedArmPos = WantedArmPos.FULLY_RETRACTED;
		RobotWantedStates.wantedElevatorPos = WantedElevatorPos.GROUND;
		if(RobotStates.debugMode) {
			DriverStation.reportWarning("Superstructure Init Finished", false);
		}
		//comp.start();
		
	}
	
	public void subsystemLooper() {

		drive.driveHandler();
		platform.platformHandler();
		intake.intakeHandler();
		liftArm.elevatorHandler();
		
		//CANCycleHandler.startCycle(CANCycleHandler.sampleCANCycle);
		
	}
	
	//This is how you start a CANCycle
	//CANCycleHandler.sampleCANCycle.start();
	
	//This is how you cancel a CANCycle
	//CANCycleHandler.sampleCANCycle.cancel();
	
}
