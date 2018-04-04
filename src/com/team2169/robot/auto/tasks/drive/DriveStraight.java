package com.team2169.robot.auto.tasks.drive;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.team2169.robot.Constants;
import com.team2169.robot.RobotStates.DriveType;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.auto.tasks.Task;
import com.team2169.robot.subsystems.DriveTrain;
import com.team2169.robot.subsystems.Superstructure;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveStraight extends Task {

    private int desiredEncoderTicks;
    private double initialAngle;
    private DriveTrain drive;
    int i;
    private double speed;
    private int directionFactor;

    public DriveStraight(double inches, double speed_) {

    	System.out.println("RECRREATING FOR NEW DRIVE FORWARD");
    	RobotWantedStates.wantedDriveType = DriveType.EXTERNAL_DRIVING;
        directionFactor = (desiredEncoderTicks >= 0) ? 1 : -1;
        desiredEncoderTicks = (int) (inches /  (Constants.wheelDiameter * Math.PI) * Constants.ticksPerRotation);
        drive = Superstructure.drive;
        speed = speed_;   
    }

    protected void initialize() {

        RobotWantedStates.wantedDriveType = DriveType.EXTERNAL_DRIVING;
        System.out.println("RESETTING FOR NEW DRIVE FORWARD");
        drive.left.setSelectedSensorPosition(0, 0, 10);
        drive.right.setSelectedSensorPosition(0, 0, 10);
        System.out.println("ENCODERS ZEROED");
        drive.navX.reset();
        initialAngle = drive.navX.getAngle();
        System.out.println("Desired Enc Ticks: " + desiredEncoderTicks);
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
    	
        double leftOutput = -(speed - getAngleCorrection()) * directionFactor;
        double rightOutput = -(speed + getAngleCorrection()) * directionFactor;
        System.out.println(getAngleCorrection());
        SmartDashboard.putNumber("Desired Ticks", desiredEncoderTicks);
        SmartDashboard.putNumber("Left DT Error", desiredEncoderTicks - drive.left.getSelectedSensorPosition(0));
        SmartDashboard.putNumber("Right DT Error", desiredEncoderTicks - drive.right.getSelectedSensorPosition(0));
        drive.left.set(ControlMode.PercentOutput, leftOutput);
        drive.right.set(ControlMode.PercentOutput, rightOutput);

    	}
    	
    	i++;
    	
    }

    @Override
    protected boolean isFinished() {
        boolean finished = distanceFinished() || this.isTimedOut();
        SmartDashboard.putBoolean("Segment Finished", finished);
        return finished;
    }

    public boolean distanceFinished() {
    	
    	//If either encoder has hit the point, stop.  This is because red/orange encoders don't read as many ticks, so they overshoot.
    	if(drive.left.getSelectedSensorPosition(0) >= desiredEncoderTicks || drive.right.getSelectedSensorPosition(0) >= desiredEncoderTicks) {
    		System.out.println("DRIVE FINISHED");
    		return true;
    	}
    	return false;
    }
    
    private double getAngleCorrection() {
        return Constants.driveTrainP * getAngleError();
    }

    private double getAngleError() {
        double error = initialAngle - drive.getAngle();
        SmartDashboard.putNumber("Angle Error", error);
        return error;
    }

    protected void end() {
        drive.left.set(ControlMode.PercentOutput, 0);
        drive.right.set(ControlMode.PercentOutput, 0);
    }

    protected void interrupted() {
        end();
    }
}