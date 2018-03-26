package com.team2169.util.motionProfiling;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Notifier;

public class MotionProfileFollower {

    private ProfileTalon left;
    private ProfileTalon right;

    public Notifier processBuffer;

    public MotionProfileFollower(ProfileTalon left, ProfileTalon right)
    {
        this.left = left;
        this.right = right;
        processBuffer = new Notifier(() -> {
            System.out.println("processBuffer notifier");
            processMotionProfileBufferPeriodic();
            followProfilePeriodic();
        });
    }

    public void setProfiles(MotionProfilePath profiles)
    {
        if (profiles.leftPath.size() != profiles.rightPath.size())
            System.err.println("Profile size mismatch, things WILL break.");
        left.profile = profiles.leftPath;
        right.profile = profiles.rightPath;
    }

    public void initFollowProfile()
    {
        for (ProfileTalon v : new ProfileTalon[] {left, right})
        {
            v.followInit();
            for (int i = 0; i < 64; i++)
                v.sendNextPoint(); // Get some initial points
        }
        System.out.println("Init Follow Profile");
    }

    public void processMotionProfileBufferPeriodic()
    {
        for (ProfileTalon v : new ProfileTalon[] {left, right})
        {
            v.processMotionProfileBuffer(); 
        }
        System.out.println("ProcessBuffer");
    }

    public void followProfilePeriodic()
    {
    	
    	if(!left.isMotionProfileTopLevelBufferFull() && left.getSentPoints() <= left.profile.size()) {
    		left.sendNextPoint();
    	}
    	if(!right.isMotionProfileTopLevelBufferFull() && right.getSentPoints() <= right.profile.size()) {
    		right.sendNextPoint();
    	}
    	
        MotionProfileStatus statusL = left.getStatus();
        MotionProfileStatus statusR = right.getStatus();
        left.set(ControlMode.MotionProfile, statusL.isLast ? 2 : 1);
        right.set(ControlMode.MotionProfile, statusR.isLast ? 2 : 1);
        System.out.println("followProfile");
    }

    public boolean doneWithProfile()
    {
        MotionProfileStatus statusL = left.getStatus();
        MotionProfileStatus statusR = right.getStatus();
        return statusL.isLast && statusR.isLast;
    }

    public void startFollowing()
    {
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

    public void stopFollowing()
    {
        System.out.println("stopFollowing");
        left.reset();
        right.reset();
        processBuffer.stop();
    }
}