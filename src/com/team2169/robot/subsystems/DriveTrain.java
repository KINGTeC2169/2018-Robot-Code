
package com.team2169.robot.subsystems;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import com.team2169.robot.ActuatorMap;
import com.team2169.robot.Constants;
import com.team2169.robot.ControlMap;
import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotWantedStates;
import com.team2169.robot.RobotStates.DriveMode;
import com.team2169.robot.RobotStates.DriveType;
import com.team2169.robot.subsystems.Subsystem;
import com.team2169.util.DebugPrinter;
import com.team2169.util.FlyByWireHandler;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends Subsystem {

	private static DriveTrain dInstance = null;

	public static DriveTrain getInstance() {
		if (dInstance == null) {
			dInstance = new DriveTrain();
		}
		return dInstance;
	}

	public TalonSRX leftMaster;
	private TalonSRX leftTop;
	private TalonSRX leftFront;
	public TalonSRX rightMaster;
	private TalonSRX rightFront;
	private TalonSRX rightTop;
	private DoubleSolenoid shifter;
	private DoubleSolenoid ptoShift;
	public AHRS navX;
	private EncoderFollower[] followers;

	private enum PathCalculationStatus {
		CALCULATING, IDLE, FINISHED
	}

	PathCalculationStatus pathCalculationStatus = PathCalculationStatus.IDLE;

	// define pathfinder variables
	public boolean isProfileFinished = false;

	public DriveTrain() {

		// Define IMU
		navX = new AHRS(SPI.Port.kMXP, (byte) 200);

		// Create the objects and set properties
		leftMaster = new TalonSRX(ActuatorMap.leftMasterDriveTalon);
		leftFront = new TalonSRX(ActuatorMap.leftFront);
		leftTop = new TalonSRX(ActuatorMap.leftTop);

		rightMaster = new TalonSRX(ActuatorMap.rightMasterDriveTalon);
		rightFront = new TalonSRX(ActuatorMap.rightFront);
		rightTop = new TalonSRX(ActuatorMap.rightTop);

		// Configure Mag Encoders
		leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
		rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);

		// Set Left Slaves
		leftTop.set(ControlMode.Follower, ActuatorMap.leftMasterDriveTalon);
		leftFront.set(ControlMode.Follower, ActuatorMap.leftMasterDriveTalon);

		// Set Right Slaves
		rightFront.set(ControlMode.Follower, ActuatorMap.rightMasterDriveTalon);
		rightTop.set(ControlMode.Follower, ActuatorMap.rightMasterDriveTalon);
		
		// Set Inversions
		leftMaster.setInverted(true);
		leftFront.setInverted(true);
		rightTop.setInverted(true);

		// Set Current Limits

		leftMaster.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		leftFront.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		leftTop.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		rightMaster.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		rightFront.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
		rightTop.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);

		enableRamping();

		// Shifting Solenoids
		shifter = new DoubleSolenoid(ActuatorMap.PCMPort, ActuatorMap.dtSpeedShiftForward,
				ActuatorMap.dtSpeedShiftReverse);
		ptoShift = new DoubleSolenoid(ActuatorMap.PCMPort, ActuatorMap.ptoShiftForward, ActuatorMap.ptoShiftReverse);

		RobotWantedStates.wantedDriveMode = DriveMode.SHIFT_TO_LOW;
		RobotWantedStates.wantedDriveType = DriveType.WANTS_TO_DRIVE;
		pathCalculationStatus = PathCalculationStatus.IDLE;

	}
	
	void enableRamping() {
		leftMaster.configOpenloopRamp(Constants.driveTrainRampRate, 0);
		leftMaster.configClosedloopRamp(Constants.driveTrainRampRate, 0);
		rightMaster.configOpenloopRamp(Constants.driveTrainRampRate, 0);
		rightMaster.configClosedloopRamp(Constants.driveTrainRampRate, 0);
	}

	void disableRamping() {
		leftMaster.configOpenloopRamp(0, 0);
		leftMaster.configClosedloopRamp(0, 0);
		rightMaster.configOpenloopRamp(0, 0);
		rightMaster.configClosedloopRamp(0, 0);
	}

	public void resetEncoders() {
		leftMaster.setSelectedSensorPosition(0, Constants.leftDriveData.pidLoopIDx, Constants.leftDriveData.timeoutMs);
		rightMaster.setSelectedSensorPosition(0, Constants.rightDriveData.pidLoopIDx,
				Constants.rightDriveData.timeoutMs);
	}

	public double getEncoderDistanceRight() {
		return (rightMaster.getSelectedSensorPosition(0) * Math.PI * Constants.wheelDiameter)
				/ Constants.ticksPerRotation;
	}

	public double getEncoderDistanceLeft() {
		return (leftMaster.getSelectedSensorPosition(0) * Math.PI * Constants.wheelDiameter)
				/ Constants.ticksPerRotation;
	}

	public double getEncoderRawLeft() {
		return leftMaster.getSelectedSensorPosition(0);
	}

	public double getEncoderRawRight() {
		return rightMaster.getSelectedSensorPosition(0);
	}

	public double getAngle() {
		return -navX.getAngle();
	}

	public void resetGyro() {
		navX.reset();
	}

	private void drive(double leftPower, double rightPower) {
		leftMaster.set(ControlMode.PercentOutput, leftPower);
		rightMaster.set(ControlMode.PercentOutput, rightPower);
		leftTop.set(ControlMode.PercentOutput, leftPower * 0.91);
		rightTop.set(ControlMode.PercentOutput, rightPower * 0.91);
	}

	public void driveHandler() {

		switch (RobotWantedStates.wantedDriveType) {

		// Drive without Override
		case NORMAL_DRIVING:
		default:

			// Debugging Information
			if (RobotStates.debugMode) {
				DriverStation.reportWarning("DriveTrain: No Override, Standard Driving", false);
			}

			// Acceleration Handler with Squared controls
			leftMaster.set(ControlMode.PercentOutput, ControlMap.leftTankStick(false));
			rightMaster.set(ControlMode.PercentOutput, ControlMap.rightTankStick(false));
			leftTop.set(ControlMode.PercentOutput, ControlMap.leftTankStick(false) * 0.91);
			rightTop.set(ControlMode.PercentOutput, ControlMap.rightTankStick(false) * 0.91);

			// Shift without override
			shift(false);

			// Set Robot State
			RobotStates.driveType = DriveType.NORMAL_DRIVING;

			break;

		// Drive with Override
		case OVERRIDE_DRIVING:

			// Debugging Information
			if (RobotStates.debugMode) {
				DriverStation.reportWarning("DriveTrain: Override Mode", false);
			}

			// Drive with Override
			leftMaster.set(ControlMode.PercentOutput, ControlMap.leftTankStick(true));
			rightMaster.set(ControlMode.PercentOutput, ControlMap.rightTankStick(true));
			leftTop.set(ControlMode.PercentOutput, ControlMap.leftTankStick(true) * 0.91);
			rightTop.set(ControlMode.PercentOutput, ControlMap.rightTankStick(true) * 0.91);
			// Shift with override
			shift(true);

			// Set Robot State
			RobotStates.driveType = DriveType.OVERRIDE_DRIVING;

			break;

		// Hang
		case HANG:

			// Debugging Information
			if (RobotStates.debugMode) {
				DriverStation.reportWarning("DriveTrain: Hang Mode", false);
			}

			// Set Wheels to Hang Power
			leftMaster.set(ControlMode.PercentOutput, Constants.climbPower);

			// Set Robot State
			RobotStates.driveType = DriveType.HANG;

			break;

		// Single-Run Go into Hang Mode
		case WANTS_TO_HANG:

			// Debugging Information
			if (RobotStates.debugMode) {
				DriverStation.reportWarning("DriveTrain: Wants To Hang Mode", false);
			}

			// Set Talon Modes for Driving
			rightMaster.set(ControlMode.Follower, ActuatorMap.leftMasterDriveTalon);
			rightFront.set(ControlMode.Follower, ActuatorMap.leftMasterDriveTalon);
			rightTop.set(ControlMode.Follower, ActuatorMap.leftMasterDriveTalon);

			// Dogshifter Extended
			ptoShift.set(Value.kForward);
			RobotStates.ptoActive = true;

			// Set Robot State
			RobotStates.driveType = DriveType.WANTS_TO_HANG;

			break;

		// Single-Run Go into Drive Mode
		case WANTS_TO_DRIVE:

			// Debugging Information
			if (RobotStates.debugMode) {
				DriverStation.reportWarning("DriveTrain: Wants To Drive Mode", false);
			}

			// Set Talon Modes for Driving
			rightFront.set(ControlMode.Follower, ActuatorMap.rightMasterDriveTalon);
			rightTop.set(ControlMode.Follower, ActuatorMap.rightMasterDriveTalon);

			// Dogshifter Retracted
			ptoShift.set(Value.kReverse);
			RobotStates.ptoActive = false;

			// Set Robot State
			RobotStates.driveType = DriveType.WANTS_TO_DRIVE;

			break;

		case WANTS_TO_FOLLOW_PATH:
			
			RobotStates.driveType = DriveType.WANTS_TO_FOLLOW_PATH;
			
			DriverStation.reportWarning("Wanting To Follow Path", false);
			switch (pathCalculationStatus) {
			case IDLE:
			default:
				DriverStation.reportWarning("Calculating started", false);
				followers = this.pathSetup(RobotStates.currentPath);
				break;
			case CALCULATING:
				break;
			case FINISHED:
				RobotWantedStates.wantedDriveType = DriveType.WAITING;
				break;
			}
			break;

		case FOLLOW_PATH:
			pathFollow(followers, false);
			RobotStates.driveType = DriveType.FOLLOW_PATH;
			break;

		case WAITING:
			RobotStates.driveType = DriveType.WAITING;
			break;

		}

		RobotStates.gyroAngle = getAngle();

	}

	void wantedClimbHander(boolean climb) {
		if (climb) {

		}

		else if (!climb) {

			// Set Right Slaves as slaves of Right Master

		}
	}

	void shift(boolean override) {
		// If the override is active, switch to the requested Drive Mode

		// If Master Override is Active
		if (override) {

			if (RobotStates.debugMode) {
				DriverStation.reportWarning("DriveTrain Class: Shifting Override Active", false);
			}

			// If wanting to shift high
			if (RobotWantedStates.wantedDriveMode == DriveMode.SHIFT_TO_HIGH) {

				shifter.set(Constants.highGear);

				// Set Robot States
				RobotStates.driveMode = DriveMode.HIGH;
				RobotWantedStates.wantedDriveMode = DriveMode.HIGH;

			}

			// If wanting to shift low
			if (RobotWantedStates.wantedDriveMode == DriveMode.SHIFT_TO_LOW) {

				shifter.set(Constants.lowGear);

				// Set Robot States
				RobotStates.driveMode = DriveMode.LOW;
				RobotWantedStates.wantedDriveMode = DriveMode.LOW;

			}
		}

		// If Master Override is not Active
		else {

			// If wanting to shift shift high, and it is safe
			if (RobotWantedStates.wantedDriveMode == DriveMode.SHIFT_TO_HIGH
					&& FlyByWireHandler.determineShiftSafety(RobotWantedStates.wantedDriveMode)) {

				shifter.set(Constants.highGear);

				if (RobotStates.debugMode) {
					DriverStation.reportWarning("High Gear (safe)", false);
				}

				// Set Robot States
				RobotStates.driveMode = DriveMode.HIGH;
				RobotWantedStates.wantedDriveMode = DriveMode.HIGH;

			}

			// If wanting to shift shift low, and it is safe
			else if (RobotWantedStates.wantedDriveMode == DriveMode.SHIFT_TO_LOW
					&& FlyByWireHandler.determineShiftSafety(RobotWantedStates.wantedDriveMode)) {

				shifter.set(Constants.lowGear);

				if (RobotStates.debugMode) {
					DriverStation.reportWarning("Low Gear (safe)", false);
				}

				// Set Robot States
				RobotStates.driveMode = DriveMode.LOW;
				RobotWantedStates.wantedDriveMode = DriveMode.LOW;

			}

		}
	}

	@Override
	public void pushToDashboard() {

		// Put any SmartDash info here.
		SmartDashboard.putNumber("Gyro", RobotStates.gyroAngle);
		DebugPrinter.driveTrainDebug();
		SmartDashboard.putNumber("Left Encoder Value: ",
				leftMaster.getSelectedSensorPosition(Constants.leftDriveData.slotIDx));
		SmartDashboard.putNumber("Right Encoder Value: ",
				rightMaster.getSelectedSensorPosition(Constants.rightDriveData.slotIDx));

		SmartDashboard.putNumber("RightTop", rightTop.getOutputCurrent());
		SmartDashboard.putNumber("RightMaster", rightMaster.getOutputCurrent());
		SmartDashboard.putNumber("RightFront", rightFront.getOutputCurrent());
		SmartDashboard.putNumber("RightToVolts", rightTop.getMotorOutputVoltage());
		SmartDashboard.putNumber("RightMasterVolts", rightMaster.getMotorOutputVoltage());
		SmartDashboard.putNumber("RightFrontVolts", rightFront.getMotorOutputVoltage());

		SmartDashboard.putNumber("LeftTop", leftTop.getOutputCurrent());
		SmartDashboard.putNumber("LeftMaster", leftTop.getOutputCurrent());
		SmartDashboard.putNumber("LeftFront", leftTop.getOutputCurrent());
		SmartDashboard.putNumber("LeftTopVolts", leftTop.getMotorOutputVoltage());
		SmartDashboard.putNumber("LeftMasterVolts", leftTop.getMotorOutputVoltage());
		SmartDashboard.putNumber("LeftFrontVolts", leftTop.getMotorOutputVoltage());
	}

	@Override
	public void zeroSensors() {

		resetGyro();
		resetEncoders();
	}

	public boolean getIsProfileFinished() {
		return isProfileFinished;
	}

	public void resetForPath() {
		isProfileFinished = false;
		resetEncoders();
		resetGyro();
		pathCalculationStatus = PathCalculationStatus.IDLE;
	}

	public void resetPathAngleOffset() {
		PathfinderData.path_angle_offset = 0.0;
	}

	public void stopPath() {
		isProfileFinished = true;
	}

	public EncoderFollower[] pathSetup(Waypoint[] path) {
		pathCalculationStatus = PathCalculationStatus.CALCULATING;
		DriverStation.reportWarning("calculating path - drivetrain", false);
		EncoderFollower left = new EncoderFollower();
		EncoderFollower right = new EncoderFollower();
		Trajectory.Config cfg = new Trajectory.Config(Trajectory.FitMethod.HERMITE_QUINTIC,
				Trajectory.Config.SAMPLES_HIGH, PathfinderData.dt, PathfinderData.max_velocity,
				PathfinderData.max_acceleration, PathfinderData.max_jerk);
		DriverStation.reportWarning("config set - drivetrain", false);

		//String pathHash = String.valueOf(path.hashCode());
		//String configHash = String.valueOf(cfg.hashCode());
		//SmartDashboard.putString("Path Hash", pathHash);
		Trajectory toFollow = Pathfinder.generate(path, cfg);
		/*File trajectory = new File("/home/lvuser/paths/" + pathHash + configHash + ".csv");
		if (!trajectory.exists()) {
			toFollow = Pathfinder.generate(path, cfg);
			Pathfinder.writeToCSV(trajectory, toFollow);
			System.out.println(pathHash + configHash + ".csv not found, wrote to file");
		} else {
			System.out.println(pathHash + configHash +".csv read from file");
			toFollow = Pathfinder.readFromCSV(trajectory);
		}*/

		TankModifier modifier = new TankModifier(toFollow).modify(PathfinderData.wheel_base_width);
		PathfinderData.last_gyro_error = 0.0;
		RobotStates.leftPathTotalSegments = modifier.getLeftTrajectory().length();
		RobotStates.rightPathTotalSegments = modifier.getRightTrajectory().length();
		left = new EncoderFollower(modifier.getLeftTrajectory());
		right = new EncoderFollower(modifier.getRightTrajectory());
		left.configureEncoder(leftMaster.getSelectedSensorPosition(0), Constants.ticksPerRotation,
				PathfinderData.wheel_diameter);
		right.configureEncoder(rightMaster.getSelectedSensorPosition(0), Constants.ticksPerRotation,
				Constants.wheelDiameter);
		left.configurePIDVA(PathfinderData.kp, PathfinderData.ki, PathfinderData.kd, PathfinderData.kv,
				PathfinderData.ka);
		right.configurePIDVA(PathfinderData.kp, PathfinderData.ki, PathfinderData.kd, PathfinderData.kv,
				PathfinderData.ka);
		DriverStation.reportWarning("calculated path - drivetrain", false);
		pathCalculationStatus = PathCalculationStatus.FINISHED;
		return new EncoderFollower[] { left, // 0
				right, // 1
		};
	}

	public void pathFollow(EncoderFollower[] followers, boolean reverse) {
		DriverStation.reportWarning("running path - drivetrain", false);
		EncoderFollower left = followers[0];
		EncoderFollower right = followers[1];
		double l;
		double r;
		double localGp = PathfinderData.gp;
		if (!reverse) {
		//localGp *= -1;

			l = left.calculate(-leftMaster.getSelectedSensorPosition(Constants.leftDriveData.slotIDx));
			r = right.calculate(-rightMaster.getSelectedSensorPosition(Constants.rightDriveData.slotIDx));
		} else {
			l = left.calculate(leftMaster.getSelectedSensorPosition(Constants.leftDriveData.slotIDx));
			r = right.calculate(rightMaster.getSelectedSensorPosition(Constants.rightDriveData.slotIDx));
		}

		//double gyro_heading = reverse ? -getAngle() - PathfinderData.path_angle_offset : getAngle() + PathfinderData.path_angle_offset;
		double gyro_heading = reverse ? -getAngle() : getAngle();
		//double gyro_heading = getAngle();
		double angle_setpoint = Pathfinder.r2d(left.getHeading());
		SmartDashboard.putNumber("Angle setpoint", angle_setpoint);
		double angleDifference = Pathfinder.boundHalfDegrees(angle_setpoint - gyro_heading);
		SmartDashboard.putNumber("Angle difference", angleDifference);

		double turn = localGp * angleDifference
				+ (PathfinderData.gd * ((angleDifference - PathfinderData.last_gyro_error) / PathfinderData.dt));

		PathfinderData.last_gyro_error = angleDifference;

		if ((left != null && !left.isFinished()) && (right != null && !right.isFinished()) && (Math.abs(angleDifference) >= 3)) {
			
			SmartDashboard.putNumber("Turn", turn);
			SmartDashboard.putNumber("Left diff", left.getSegment().x + this.getEncoderDistanceLeft());
			SmartDashboard.putNumber("Left Completion Precentage", (left.getSegment().position / RobotStates.leftPathTotalSegments));
			SmartDashboard.putNumber("Right Completion Precentage", (right.getSegment().position / RobotStates.leftPathTotalSegments));
			SmartDashboard.putNumber("Left calc voltage", l);
			SmartDashboard.putNumber("Right calc voltage", r);
			
			SmartDashboard.putNumber("Commanded seg heading", left.getHeading());
			SmartDashboard.putNumber("-Left + turn", -l + turn);
			SmartDashboard.putNumber("-Right - turn", -r - turn);
			SmartDashboard.putNumber("Gyro", this.getAngle());
			SmartDashboard.putNumber("Left seg acceleration", left.getSegment().acceleration);
			SmartDashboard.putNumber("Path angle offset", PathfinderData.path_angle_offset);
			SmartDashboard.putNumber("Angle offset - h", angleDifference - PathfinderData.last_gyro_error);
		}
		if (!reverse) {
			drive(-l + turn, -r - turn);
			//drive(-l, -r);
		} else {
			drive(l + turn, r - turn);
			//drive(l, r);
		}

		if (left.isFinished() && right.isFinished() && (Math.abs(angleDifference) <= 3)) {
			isProfileFinished = true;
			PathfinderData.path_angle_offset = angleDifference;
		}
	}

	@Override
	public void stop() {
		// Code to make all moving parts halt goes here.
		leftMaster.set(ControlMode.Disabled, 1);
		rightMaster.set(ControlMode.Disabled, 1);
		resetGyro();
		resetEncoders();
	}

	public static class PathfinderData {

		public static double kp = 0.00035;
		public static double kd = 0.00001;
		public static double gp = 0.00075;
		public static double gd = 0.00002;

		public static double ki = 0.0;

		// Gyro logging for motion profiling
		public static double last_gyro_error = 0.0;

		public static double path_angle_offset = 0.0;
		public static final double max_velocity = 4.0;
		public static final double kv = 1.0 / max_velocity;
		public static final double max_acceleration = 3.8;
		public static final double ka = 0.05;
		public static final double max_jerk = 16.0;
		public static final double wheel_diameter =  0.1498;

		public static final double wheel_base_width = 0.635;
		public static final int ticks_per_rev = 4096;
		public static final double dt = 0.02;

		public static void setPIDG(double p, double i, double d, double gp, double gd) {
			SmartDashboard.putNumber("kP", p);
			SmartDashboard.putNumber("kI", i);
			SmartDashboard.putNumber("kD", d);
			SmartDashboard.putNumber("gP", gp);
			SmartDashboard.putNumber("gD", gd);
		}

		public static void updatePIDG() {
			kp = SmartDashboard.getNumber("kP", 0.0);
			ki = SmartDashboard.getNumber("kI", 0.0);
			kd = SmartDashboard.getNumber("kD", 0.0);
			gp = SmartDashboard.getNumber("gP", 0.0);
			gd = SmartDashboard.getNumber("gD", 0.0);
		}
	}
}
