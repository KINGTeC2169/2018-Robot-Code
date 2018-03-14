package com.team2169.util;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class TalonMaker {

    public static class TalonConfig {

        public int timeoutMs = 10;
        public int pidLoopIDx = 0;
        public int slotIDx = 0;
        boolean inverted = false;
        public double p;
        public double i;
        public double d;
        public double f;
        public int allowedError;
        public boolean sensorPhase;
        public boolean zero = true;
        public FeedbackDevice encoderType = FeedbackDevice.CTRE_MagEncoder_Absolute;

    }

    public static TalonSRX prepTalonForMotionProfiling(TalonSRX talon_, TalonConfig config) {

        // Elevator Height Configuration
        /* first choose the sensor */

        talon_.configSelectedFeedbackSensor(config.encoderType, config.pidLoopIDx, config.timeoutMs);

        talon_.setInverted(config.inverted);

        talon_.setSensorPhase(config.sensorPhase);
        talon_.setInverted(false);

		/* Set relevant frame periods to be at least as fast as periodic rate */
        talon_.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, config.timeoutMs);
        talon_.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, config.timeoutMs);

		/* set the peak and nominal outputs */
        talon_.configNominalOutputForward(0, config.timeoutMs);
        talon_.configNominalOutputReverse(0, config.timeoutMs);
        talon_.configPeakOutputForward(1, config.timeoutMs);
        talon_.configPeakOutputReverse(-1, config.timeoutMs);

		/* set closed loop gains in slot0 - see documentation */
        talon_.selectProfileSlot(config.slotIDx, config.pidLoopIDx);
        talon_.config_kF(config.slotIDx, config.f, config.timeoutMs);
        talon_.config_kP(config.slotIDx, config.p, config.timeoutMs);
        talon_.config_kI(config.slotIDx, config.i, config.timeoutMs);
        talon_.config_kD(config.slotIDx, config.d, config.timeoutMs);

		/* zero the sensor */
        if (config.zero) {
            talon_.setSelectedSensorPosition(0, config.pidLoopIDx, config.timeoutMs);
        }
		
		/* return the updated Talon*/
        return talon_;

    }

}
