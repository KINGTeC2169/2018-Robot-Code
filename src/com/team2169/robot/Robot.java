package com.team2169.robot;

import com.team2169.robot.RobotStates.ArmPos;
import com.team2169.robot.RobotStates.DriveType;
import com.team2169.robot.RobotStates.Macro;
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

public class Robot extends TimedRobot {

	private static AutoManager auto;
	private static Superstructure superStructure;
	public static FMSManager fms;
	private static ShuffleBoardManager shuffle;
	boolean lastConnected = true;

	@Override
	public void robotInit() {

		RobotStates.runningMode = RunningMode.IDLE;
		CameraManager camera = new CameraManager();
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
		shuffle.init();

	}

	@Override
	public void autonomousInit() {
		Scheduler.getInstance().removeAll();
		superStructure.zeroAllSensors();
		RobotStates.runningMode = RunningMode.AUTO;
		auto.runAuto();

	}

	@Override
	public void autonomousPeriodic() {

		Scheduler.getInstance().run();
		try {
			
			RobotStates.isFMSConnected = m_ds.isFMSAttached();
			shuffle.auto();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Auto Error");
		}
		superStructure.subsystemLooper();
		// auto.autoLooping();

	}

	@Override
	public void teleopInit() {
		RobotStates.runningMode = RunningMode.TELEOP;
		if (auto != null) {
			auto.endAuto();
		}
		System.out.println("Starting Tele");
		Scheduler.getInstance().removeAll();
		RobotStates.armPos = ArmPos.IDLE;
		RobotWantedStates.wantedDriveType = DriveType.NORMAL_DRIVING;
		RobotWantedStates.wantedElevatorPos = Macro.PASS;
	}

	@Override
	public void teleopPeriodic() {
		
		SmartDashboard.putBoolean("New Control Data", DriverStation.getInstance().isNewControlData());
		
		Scheduler.getInstance().run();

		RobotStates.isFMSConnected = m_ds.isFMSAttached();
		RobotStates.runningMode = RunningMode.TELEOP;

		try {

			StateManager.teleOpStateLooper();
			shuffle.teleOp();

		} catch (Exception e) {
			DriverStation.reportError(e.toString(), true);
		}
		
		System.out.println("Tele Loop");
		superStructure.subsystemLooper();

	}

	@Override
	public void disabledInit() {

		RobotStates.runningMode = RunningMode.IDLE;
		auto.endAuto();
		superStructure.stop();
		// Stop all subsystems here

	}

	@Override
	public void testInit() {
		superStructure.startCompressor();
	}

	@Override
	public void testPeriodic() {

	}

}
