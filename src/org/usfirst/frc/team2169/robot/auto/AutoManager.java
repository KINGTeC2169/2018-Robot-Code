 package org.usfirst.frc.team2169.robot.auto;

import org.usfirst.frc.team2169.robot.Constants;
import org.usfirst.frc.team2169.robot.auto.modes.BlueCenterAuto;
import org.usfirst.frc.team2169.robot.auto.modes.BlueLeftAuto;
import org.usfirst.frc.team2169.robot.auto.modes.BlueRightAuto;
import org.usfirst.frc.team2169.robot.auto.modes.RedCenterAuto;
import org.usfirst.frc.team2169.robot.auto.modes.RedLeftAuto;
import org.usfirst.frc.team2169.robot.auto.modes.RedRightAuto;
import org.usfirst.frc.team2169.robot.auto.modes.SelfTest;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


//End users shouldn't need to touch this.


public class AutoManager {
	
	//Alliance IDs
	
		int alliance;
		int position;
		int mode;
		public static String autoName;
		public static String gameMessage;
		
	//Sendable Chooser Declarations
		
		public static SendableChooser<Integer> allianceChooser;
		public static SendableChooser<Integer> positionChooser;
		public static SendableChooser<Integer> modeChooser;
		
	//Command Declarations
		
		BlueLeftAuto bLAuto;
		BlueCenterAuto bCAuto;
		BlueRightAuto bRAuto;
		RedLeftAuto rLAuto;
		RedCenterAuto rCAuto;
		RedRightAuto rRAuto;
		SelfTest selfTest;
		
	
	
	public AutoManager() {
		
		
		positionChooser = new SendableChooser<Integer>();
		allianceChooser = new SendableChooser<Integer>();
		modeChooser= new SendableChooser<Integer>();
		
		//Alliance Choosers
		allianceChooser.addDefault("Self-Test", 0);
		allianceChooser.addObject("Blue Alliance", 1);
		allianceChooser.addObject("Red Alliance", 2);
		
	//Position Choosers
		positionChooser.addDefault("Left", -1);
		positionChooser.addObject("Center", 0);
		positionChooser.addObject("Right", 1);
		
	//Mode Choosers
		modeChooser.addDefault(Constants.defaultAutoName, 0);
		modeChooser.addObject(Constants.secondAutoName, 1);
		modeChooser.addObject(Constants.thirdAutoName, 2);

	SmartDashboard.putData("Alliance Selector", allianceChooser);
	SmartDashboard.putData("Field Position Selector", positionChooser);
	SmartDashboard.putData("Auto Mode Selector", modeChooser);

	}
	
	public void runAuto(String gameMessage_) {

		gameMessage = gameMessage_;
		alliance = allianceChooser.getSelected().intValue();
		position = positionChooser.getSelected().intValue();
		mode = modeChooser.getSelected().intValue();
		

		//Self Test
		if(alliance == 0) {
			
			
			selfTest = new SelfTest();
			autoName = "Self Test";
			selfTest.start();
			System.out.println("Auto Complete");
		}
		
		//Blue Alliance
		else if(alliance == 1) {
			
			
			autoName = "Blue";
			
			
			if(position == -1) {
			
				//Blue Left Auto
				bLAuto = new BlueLeftAuto();
				autoName += " Left";
				
				bLAuto.selectMode(mode);
				autoName += getModeName(mode);
				System.out.println(autoName + " Selected");
				System.out.println("Starting " + autoName);
				bLAuto.start();
				System.out.println("Auto Complete");
			
			}
			
			else if(position == 0) {
				
				//Blue Center Auto
				bCAuto = new BlueCenterAuto();
				autoName += " Center";
				
				bCAuto.selectMode(mode);
				autoName += getModeName(mode);
				System.out.println(autoName + " Selected");
				System.out.println("Starting " + autoName);
				bCAuto.start();
				System.out.println("Auto Complete");
				
			}
			
			else if(position == 1) {
			
				//Blue Right Auto
				bRAuto = new BlueRightAuto();
				autoName += " Right";
				
				bRAuto.selectMode(mode);
				autoName += getModeName(mode);
				System.out.println(autoName + " Selected");
				System.out.println("Starting " + autoName);
				bRAuto.start();
				System.out.println("Auto Complete");
			
			}
			else {
				
				//Failure
				System.out.println("ERROR: Sendable Chooser has failed to select position");
				
			}
			
		}
		
		//Red Alliance
		else if(alliance == 2) {

			autoName = "Red";
			
			if(position == -1) {
				
				//Red Left Auto
				rLAuto = new RedLeftAuto();
				autoName += " Left";
				autoName += getModeName(mode);
				rLAuto.selectMode(mode);
				System.out.println(autoName + " Selected");
				System.out.println("Starting " + autoName);
				rLAuto.start();
				System.out.println("Auto Complete");
			
			}
			
			else if(position == 0) {
				
				//Red Center Auto
				rCAuto = new RedCenterAuto();
				autoName += " Center";
				autoName += getModeName(mode);
				rCAuto.selectMode(mode);
				System.out.println(autoName + " Selected");
				System.out.println("Starting " + autoName);
				rCAuto.start();
				System.out.println("Auto Complete");
				
				
			}
			
			else if(position == 1) {
			
				//Red Right Auto
				rRAuto = new RedRightAuto();
				autoName += " Right";
				autoName += getModeName(mode);
				rRAuto.selectMode(mode);				
				System.out.println(autoName + " Selected");
				System.out.println("Starting " + autoName);
				rRAuto.start();
				System.out.println("Auto Complete");
			}
			else {
				
				//Failure
				System.out.println("ERROR: Sendable Chooser has failed to select position");
				
			}
			
		}
		else {
			
			//Failure
			System.out.println("ERROR: Sendable Chooser has failed to select alliance");
	
		}	
		
		
		
		
	}
	
	public void autoLooping() {
		
		if(alliance == 0) {
			selfTest.looper();
		}
		
		else if(alliance == 1) {
			
			if(position == -1) {
			
				//Blue Left Auto Looper
				bLAuto.looper();
			
			}
			
			else if(position == 0) {
				
				//Blue Center Auto Looper
				bCAuto.looper();
				
			}
			
			else if(position == 1) {
			
				//Blue Right Auto Looper
				bRAuto.looper();
			
			}
			else {
				
				//Failure
				
			}
			
		}
		
		else if(alliance == 2) {

			if(position == -1) {
				
				//Red Left Auto Looper
				rLAuto.looper();
			
			}
			
			else if(position == 0) {
				
				//Red Center Auto Looper
				rCAuto.looper();
				
				
			}
			
			else if(position == 1) {
			
				//Red Right Auto Looper
				rRAuto.looper();
			}	
			else {
				
				//Failure
				
			}
			
		}
		else {
			
			//Failure
			
		}
		
		
	}

	public String getModeName(int mode) {
		
		if(mode == 0) {
			return " " + Constants.defaultAutoName;
		}
		
		else if(mode == 1) {
			return " "+ Constants.secondAutoName;
		}
		
		else if(mode == 2) {
			return " "+Constants.thirdAutoName;
		}
		
		else {
			return null;
		}
	}
	
}
