package com.team2169.robot;

import com.team2169.robot.RobotStates.ArmPos;
import com.team2169.robot.RobotStates.DriveType;
import com.team2169.robot.RobotStates.RunningMode;
import com.team2169.robot.auto.AutoManager;
import com.team2169.robot.canCycles.CANCycleHandler;
import com.team2169.robot.subsystems.Superstructure;
import com.team2169.util.CameraManager;
import com.team2169.util.FMSManager;
import com.team2169.util.ShuffleBoardManager;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot{

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
		CANCycleHandler.createCycles();
		superStructure = new Superstructure();
		shuffle = new ShuffleBoardManager();
		camera.startCameraServer(true, false, false);
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
		Scheduler.getInstance().removeAll();
		superStructure.zeroAllSensors();
		RobotStates.runningMode = RunningMode.AUTO;
		// ShuffleBoardManager.getPathfinderConstants();
		auto.runAuto();

	}

	@Override
	public void autonomousPeriodic() {

		Scheduler.getInstance().run();
		superStructure.subsystemLooper();
		RobotStates.isFMSConnected = m_ds.isFMSAttached();
		shuffle.auto(m_ds.isFMSAttached());
		auto.autoLooping();

	}

	@Override
	public void teleopInit() {
		RobotStates.runningMode = RunningMode.TELEOP;
		if (auto != null) {
			auto.endAuto();
		}
		
		Scheduler.getInstance().removeAll();
		RobotStates.armPos =  ArmPos.CONFIG;
		RobotWantedStates.wantedDriveType = DriveType.NORMAL_DRIVING;

	}

	@Override
	public void teleopPeriodic() {

		Scheduler.getInstance().run();

		RobotStates.isFMSConnected = m_ds.isFMSAttached();
		RobotStates.runningMode = RunningMode.TELEOP;

		try {

			StateManager.teleOpStateLooper();
			shuffle.teleOp(m_ds.isFMSAttached());
			

		} catch (Exception e) {
			DriverStation.reportError(e.toString(), true);
		}
		
		superStructure.subsystemLooper();

	}

	@Override
	public void disabledInit() {
		auto.endAuto();
		// Stop all subsystems here

	}
	
	@Override
	public void testInit(){
		superStructure.startCompressor();
	}
	@Override
	public void testPeriodic() {
		
	}

}
