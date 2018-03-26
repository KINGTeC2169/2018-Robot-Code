package com.team2169.util;

import com.team2169.robot.Constants;

public class Converter {

    public static int inchesToTicks(double inches) {

        double val = inches * Constants.ticksPerRotation;
        return (int) Math.round(val);

    }

    public static double fpsToRPS(double fps, double diameter) {
    	return (fps * 12)/(diameter *  Math.PI);
    }
    
    public static int feetToTicks(double feet) {

        double val = feet * 12 * Constants.ticksPerRotation;
        return (int) Math.round(val);

    }

    public static int winchInchesToTicks(double inches, double drumDiameterIN) {

        double val = (inches * .5 * Constants.ticksPerRotation) / (drumDiameterIN * Math.PI);
        return (int) Math.round(val);

    }

    public static int winchFeetToTicks(double feet, double drumDiameterIN) {

        double val = (feet * 12 * .5 * Constants.ticksPerRotation) / (drumDiameterIN * Math.PI);
        return (int) Math.round(val);

    }

}
