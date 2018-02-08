 package com.team2169.robot.auto;

import com.team2169.robot.Constants;
import com.team2169.robot.Robot;
import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.FieldSetup;
import com.team2169.robot.auto.modes.AutoMode;
import com.team2169.robot.auto.modes.FailureAuto;
import com.team2169.robot.auto.modes.SelfTest;
import com.team2169.robot.auto.modes.center.CLLAuto;
import com.team2169.robot.auto.modes.center.CLRAuto;
import com.team2169.robot.auto.modes.center.CRLAuto;
import com.team2169.robot.auto.modes.center.CRRAuto;
import com.team2169.robot.auto.modes.left.LLLAuto;
import com.team2169.robot.auto.modes.left.LLRAuto;
import com.team2169.robot.auto.modes.left.LRLAuto;
import com.team2169.robot.auto.modes.left.LRRAuto;
import com.team2169.robot.auto.modes.right.RLLAuto;
import com.team2169.robot.auto.modes.right.RLRAuto;
import com.team2169.robot.auto.modes.right.RRLAuto;
import com.team2169.robot.auto.modes.right.RRRAuto;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


//End users shouldn't need to touch this.


public class AutoManager {
	
	//Alliance IDs
	
		int position;
		int mode;
		int selfTestActive;
		public static String autoName;
		AutoMode auto;
		
	//Sendable Chooser Declarations

		public static SendableChooser<Integer> selfTestChooser;
		public static SendableChooser<Integer> positionChooser;
		public static SendableChooser<Integer> modeChooser;
		
	//Command Declarations
		
		SelfTest selfTest;
	
		
	public AutoManager() {
		
		positionChooser = new SendableChooser<Integer>();
		selfTestChooser = new SendableChooser<Integer>();
		modeChooser= new SendableChooser<Integer>();
		
		//Alliance Choosers
		selfTestChooser.addDefault("Normal", 0);
		selfTestChooser.addObject("Self-Test", 1);
		
	//Position Choosers
		positionChooser.addDefault("Left", -1);
		positionChooser.addObject("Center", 0);
		positionChooser.addObject("Right", 1);
		
	//Mode Choosers
		modeChooser.addDefault(Constants.defaultAutoName, 0);
		modeChooser.addObject(Constants.secondAutoName, 1);
		modeChooser.addObject(Constants.thirdAutoName, 2);

	SmartDashboard.putData("Self-Test Selector", selfTestChooser);
	SmartDashboard.putData("Field Position Selector", positionChooser);
	SmartDashboard.putData("Auto Mode Selector", modeChooser);

	}
	
	void determineField() {
	
		String gameMessage_ = Robot.fms.getGameMessage();
		
		if(gameMessage_.equals("LRL") || gameMessage_.equals("LRR")) {
			RobotStates.fieldSetup = FieldSetup.LR;
		}
		else if(gameMessage_.equals("LLL") || gameMessage_.equals("LLR")) {
			RobotStates.fieldSetup = FieldSetup.LL;
		}
		else if(gameMessage_.equals("RLL") || gameMessage_.equals("RLR")) {
			RobotStates.fieldSetup = FieldSetup.RL;
		}
		else if(gameMessage_.equals("RRL") || gameMessage_.equals("RRR")) {
			RobotStates.fieldSetup = FieldSetup.RR;
		}
		else {
			DriverStation.reportError("Failure to recieve Field Data", true);
			RobotStates.fieldSetup = FieldSetup.FAIL;
		}
		
	}
	
	
	public void runAuto() {
		
		determineField();
		selfTestActive = selfTestChooser.getSelected().intValue();
		position = positionChooser.getSelected().intValue();
		mode = modeChooser.getSelected().intValue();
		
		SmartDashboard.putString("Field Setup", RobotStates.fieldSetup.toString());
		auto.start();

	}
	
	public void autoLooping() {

		auto.looper();
		
	}
	
	//You're gonna want to minimize this
	public void setAutoMode() {
		switch(RobotStates.fieldSetup) {
		
		//FAIL Case
		case FAIL: default:
		
			auto = new FailureAuto();
			break;
		
		//Switch: Left, Scale: Left
		case LL:
			
			switch(RobotStates.startingPosition) {
			
			//Robot Pos: Center
			case CENTER: 
				auto = new CLLAuto();
				break;
			
			//Robot Pos: Left
			case LEFT:
				auto = new LLLAuto();
				break;
				
			//Robot Pos: Right
			case RIGHT:
				auto = new RLLAuto();
				break;	
				
			//FAIL Case
			default:
				auto = new FailureAuto();
				break;
			}
			
			break;
		
		//Switch: Left, Scale: Right
		case LR:

			switch(RobotStates.startingPosition) {
			
			//Robot Pos: Center
			case CENTER: 
				auto = new CLRAuto();
				break;
			
			//Robot Pos: Left
			case LEFT:
				auto = new LLRAuto();
				break;
				
			//Robot Pos: Right
			case RIGHT:
				auto = new RLRAuto();
				break;	
				
			//FAIL Case
			default:
				auto = new FailureAuto();
				break;
			}
			
			break;
		
		//Switch: Right, Scale: Left
		case RL:
			
			switch(RobotStates.startingPosition) {
			
			//Robot Pos: Center
			case CENTER: 
				auto = new CRLAuto();
				break;
			
			//Robot Pos: Left
			case LEFT:
				auto = new LRLAuto();
				break;
				
			//Robot Pos: Right
			case RIGHT:
				auto = new RRLAuto();
				break;	
				
			//FAIL Case
			default:
				auto = new FailureAuto();
				break;
			}
			
			break;
		
		//Switch: Right, Scale: Right
		case RR:

			switch(RobotStates.startingPosition) {
			
			//Robot Pos: Center
			case CENTER: 
				auto = new CRRAuto();
				break;
			
			//Robot Pos: Left
			case LEFT:
				auto = new LRRAuto();
				break;
				
			//Robot Pos: Right
			case RIGHT:
				auto = new RRRAuto();
				break;	
				
			//FAIL Case
			default:
				auto = new FailureAuto();
				break;
			}
			
			break;
		
		
		}
	}
	
}
