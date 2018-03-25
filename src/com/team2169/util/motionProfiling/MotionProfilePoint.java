package com.team2169.util.motionProfiling;

import com.ctre.phoenix.motion.TrajectoryPoint;

public class MotionProfilePoint extends TrajectoryPoint{
	
	public MotionProfilePoint(double pos, double vel, double dt, boolean first, boolean last) {
		this.position = pos;
		this.velocity = vel;
		this.timeDur = TrajectoryDuration.Trajectory_Duration_5ms;
		this.isLastPoint = last;
		this.zeroPos = first;
		this.headingDeg = 0;
		this.profileSlotSelect0 = 0;
		this.profileSlotSelect1 = 0;
	}

}
