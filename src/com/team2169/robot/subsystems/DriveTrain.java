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
import com.team2169.util.motionProfiling.ProfileTalon;

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

    public ProfileTalon leftMaster;
    private TalonSRX leftTop;
    private TalonSRX leftFront;
    public ProfileTalon rightMaster;
    private TalonSRX rightFront;
    private TalonSRX rightTop;
    private DoubleSolenoid shifter;
    private DoubleSolenoid ptoShift;
    public AHRS navX;
    private MotionProfilePath profile;
    private PathFollower pathFollower;
    private int maxLeft = 0;
    private int maxRight = 0;

    private DriveTrain() {

    	
        // Define IMU
        navX = new AHRS(SPI.Port.kMXP, (byte) 200);

        // Create the objects and set properties
        leftMaster = new ProfileTalon(ActuatorMap.leftMasterDriveTalon);
        leftFront = new TalonSRX(ActuatorMap.leftFront);
        leftTop = new TalonSRX(ActuatorMap.leftTop);

        rightMaster = new ProfileTalon(ActuatorMap.rightMasterDriveTalon);
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

        profile = new MotionProfilePath();
    	pathFollower = new PathFollower(leftMaster, rightMaster);
        
        RobotWantedStates.wantedDriveMode = DriveMode.SHIFT_TO_LOW;
        RobotWantedStates.wantedDriveType = DriveType.WANTS_TO_DRIVE;

    }

    private void enableRamping() {
        leftMaster.configOpenloopRamp(Constants.driveTrainRampRate, 0);
        leftMaster.configClosedloopRamp(Constants.driveTrainRampRate, 0);
        rightMaster.configOpenloopRamp(Constants.driveTrainRampRate, 0);
        rightMaster.configClosedloopRamp(Constants.driveTrainRampRate, 0);
    }

    @SuppressWarnings("unused")
    private void disableRamping() {
        leftMaster.configOpenloopRamp(Constants.driveTrainRampRate, 0);
        leftMaster.configClosedloopRamp(Constants.driveTrainRampRate, 0);
        rightMaster.configOpenloopRamp(Constants.driveTrainRampRate, 0);
        rightMaster.configClosedloopRamp(Constants.driveTrainRampRate, 0);
    }

    private void resetEncoders() {
        leftMaster.setSelectedSensorPosition(0, Constants.leftDriveData.pidLoopIDx, Constants.leftDriveData.timeoutMs);
        rightMaster.setSelectedSensorPosition(0, Constants.rightDriveData.pidLoopIDx,
                Constants.rightDriveData.timeoutMs);
    }

    public double getAngle() {
        return -navX.getAngle();
    }

    private void resetGyro() {
        navX.reset();
    }

    void driveHandler() {
    	
        switch (RobotWantedStates.wantedDriveType) {

            // Drive without Override
            case NORMAL_DRIVING:
            default:

                // Debugging Information
                if (RobotStates.debugMode) {
                    DriverStation.reportWarning("DriveTrain: No Override, Standard Driving", false);
                }

                // Acceleration Handler with Squared controls
                leftMaster.set(ControlMode.PercentOutput, ControlMap.leftTankStick(false) * FlyByWireHandler.getSafeSpeed());
                rightMaster.set(ControlMode.PercentOutput, ControlMap.rightTankStick(false) * FlyByWireHandler.getSafeSpeed());
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

                profile = generatePath(RobotStates.currentPath);
                pathFollower.startFollowing(profile);

                RobotWantedStates.wantedDriveType = DriveType.FOLLOW_PATH;
                
                break;

            case FOLLOW_PATH:

            	//pathFollower.followProfilePeriodic();
                RobotStates.driveType = DriveType.FOLLOW_PATH;
                break;

            case WAITING:
                RobotStates.driveType = DriveType.WAITING;
                break;

        }
        
        RobotStates.gyroAngle = getAngle();
        SmartDashboard.putNumber("Left Output", leftMaster.getMotorOutputPercent());

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

    @Override
    public void pushToDashboard() {

    	maxVelocityGrabber();
    	
        // Put any SmartDash info here.
        SmartDashboard.putNumber("Gyro", RobotStates.gyroAngle);
        DebugPrinter.driveTrainDebug();
        SmartDashboard.putNumber("Left Encoder Value: ",
                leftMaster.getSelectedSensorPosition(Constants.leftDriveData.slotIDx));
        SmartDashboard.putNumber("Right Encoder Value: ",
                rightMaster.getSelectedSensorPosition(Constants.rightDriveData.slotIDx));
        
    }

    @Override
    public void zeroSensors() {

        resetGyro();
        resetEncoders();
    }

    public boolean getIsProfileFinished() {
        return pathFollower.doneWithProfile();
    }

    void resetForPath() {
        resetEncoders();
        resetGyro();
    }

    void maxVelocityGrabber() {
    	int leftVel = leftMaster.getSelectedSensorVelocity(0);
    	int rightVel = rightMaster.getSelectedSensorVelocity(0);
    	if(leftVel > maxLeft) {
    		SmartDashboard.putNumber("Left Velocity", leftVel);
    		maxLeft = leftVel;
    	}
    	if(rightVel > maxRight) {
    		SmartDashboard.putNumber("Right Velocity", rightVel);
    		maxRight = rightVel;
    	}
    	
    }
    
    public void stopPath() {
    	pathFollower.stopFollowing();
    }

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
        leftMaster.set(ControlMode.Disabled, 1);
        rightMaster.set(ControlMode.Disabled, 1);
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
