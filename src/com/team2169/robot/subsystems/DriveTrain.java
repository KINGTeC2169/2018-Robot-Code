package com.team2169.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;
import com.team2169.robot.*;
import com.team2169.robot.RobotStates.DriveMode;
import com.team2169.robot.RobotStates.DriveType;
import com.team2169.util.DebugPrinter;
import com.team2169.util.FlyByWireHandler;
import com.team2169.util.motionProfiling.MotionProfilePath;
import com.team2169.util.motionProfiling.PathFollower;
import com.team2169.util.motionProfiling.PathStorageHandler;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

public class DriveTrain extends Subsystem {

    private static DriveTrain dInstance = null;

    public static DriveTrain getInstance() {
        if (dInstance == null) {
            dInstance = new DriveTrain();
        }
        return dInstance;
    }

    public TalonSRX left;
    private TalonSRX leftTop;
    private TalonSRX leftFront;
    public TalonSRX right;
    private TalonSRX rightFront;
    private TalonSRX rightTop;
    private DoubleSolenoid shifter;
    private DoubleSolenoid ptoShift;
    private PathFollower pathFollower;
    public AHRS navX;
    private int maxLeft = 0;
    private int maxRight = 0;

    private DriveTrain() {
    	
        // Define IMU
        navX = new AHRS(SPI.Port.kMXP, (byte) 200);

        // Create the objects and set properties
        left = new TalonSRX(ActuatorMap.leftMasterDriveTalon);
        leftFront = new TalonSRX(ActuatorMap.leftFront);
        leftTop = new TalonSRX(ActuatorMap.leftTop);

        right = new TalonSRX(ActuatorMap.rightMasterDriveTalon);
        rightFront = new TalonSRX(ActuatorMap.rightFront);
        rightTop = new TalonSRX(ActuatorMap.rightTop);

        // Configure Mag Encoders
        left.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
        right.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);

        // Set Left Slaves
        leftTop.set(ControlMode.Follower, ActuatorMap.leftMasterDriveTalon);
        leftFront.set(ControlMode.Follower, ActuatorMap.leftMasterDriveTalon);

        // Set Right Slaves
        rightFront.set(ControlMode.Follower, ActuatorMap.rightMasterDriveTalon);
        rightTop.set(ControlMode.Follower, ActuatorMap.rightMasterDriveTalon);

        // Set Inversions
        left.setInverted(true);
        leftFront.setInverted(true);
        rightTop.setInverted(true);

        // Set Current Limits

        left.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
        leftFront.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
        leftTop.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
        right.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
        rightFront.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);
        rightTop.configContinuousCurrentLimit(Constants.maxDriveTrainCurrent, Constants.driveTrainCurrentTimeout);

        enableRamping();
        
        // Shifting Solenoids
        shifter = new DoubleSolenoid(ActuatorMap.PCMPort, ActuatorMap.dtSpeedShiftForward,
                ActuatorMap.dtSpeedShiftReverse);
        ptoShift = new DoubleSolenoid(ActuatorMap.PCMPort, ActuatorMap.ptoShiftForward, ActuatorMap.ptoShiftReverse);
        
        RobotWantedStates.wantedDriveMode = DriveMode.SHIFT_TO_LOW;
        RobotWantedStates.wantedDriveType = DriveType.WANTS_TO_DRIVE;

    }

    private void enableRamping() {
        left.configOpenloopRamp(Constants.driveTrainRampRate, 0);
        left.configClosedloopRamp(Constants.driveTrainRampRate, 0);
        right.configOpenloopRamp(Constants.driveTrainRampRate, 0);
        right.configClosedloopRamp(Constants.driveTrainRampRate, 0);
    }

    @SuppressWarnings("unused")
    private void disableRamping() {
        left.configOpenloopRamp(Constants.driveTrainRampRate, 0);
        left.configClosedloopRamp(Constants.driveTrainRampRate, 0);
        right.configOpenloopRamp(Constants.driveTrainRampRate, 0);
        right.configClosedloopRamp(Constants.driveTrainRampRate, 0);
    }

    private void resetEncoders() {
        left.setSelectedSensorPosition(0, Constants.leftDriveData.pidLoopIDx, Constants.leftDriveData.timeoutMs);
        right.setSelectedSensorPosition(0, Constants.rightDriveData.pidLoopIDx,
                Constants.rightDriveData.timeoutMs);
    }

    public double getAngle() {
        return -navX.getAngle();
    }

    private void resetGyro() {
        navX.reset();
    }

    void driveHandler() {
    	
        System.out.println(left.getMotorOutputPercent());
    	
        switch (RobotWantedStates.wantedDriveType) {

            // Drive without Override
            case NORMAL_DRIVING:
            default:

                // Debugging Information
                if (RobotStates.debugMode) {
                    DriverStation.reportWarning("DriveTrain: No Override, Standard Driving", false);
                }

                // Acceleration Handler with Squared controls
                left.set(ControlMode.PercentOutput, ControlMap.leftTankStick(false) * FlyByWireHandler.getSafeSpeed());
                right.set(ControlMode.PercentOutput, ControlMap.rightTankStick(false) * FlyByWireHandler.getSafeSpeed());
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
                left.set(ControlMode.PercentOutput, ControlMap.leftTankStick(true));
                right.set(ControlMode.PercentOutput, ControlMap.rightTankStick(true));
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
                left.set(ControlMode.PercentOutput, Constants.climbPower);

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
                right.set(ControlMode.Follower, ActuatorMap.leftMasterDriveTalon);
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

                pathFollower = new PathFollower(generatePath(RobotStates.currentPath), left, right);
                pathFollower.startPath();
                
                RobotWantedStates.wantedDriveType = DriveType.FOLLOW_PATH;
                
                break;

            case FOLLOW_PATH:

            	pathFollower.pathLooper();
                RobotStates.driveType = DriveType.FOLLOW_PATH;
                break;

            case STOP_PATH:
            	pathFollower.stopPath();
                RobotStates.driveType = DriveType.STOP_PATH;
                RobotWantedStates.wantedDriveType = DriveType.NORMAL_DRIVING;
                break;

        }
        
        RobotStates.gyroAngle = getAngle();
        SmartDashboard.putNumber("Left Output", left.getMotorOutputPercent());

    }

    private void shift(boolean override) {
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
    
    public boolean isPathDone() {
    	return pathFollower.isDone();
    }

    @Override
    public void pushToDashboard() {

    	maxVelocityGrabber();
    	
        // Put any SmartDash info here.
        SmartDashboard.putNumber("Gyro", RobotStates.gyroAngle);
        DebugPrinter.driveTrainDebug();
        SmartDashboard.putNumber("Left Encoder Value: ",
                left.getSelectedSensorPosition(Constants.leftDriveData.slotIDx));
        SmartDashboard.putNumber("Right Encoder Value: ",
                right.getSelectedSensorPosition(Constants.rightDriveData.slotIDx));
        
    }

    @Override
    public void zeroSensors() {

        resetGyro();
        resetEncoders();
    }

    void resetForPath() {
        resetEncoders();
        resetGyro();
    }

    void maxVelocityGrabber() {
    	int leftVel = left.getSelectedSensorVelocity(0);
    	int rightVel = right.getSelectedSensorVelocity(0);
    	if(leftVel > maxLeft) {
    		SmartDashboard.putNumber("Left Velocity", leftVel);
    		maxLeft = leftVel;
    	}
    	if(rightVel > maxRight) {
    		SmartDashboard.putNumber("Right Velocity", rightVel);
    		maxRight = rightVel;
    	}
    	
    }
    
    @SuppressWarnings("unused")
	private MotionProfilePath generatePath(Waypoint[] path) {
    	DriverStation.reportWarning("Calculating Path", false);
    	try {
        	Trajectory.Config cfg = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC,
                    Trajectory.Config.SAMPLES_LOW, PathfinderData.timeStep, PathfinderData.max_velocity,
                    PathfinderData.max_acceleration, PathfinderData.max_jerk);
        	return PathStorageHandler.handlePath(path, cfg);
            	
        }
        catch(Exception e){
        	e.printStackTrace();
        	System.out.println("Path Reading Failed");
        	return new MotionProfilePath();
        }
    	
        

    }

    @Override
    public void stop() {
        // Code to make all moving parts halt goes here.
        left.set(ControlMode.Disabled, 1);
        right.set(ControlMode.Disabled, 1);
        resetGyro();
        resetEncoders();
    }

    public static class PathfinderData {

        static final double max_velocity = 7.5*12;
        static final double max_acceleration = 15;
        static final double max_jerk = .5;
        private static final double timeStep = .01;
        
        public static final double wheel_diameter = 6;
        public static final double wheel_base_width = 32;
        
		public static final double kP = 0;
		public static final double kI = 0;
		public static final double kD = 0;
		public static final double kF = 0;

    }
}
