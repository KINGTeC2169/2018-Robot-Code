package org.usfirst.frc.team2169.robot;

import org.usfirst.frc.team2169.robot.RobotStates.runningMode;
import org.usfirst.frc.team2169.robot.auto.AutoManager;
import org.usfirst.frc.team2169.robot.subsystems.Superstructure;
import org.usfirst.frc.team2169.util.FMSManager;
import org.usfirst.frc.team2169.util.ShuffleBoardManager;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;


public class Robot extends IterativeRobot {

	static AutoManager auto;
	ControlMap controls;
	Superstructure superStructure;
	public static FMSManager fms;
	ShuffleBoardManager shuffle;
	
	@Override
	public void robotInit() {

		fms = new FMSManager(m_ds);
		RobotStates.runningMode = runningMode.IDLE;
		auto = new AutoManager();
		superStructure = new Superstructure();
		controls = new ControlMap();
		shuffle = new ShuffleBoardManager();

	
	}
	
	public void disabledPeriodic() {
		
		RobotStates.isFMSConnected = m_ds.isFMSAttached();
		shuffle.init(m_ds.isFMSAttached());
		
	}

	@Override
	public void autonomousInit() {

		RobotStates.runningMode = runningMode.AUTO;
		auto.runAuto(m_ds.getGameSpecificMessage());
		
	}
	
	@Override
	public void autonomousPeriodic() {
		
		RobotStates.isFMSConnected = m_ds.isFMSAttached();
		Scheduler.getInstance().run();
		shuffle.auto(m_ds.isFMSAttached());
		auto.autoLooping();
		
	}

	
	@Override
	public void teleopInit() {
		// TODO Auto-generated method stub
		super.teleopInit();
	}
	
	
	@Override
	public void teleopPeriodic() {
		
		RobotStates.isFMSConnected = m_ds.isFMSAttached();
		RobotStates.runningMode = runningMode.TELEOP;
		
		try{
			
			shuffle.teleOp(m_ds.isFMSAttached());
			//Put Tele-Op Methods here
		
		}
		catch(Exception e){
			DriverStation.reportError(e.toString(), true);
		}
		
	}

	@Override
	public void disabledInit() {
	
		//Stop all subsystems here	
		
	}
	
	
	@Override
	public void testPeriodic() {
	}
	
	
}

