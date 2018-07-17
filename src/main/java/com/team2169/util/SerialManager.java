package com.team2169.util;

import java.util.ArrayList;

import com.team2169.util.ColorData.Color;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;

public class SerialManager {

	static ArrayList<Color> colors = new ArrayList<Color>();
	
	public static void initializeColors() {
		
	}
	
	// uses the i2c port on the RoboRIO
	// uses address 4, must match arduino
	private static I2C Wire = new I2C(Port.kOnboard, 4);
	
	private static final int MAX_BYTES = 32;

	//Function to write strings to the arduino
	public static void write(String input) {
		//Create a char array from the input string
		char[] CharArray = input.toCharArray();
		//Create a byte array from the char array
		byte[] WriteData = new byte[CharArray.length];
		//Write each byte to the arduino
		for (int i = 0; i < CharArray.length; i++) {
			//Add the char elements to the byte array
			WriteData[i] = (byte) CharArray[i];
		}
		//Send each byte to arduino
		Wire.transaction(WriteData, WriteData.length, null, 0);

	}
	
	//Function to read the data from arduino
	public static String read(){
		
		//Create a byte array to hold the incoming data
		byte[] data = new byte[MAX_BYTES];
		//Use address 4 on i2c and store it in data
		Wire.read(4, MAX_BYTES, data);
		//Create a string from the byte array
		String output = new String(data);
		//Create an indexed integer of the data
		int pt = output.indexOf((char)255);
		//Return the subsequence of the string, cutting out any trash data and returning info from the indexed integer.
		return (String) output.subSequence(0, pt < 0 ? 0 : pt);
}

}
