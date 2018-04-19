package com.team2169.robot.auto.tasks.drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.team2169.robot.Constants;
import com.team2169.robot.RobotStates.DriveType;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.auto.AutoConstants.RobotSide;
import com.team2169.robot.auto.tasks.Task;
import com.team2169.robot.subsystems.DriveTrain;
import com.team2169.util.PIDF;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnInPlace extends Task{

  private double angle = 0.0;
  private int i = 0;
  private PIDF pid;
  private DriveTrain drive;
  private double error;
  
  public TurnInPlace(double angle, double speed, RobotSide side) {

    this.angle = angle;
	pid = new PIDF(Constants.driveTurnP, Constants.driveTurnI, Constants.driveTurnD, Constants.driveTurnF);
	pid.setSetpoint(angle);
    drive = DriveTrain.getInstance();
    
  }
  
  public TurnInPlace(double angle, double speed, boolean inverted) {

	    this.angle = angle;
		pid = new PIDF(Constants.driveTurnP, Constants.driveTurnI, Constants.driveTurnD, Constants.driveTurnF);
		pid.setSetpoint(angle);
	    drive = DriveTrain.getInstance();
	    
  }
  
  public TurnInPlace(double angle, double speed) {

	    this.angle = angle;
		pid = new PIDF(Constants.driveTurnP, Constants.driveTurnI, Constants.driveTurnD, Constants.driveTurnF);
		pid.setSetpoint(angle);		
	    drive = DriveTrain.getInstance();
	    
}

  protected void initialize() {
	  
    drive.resetGyro();
    RobotWantedStates.wantedDriveType = DriveType.EXTERNAL_DRIVING;
    
  }

  protected boolean isFinished() {
	  if(Math.abs(error) < 3 || this.isTimedOut()) {
		  i++;
	  }
	  else {
		  i = 0;
	  }

	  return i > 2;
    
  }

  protected void execute() {

    
    error = drive.getAngle() - this.angle;
    System.out.println("Output: " + pid.getOutput(drive.getAngle()));
    System.out.println("Error: " + error);
    drive.left.set(ControlMode.PercentOutput, pid.getOutput(drive.getAngle()));
    drive.right.set(ControlMode.PercentOutput, -pid.getOutput(drive.getAngle()));
    SmartDashboard.putNumber("Turn Error", error);
    
    
  }

  protected void end() {

	  drive.left.set(ControlMode.PercentOutput, 0);
	  drive.right.set(ControlMode.PercentOutput, 0);

  }

  protected void interrupted() {

    end();
  }
}