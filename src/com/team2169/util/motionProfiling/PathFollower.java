package com.team2169.util.motionProfiling;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PathFollower {

    private ProfileTalon left;
    private ProfileTalon right;
    public boolean running = false;

    public Notifier processBuffer;

    public PathFollower(ProfileTalon left, ProfileTalon right)
    {
        this.left = left;
        this.right = right;
        processBuffer = new Notifier(() -> {
            //System.out.println("processBuffer notifier");
        	
        	if(running) {
        		 processMotionProfileBufferPeriodic();
                 followProfilePeriodic();
        	}
           
        });
    }

    void setPath(MotionProfilePath path)
    {
        if (path.leftPath.size() != path.rightPath.size())
            System.err.println("Profile size mismatch, things WILL break.");
        left.setProfile(path.leftPath);
        right.setProfile(path.rightPath);
    }

    public void initFollowProfile()
    {
    
    	running = true;
    	if(!((left.profile.size() == 0) || (left.profile == null)) && !((right.profile.size() == 0) || (right.profile == null))) {
    		
    		for (ProfileTalon v : new ProfileTalon[] {left, right})
            {
                v.followInit();
                for (int i = 0; i < 64; i++)
                    v.sendNextPoint(); // Get some initial points
            }
            System.out.println("Init Follow Profile");
    		
    	}
    	
    	else {
    		if(left.profile == null) {
    			System.out.println("Path is null!");
    		}
    		else {
    			System.out.println("Path is empty!");
    		}
    	}
    	
    	
    	
        
    }

    public void processMotionProfileBufferPeriodic()
    {
        for (ProfileTalon v : new ProfileTalon[] {left, right})
        {
        	if(!v.isMotionProfileTopLevelBufferFull() && !(v.getSentPoints() > v.profile.size())){
        		v.sendNextPoint();
        	}
             // It's ok if this fails, we won't lose any points
            v.processMotionProfileBuffer(); // Move points from the top buffer to the bottom buffer
        }
        //System.out.println("ProcessBuffer");
    }

    public void followProfilePeriodic(){
    	
		SmartDashboard.putNumber("Left Top Count", left.getMotionProfileTopLevelBufferCount());
		SmartDashboard.putBoolean("Left Top Full", left.isMotionProfileTopLevelBufferFull());
		SmartDashboard.putNumber("Right Top Count", right.getMotionProfileTopLevelBufferCount());
		SmartDashboard.putBoolean("Right Top Full", right.isMotionProfileTopLevelBufferFull());
    	
        MotionProfileStatus statusL = left.getStatus();
        MotionProfileStatus statusR = right.getStatus();
        left.set(ControlMode.MotionProfile, statusL.isLast ? 2 : 1);
        right.set(ControlMode.MotionProfile, statusR.isLast ? 2 : 1);
        //System.out.println("followProfile");
    }

    public boolean doneWithProfile(){
        MotionProfileStatus statusL = left.getStatus();
        MotionProfileStatus statusR = right.getStatus();
        return statusL.isLast && statusR.isLast;
    }

    public void startFollowing(MotionProfilePath path){
    	
    	setPath(path);
    	
        System.out.println("startFollowing");
        if (left.profile == null || right.profile == null) {
            System.err.println("Call setProfiles before attempting to follow a profile");
            return;
        }
        initFollowProfile();
        System.out.println("Starting notifiers");
        processBuffer.startPeriodic(10/2000);
        System.out.println("Notifiers started");
    }

    public void stopFollowing(){
    	running = false;
        System.out.println("stopFollowing");
        left.reset();
        right.reset();
        processBuffer.stop();
    }
}