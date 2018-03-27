package com.team2169.util.motionProfiling;

import java.util.ArrayList;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.team2169.robot.Constants;
import com.team2169.robot.subsystems.DriveTrain.PathfinderData;

public class ProfileTalon extends WPI_TalonSRX {

    public ArrayList<MotionProfilePoint> profile;
    
    private int pointsSent;
    
    public ProfileTalon(int port)
    {
        super(port);
        reset();
        setGains(new SRXGains(PathfinderData.kP,PathfinderData.kI,PathfinderData.kD,PathfinderData.kF));
        pointsSent = 0;
    }

    public void setProfile(ArrayList<MotionProfilePoint> path) {
    	profile = path;
    }
    
    public void zeroEncoder()
    {
        setSelectedSensorPosition(0,0, Constants.kTimeoutMs);
    }

    public void setGains(SRXGains gains)
    {
        config_kP(0, gains.p, Constants.kTimeoutMs);
        config_kI(0, gains.i, Constants.kTimeoutMs);
        config_kD(0, gains.d, Constants.kTimeoutMs);
        config_kF(0, gains.f, Constants.kTimeoutMs);
    }

    public boolean sendNextPoint(){
    		
    		//System.out.println("sendN");
            if (profile == null)
            {
                System.err.println("Attempted to load null profile to talon.");
                return false;
            }
            if (pointsSent >= profile.size())
            {
            	System.out.println("Sending Point #: " + pointsSent);
                System.err.println("Attempted to send more points than were in the profile.");
                return false;
            }

            if (pushMotionProfileTrajectory(profile.get(pointsSent)) == ErrorCode.OK)
            {
            	System.out.println("Sending Point: " + profile.get(pointsSent).position + 
            			", " + profile.get(pointsSent).velocity + ", " + profile.get(pointsSent).zeroPos + 
            			", " + profile.get(pointsSent).isLastPoint);
                pointsSent++;
                return true;
            }

            return false;

    }
    
    public void configForProfiling() {

		this.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
		this.setSensorPhase(true); /* keep sensor and motor in phase */
		this.configNeutralDeadband(Constants.kNeutralDeadband, Constants.kTimeoutMs);

		this.config_kF(0, PathfinderData.kF, Constants.kTimeoutMs);
		this.config_kP(0, PathfinderData.kP, Constants.kTimeoutMs);
		this.config_kI(0, PathfinderData.kI, Constants.kTimeoutMs);
		this.config_kD(0, PathfinderData.kD, Constants.kTimeoutMs);

		this.configMotionProfileTrajectoryPeriod(10, Constants.kTimeoutMs); 
		this.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.kTimeoutMs);
		
		this.clearMotionProfileTrajectories();
    }

    public int getSentPoints() {
    	return pointsSent;
    }
    
    public MotionProfileStatus getStatus()
    {
        MotionProfileStatus status = new MotionProfileStatus();
        getMotionProfileStatus(status);
        return status;
    }

    public void followInit()
    {
    	configForProfiling();
        set(ControlMode.PercentOutput, 0);
        pointsSent = 0;
        zeroEncoder();
        clearMotionProfileTrajectories();
    }

    public void reset()
    {
        set(ControlMode.PercentOutput, 0);
        clearMotionProfileTrajectories();
        zeroEncoder();
        pointsSent = 0;
        profile = null;
    }

    public int getEncoderPosition()
    {
        return getSelectedSensorPosition(0);
    }

    public int getEncoderVelocity()
    {
        return getSelectedSensorVelocity(0);
    }
    
    public class SRXGains {
    	
        public double p;
        public double i;
        public double d;
        public double f;

        public SRXGains(double p, double i, double d, double f)
        {
            this.p = p;
            this.i = i;
            this.d = d;
            this.f = f;
        }
    	
    }
    
}
