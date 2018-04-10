package com.team2169.robot.auto.tasks.drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.kauailabs.navx.frc.AHRS;
import com.team2169.robot.RobotStates.DriveType;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.auto.AutoConstants.RobotSide;
import com.team2169.robot.auto.tasks.Task;
import com.team2169.robot.subsystems.DriveTrain;
import com.team2169.robot.subsystems.Superstructure;

public class TurnInPlace extends Task{

  private double speed = 0.0;
  private double angle = 0.0;
  private AHRS navX;
  private DriveTrain drive;
  private double error;

  public TurnInPlace(double angle, double speed, RobotSide side) {

    this.speed = speed;
    this.angle = angle;
	if(side == RobotSide.RIGHT) {
		this.angle = -this.angle;
	}
    navX = Superstructure.drive.navX;
    drive = Superstructure.drive;
    
  }
  
  public TurnInPlace(double angle, double speed, boolean inverted) {

	    this.speed = speed;
	    this.angle = angle;
		if(inverted) {
			this.angle = -this.angle;
		}
	    navX = Superstructure.drive.navX;
	    drive = Superstructure.drive;
	    
  }
  
  public TurnInPlace(double angle, double speed) {

	    this.speed = speed;
	    this.angle = angle;
		
	    navX = Superstructure.drive.navX;
	    drive = Superstructure.drive;
	    
}

  protected void initialize() {
	  
    navX.reset();
    RobotWantedStates.wantedDriveType = DriveType.EXTERNAL_DRIVING;
    
  }

  protected boolean isFinished() {

    return Math.abs(error) < 1 || this.isTimedOut();
  }

  protected void execute() {

    double p = 0.025;
    error = navX.getAngle() - this.angle;
    drive.left.set(ControlMode.PercentOutput, p * error * speed);
    drive.right.set(ControlMode.PercentOutput, -p * error * speed);
    
    
  }

  protected void end() {

	  drive.left.set(ControlMode.PercentOutput, 0);
	  drive.right.set(ControlMode.PercentOutput, 0);

  }

  protected void interrupted() {

    end();
  }
}