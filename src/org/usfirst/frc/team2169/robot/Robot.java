package org.usfirst.frc.team2169.robot;

import org.usfirst.frc.team2169.robot.RobotStates.RunningMode;
import org.usfirst.frc.team2169.robot.auto.AutoManager;
import org.usfirst.frc.team2169.robot.subsystems.Superstructure;
import org.usfirst.frc.team2169.util.CameraManager;
import org.usfirst.frc.team2169.util.FMSManager;
import org.usfirst.frc.team2169.util.ShuffleBoardManager;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends IterativeRobot {

	static AutoManager auto;
	static Superstructure superStructure;
	public static FMSManager fms;
	static ShuffleBoardManager shuffle;
	static CameraManager camera;
	
	@Override
	public void robotInit() {
		
		RobotStates.runningMode = RunningMode.IDLE;
		camera = new CameraManager();
		fms = new FMSManager(m_ds);
		auto = new AutoManager();
		superStructure = new Superstructure();
		shuffle = new ShuffleBoardManager();
		///camera.startCameraServer(true, true, true);
		SmartDashboard.putBoolean("isRunning", false);
		ControlMap.init();
		superStructure.robotInit();
		
		
	}
	
	public void disabledPeriodic() {
		
		RobotStates.isFMSConnected = m_ds.isFMSAttached();
		shuffle.init(m_ds.isFMSAttached());
		
	}

	@Override
	public void autonomousInit() {

		RobotStates.fieldSetup = auto.determineField(m_ds.getGameSpecificMessage());
		RobotStates.runningMode = RunningMode.AUTO;
		auto.runAuto();
		
	}
	
	@Override
	public void autonomousPeriodic() {
		
		Scheduler.getInstance().run();
		
		RobotStates.isFMSConnected = m_ds.isFMSAttached();
		shuffle.auto(m_ds.isFMSAttached());
		auto.autoLooping();
		
		
	}
	
	
	@Override
	public void teleopPeriodic() {
		
		Scheduler.getInstance().run();
		
		RobotStates.isFMSConnected = m_ds.isFMSAttached();
		RobotStates.runningMode = RunningMode.TELEOP;
		
		try{
			
			shuffle.teleOp(m_ds.isFMSAttached());
			superStructure.teleOpLoop();
			
			//Put Tele-Op Methods here
			
		}
		catch(Exception e){
			DriverStation.reportError(e.toString(), true);
		}

		superStructure.teleOpLoop();
		
		//test.start();
		
		//superStructure.teleOpLoop();
		
	}

	@Override
	public void disabledInit() {
	
		//Stop all subsystems here	
		
	}
	
	
	@Override
	public void testPeriodic() {
	}
	
	
}

