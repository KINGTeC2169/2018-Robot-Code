package com.team2169.robot.auto.tasks.drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.team2169.robot.Constants;
import com.team2169.robot.RobotStates.DriveType;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.auto.tasks.Task;
import com.team2169.robot.subsystems.DriveTrain;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveStraight extends Task {

    private int desiredEncoderTicks;
    private double initialAngle;
    private DriveTrain drive;
    private int i;
    private int j = 0;
    private double speed;
    private int directionFactor;
    private double leftError = 0;
    private double rightError = 0;

    public DriveStraight(double inches, double speed_) {

    	System.out.println("RECRREATING FOR NEW DRIVE FORWARD");
    	RobotWantedStates.wantedDriveType = DriveType.EXTERNAL_DRIVING;
        directionFactor = (desiredEncoderTicks >= 0) ? -1 : 1;
        desiredEncoderTicks = (int) (inches /  (Constants.wheelDiameter * Math.PI) * Constants.ticksPerRotation);
        desiredEncoderTicks += 400;
        drive = DriveTrain.getInstance();
        speed = speed_;   
    }

    protected void initialize() {

        RobotWantedStates.wantedDriveType = DriveType.EXTERNAL_DRIVING;
        drive.left.setSelectedSensorPosition(0, 0, 10);
        drive.right.setSelectedSensorPosition(0, 0, 10);
        drive.navX.reset();
        initialAngle = drive.navX.getAngle();
        i = 0;
        while(i < 5) {

        	if(((Math.abs(drive.left.getSelectedSensorPosition(0)) <= 50) || drive.left.getSelectedSensorPosition(0) == 0)
        			&& ((Math.abs(drive.right.getSelectedSensorPosition(0)) <= 50) || drive.right.getSelectedSensorPosition(0) == 0)) {
        		i++;
        	}
        	else {
        		i = 0;
        	}
        }
        
        i = 0;
    	
    }

    protected void execute() {

    	if(i > 10) {

	        leftError = desiredEncoderTicks - drive.left.getSelectedSensorPosition(0);
	        rightError = desiredEncoderTicks - drive.right.getSelectedSensorPosition(0);
	        double leftOutput = 0;
	        double rightOutput = 0;
	        leftOutput = (getDesiredSpeed(leftError) - getAngleCorrection()) * directionFactor;
	        rightOutput = (getDesiredSpeed(rightError) + getAngleCorrection()) * directionFactor;
	        SmartDashboard.putNumber("Desired Ticks", desiredEncoderTicks);
	        SmartDashboard.putNumber("Left DT Error", leftError);
	        SmartDashboard.putNumber("Right DT Error", rightError);
	        drive.left.set(ControlMode.PercentOutput, leftOutput);
	        drive.right.set(ControlMode.PercentOutput, rightOutput);
	        
	        System.out.println("Left Error: " + leftError + "Right Error: " + rightError);
	        
	        System.out.println("Left Des Speed: " + getDesiredSpeed(leftError));
	        System.out.println("Right Des Speed: " + getDesiredSpeed(rightError));
	        

    	}
    	
    	i++;
    	
    }

    private double getDesiredSpeed(double error) {
    	double p = Constants.driveStraightP * error;
    	System.out.println(p);
        if(p > speed) {
        	return speed;
        }
        else if(-p < -speed) {
        	return -speed;
        }

        return p;
    	
	}

	@Override
    protected boolean isFinished() {
        boolean finished = distanceFinished() || this.isTimedOut();
        return finished;
    }

    public boolean distanceFinished() {
    	
    	//If either encoder has hit the point, stop.  This is because red/orange encoders don't read as many ticks, so they overshoot.
    	if(Math.abs(leftError) < 100 || Math.abs(rightError) < 100 ) {
    		System.out.println("Loop Added   Left Error: " + leftError + "Right Error: " + rightError);
    		j++;
    	}
    	else {
    		j = 0;
    	}
    	
    	return j > 20;
    }
    
    private double getAngleCorrection() {
        return Constants.driveTrainAngleCorrectionP * getAngleError();
    }

    private double getAngleError() {
        double error = initialAngle - drive.getAngle();
        SmartDashboard.putNumber("Angle Error", error);
        return error;
    }

    protected void end() {
    	System.out.println("DONE");
        drive.left.setNeutralMode(NeutralMode.Brake);
        drive.right.setNeutralMode(NeutralMode.Brake);	
        drive.left.set(ControlMode.PercentOutput, 0);
        drive.right.set(ControlMode.PercentOutput, 0);
    }

    protected void interrupted() {
        end();
    }
}