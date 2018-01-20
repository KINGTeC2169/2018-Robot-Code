package org.usfirst.frc.team2169.robot.subsystems;

import org.usfirst.frc.team2169.robot.RobotWantedStates;
import org.usfirst.frc.team2169.robot.RobotWantedStates.WantedArmPos;
import org.usfirst.frc.team2169.robot.RobotWantedStates.WantedElevatorPos;
import org.usfirst.frc.team2169.robot.RobotWantedStates.WantedIntakeMode;
//import org.usfirst.frc.team2169.robot.ActuatorMap;
import org.usfirst.frc.team2169.robot.auto.canCycles.CANCycleHandler;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.SPI;

public class Superstructure {
	
	public static AHRS navX;
	CANCycleHandler canHandler;
	public static DriveTrain drive;
	static Compressor comp;
	public static Intake intake;
	public static ElevatorArm liftArm;
	
	public Superstructure(){
		
		//comp = new Compressor(ActuatorMap.compressorPCMPort);
		drive = new DriveTrain();
		intake = new Intake();
		liftArm = new ElevatorArm();
		navX = new AHRS(SPI.Port.kMXP, (byte)200);
		canHandler = new CANCycleHandler();
		
	}

	public void robotInit() {

		RobotWantedStates.wantedIntakeMode = WantedIntakeMode.IDLE;
		RobotWantedStates.wantedArmPos = WantedArmPos.FULLY_RETRACTED;
		RobotWantedStates.wantedElevatorPos = WantedElevatorPos.GROUND;
		//comp.start();
		
	}
	
	public void teleOpLoop() {

		drive.drive();
		intake.intakeHandler();
		liftArm.elevatorHandler();
		//CANCycleHandler.startCycle(CANCycleHandler.sampleCANCycle);
		
	}
	
	//This is how you start a CANCycle
	//CANCycleHandler.sampleCANCycle.start();
	
	//This is how you cancel a CANCycle
	//CANCycleHandler.sampleCANCycle.cancel();
	
}
