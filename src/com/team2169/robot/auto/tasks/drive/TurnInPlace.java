package com.team2169.robot.auto.tasks.drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.team2169.robot.RobotStates.DriveType;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.auto.AutoConstants.RobotSide;
import com.team2169.robot.auto.tasks.Task;
import com.team2169.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TurnInPlace extends Task{

  private double speed = 0.0;
  private double angle = 0.0;
  private DriveTrain drive;
  private double error;
  private int i = 0;
  double p;
  
  public TurnInPlace(double angle, double speed, RobotSide side) {

    this.speed = speed;
    this.angle = angle;
	if(side == RobotSide.LEFT) {
		this.angle = -this.angle;
		p = 0.0196;
	}
	else {
		p = 0.0199;
	}
	
    drive = DriveTrain.getInstance();
    
  }
  
  public TurnInPlace(double angle, double speed, boolean inverted) {

	    this.speed = speed;
	    this.angle = angle;
		if(inverted) {
			this.angle = -this.angle;
		}
		
	    drive = DriveTrain.getInstance();
	    
  }
  
  public TurnInPlace(double angle, double speed) {

	    this.speed = speed;
	    this.angle = angle;
		
	    drive = DriveTrain.getInstance();
	    
}

  protected void initialize() {
	  
    drive.resetGyro();
    RobotWantedStates.wantedDriveType = DriveType.EXTERNAL_DRIVING;
    
  }

  protected boolean isFinished() {
	  if(Math.abs(error) < 1 || this.isTimedOut()) {
		  i++;
	  }
	  else {
		  i = 0;
	  }

	  return i > 25;
    
  }

  protected void execute() {

    
    error = drive.getAngle() - this.angle;
    SmartDashboard.putNumber("Turn Error", error);
    drive.left.set(ControlMode.PercentOutput, -p * error * speed);
    drive.right.set(ControlMode.PercentOutput, p * error * speed);
    
    
  }

  protected void end() {

	  drive.left.set(ControlMode.PercentOutput, 0);
	  drive.right.set(ControlMode.PercentOutput, 0);

  }

  protected void interrupted() {

    end();
  }
}