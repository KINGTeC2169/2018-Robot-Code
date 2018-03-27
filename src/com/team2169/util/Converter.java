package com.team2169.util;

import com.team2169.robot.Constants;

public class Converter {

    public static int inchesToTicks(double inches) {

        double val = inches * Constants.ticksPerRotation;
        return (int) Math.round(val);

    }

    public static double talonVelocityConverter(double fps, double diameter) {
    	return ((((60 * fps)/(diameter * Math.PI))*(4096))/600);
    }
    
    public static double talonPositionConverter(double rot) {
    	return (rot*4096);
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
