package com.team2169.util.motionProfiling;

import com.ctre.phoenix.motion.TrajectoryPoint;

public class MotionProfilePoint extends TrajectoryPoint{
	
	public double timeStep;
	
	public MotionProfilePoint(double pos, double vel, double dt) {
		this.position = pos;
		this.velocity = vel;
		this.timeStep = dt;
		if(this.timeStep == 5 || this.timeStep == .005) {
			this.timeDur = TrajectoryDuration.Trajectory_Duration_5ms;	
		}
		else if(this.timeStep == 10 || this.timeStep == .01) {
			this.timeDur = TrajectoryDuration.Trajectory_Duration_10ms;	
		}
		else if(this.timeStep == 50|| this.timeStep == .05) {
			this.timeDur = TrajectoryDuration.Trajectory_Duration_50ms;	
		}
		else {
			this.timeDur = TrajectoryDuration.Trajectory_Duration_0ms;	
		}
		this.headingDeg = 0;
		this.profileSlotSelect0 = 0;
		this.profileSlotSelect1 = 0;
	}

}
