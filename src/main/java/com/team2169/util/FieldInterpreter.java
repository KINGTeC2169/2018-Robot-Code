package com.team2169.util;

import com.team2169.robot.auto.AutoConstants.ElementSide;
import com.team2169.robot.auto.AutoConstants.Possesion;
import com.team2169.robot.auto.AutoConstants.RobotSide;

public class FieldInterpreter {

	String fieldMessage;
	public ElementSide nearSwitchSide;
	public ElementSide scaleSide;
	public ElementSide farSwitchSide;
	public Possesion nearSwitchPos;
	public Possesion scalePos;
	public Possesion farSwitchPos;

	public FieldInterpreter(String fieldMessage_, RobotSide side) {

		fieldMessage = fieldMessage_;
		determinePositions();
		determinePossesions(side);

	}

	void determineNearSwitchPosition() {
		if (fieldMessage.startsWith("L")) {
			nearSwitchSide = ElementSide.LEFT;
		} else if (fieldMessage.startsWith("R")) {
			nearSwitchSide = ElementSide.RIGHT;
		} else {
			nearSwitchSide = ElementSide.ERROR;
		}
	}

	void determineFarSwitchPosition() {
		if (fieldMessage.endsWith("L")) {
			farSwitchSide = ElementSide.LEFT;
		} else if (fieldMessage.endsWith("R")) {
			farSwitchSide = ElementSide.RIGHT;
		} else {
			farSwitchSide = ElementSide.ERROR;
		}
	}

	void determineScalePosition() {
		if (fieldMessage.substring(1).startsWith("L")) {
			scaleSide = ElementSide.LEFT;
		} else if (fieldMessage.substring(1).startsWith("R")) {
			scaleSide = ElementSide.RIGHT;
		} else {
			scaleSide = ElementSide.ERROR;
		}
	}

	void determinePositions() {
		determineNearSwitchPosition();
		determineFarSwitchPosition();
		determineScalePosition();
	}

	void determineNearSwitchPossesion(RobotSide side) {

		if(side == RobotSide.LEFT) {
			if(nearSwitchSide == ElementSide.LEFT) {
				nearSwitchPos = Possesion.CLOSE;
			}
			else if (nearSwitchSide == ElementSide.RIGHT){
				nearSwitchPos = Possesion.FAR;
			}
			else {
				nearSwitchPos = Possesion.ERROR;
			}
		}
		else if(side == RobotSide.RIGHT) {
			if(nearSwitchSide == ElementSide.RIGHT) {
				nearSwitchPos = Possesion.CLOSE;
			}
			else if (nearSwitchSide == ElementSide.LEFT){
				nearSwitchPos = Possesion.FAR;
			}
			else {
				nearSwitchPos = Possesion.ERROR;
			}
		}
		else if(side == RobotSide.CENTER){
			nearSwitchPos = Possesion.CENTER;
		}
		else {
			nearSwitchPos = Possesion.ERROR;
		}
		
	}

	void determineFarSwitchPossesion(RobotSide side) {

		if(side == RobotSide.LEFT) {
			if(farSwitchSide == ElementSide.LEFT) {
				farSwitchPos = Possesion.CLOSE;
			}
			else if (farSwitchSide == ElementSide.RIGHT){
				farSwitchPos = Possesion.FAR;
			}
			else {
				farSwitchPos = Possesion.ERROR;
			}
		}
		else if(side == RobotSide.RIGHT) {
			if(farSwitchSide == ElementSide.RIGHT) {
				farSwitchPos = Possesion.CLOSE;
			}
			else if (farSwitchSide == ElementSide.LEFT){
				farSwitchPos = Possesion.FAR;
			}
			else {
				farSwitchPos = Possesion.ERROR;
			}
		}
		else if(side == RobotSide.CENTER){
			farSwitchPos = Possesion.CENTER;
		}
		else {
			farSwitchPos = Possesion.ERROR;
		}
		
	}
	
	void determineScalePossesion(RobotSide side) {

		if(side == RobotSide.LEFT) {
			if(scaleSide == ElementSide.LEFT) {
				scalePos = Possesion.CLOSE;
			}
			else if (scaleSide == ElementSide.RIGHT){
				scalePos = Possesion.FAR;
			}
			else {
				scalePos = Possesion.ERROR;
			}
		}
		else if(side == RobotSide.RIGHT) {
			if(scaleSide == ElementSide.RIGHT) {
				scalePos = Possesion.CLOSE;
			}
			else if (scaleSide == ElementSide.LEFT){
				scalePos = Possesion.FAR;
			}
			else {
				scalePos = Possesion.ERROR;
			}
		}
		else if(side == RobotSide.CENTER){
			scalePos = Possesion.CENTER;
		}
		else {
			scalePos = Possesion.ERROR;
		}
		
	}
	
	void printPossesions() {
		System.out.println("Near Switch Pos: " + nearSwitchPos);
		System.out.println("Scale Pos: " + scalePos);
		System.out.println("Far Switch Pos: " + farSwitchPos);
	}

	void determinePossesions(RobotSide side) {
		determineNearSwitchPossesion(side);
		determineScalePossesion(side);
		determineFarSwitchPossesion(side);
	}
	
	void printPositions() {
		System.out.println("Near Switch: " + nearSwitchSide.name());
		System.out.println("Scale : " + scaleSide.name());
		System.out.println("Far Switch: " + farSwitchSide.name());
	}
}
