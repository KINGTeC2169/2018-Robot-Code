package jaci.pathfinder.followers;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;

public class KTEncoderFollower extends EncoderFollower{

	 public KTEncoderFollower(Trajectory traj) {
		 super(traj);
	 }
	
	public double getCompletionPercentage() {
		
		return segment/trajectory.length();
		
	}
	
}
