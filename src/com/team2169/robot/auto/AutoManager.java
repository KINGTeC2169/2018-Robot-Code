package com.team2169.robot.auto;

import com.team2169.robot.Constants;
import com.team2169.robot.RobotStates;
import com.team2169.robot.auto.AutoConstants.Possesion;
import com.team2169.robot.auto.AutoConstants.Preference;
import com.team2169.robot.auto.AutoConstants.RobotSide;
import com.team2169.robot.auto.AutoConstants.Yield;
import com.team2169.robot.auto.modes.AutoMode;
import com.team2169.robot.auto.modes.DriveForwardAuto;
import com.team2169.robot.auto.modes.FailureAuto;
import com.team2169.robot.auto.modes.PrepForMatch;
import com.team2169.robot.auto.modes.SelfTest;
import com.team2169.util.FieldInterpreter;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//End users shouldn't need to touch this.

public class AutoManager {

	// Alliance IDs

	private int selfTestActive;
	String gameMessage;
	private AutoMode auto;
	FieldInterpreter interpreter;
	RobotSide position;
	Preference preference;
	Yield yield;

	// Sendable Chooser Declarations

	private static SendableChooser<Integer> selfTestChooser;
	private static SendableChooser<RobotSide> positionChooser;
	private static SendableChooser<Preference> preferenceChooser;
	private static SendableChooser<Yield> yieldChooser;

	// Command Declarations

	public AutoManager() {

		positionChooser = new SendableChooser<>();
		selfTestChooser = new SendableChooser<>();
		preferenceChooser = new SendableChooser<>();
		yieldChooser = new SendableChooser<>();

		// Alliance Choosers
		selfTestChooser.addDefault("Normal", 0);
		selfTestChooser.addObject("Self-Test", 1);
		selfTestChooser.addObject("Match Prep", 2);

		// Position Choosers
		positionChooser.addDefault("Left", RobotSide.LEFT);
		positionChooser.addObject("Center", RobotSide.CENTER);
		positionChooser.addObject("Right", RobotSide.RIGHT);

		// Mode Choosers
		preferenceChooser.addDefault(Constants.preferenceOneName, Preference.SWITCH);
		preferenceChooser.addObject(Constants.preferenceTwoName, Preference.SCALE);
		
		yieldChooser.addDefault("None", Yield.NONE);
		yieldChooser.addObject("Far Scale", Yield.FAR_SCALE);
		yieldChooser.addObject("All Scale", Yield.ALL_SCALE);
		
		SmartDashboard.putData("Self-Test Selector", selfTestChooser);
		SmartDashboard.putData("Field Position Selector", positionChooser);
		SmartDashboard.putData("Auto Mode Selector", preferenceChooser);

	}

	private void determineField() {

		gameMessage = DriverStation.getInstance().getGameSpecificMessage();

		interpreter = new FieldInterpreter(gameMessage, position);

		setAutoMode();

		SmartDashboard.putString("Robot Location:", RobotStates.startingPosition.toString());

	}

	public void runAuto() {

		selfTestActive = selfTestChooser.getSelected();
		position = positionChooser.getSelected();
		preference = preferenceChooser.getSelected();
		yield = yieldChooser.getSelected();

		determineField();

		SmartDashboard.putString("Field ID", gameMessage);

		auto.start();

	}

	public void endAuto() {
		if (auto != null) {
			auto.cancel();
		}
	}

	public void autoLooping() {

		auto.looper();

	}

	// You're gonna want to minimize this pal
	private void setAutoMode() {

		if (selfTestActive == 1) {
			auto = new SelfTest();
		} else if (selfTestActive == 2) {
			auto = new PrepForMatch();
		} else {

			// Switch Autos
			//Branch 0
			if (preference == Preference.SWITCH) {
				
				//Branch 1
				if(interpreter.nearSwitchPos == Possesion.CLOSE) {
					//Run Our Side Switch
				}
				//Branch 1
				else if(interpreter.nearSwitchPos == Possesion.FAR) {
					//Branch 2
					if(interpreter.scalePos == Possesion.CLOSE) {
						//Branch 3
						if(yield == Yield.NONE || yield == Yield.FAR_SCALE) {
							//Run Our Side Scale
						}
						else if(yield == Yield.ALL_SCALE) {
							auto = new DriveForwardAuto();
						}
						else {
							auto = new FailureAuto();
						}
					}
					//Branch 2
					else if(interpreter.scalePos == Possesion.FAR) {
						//Branch 3
						if(yield == Yield.NONE) {
							//Run Opposite Side Scale
						}
						//Branch 3
						else if(yield == Yield.ALL_SCALE || yield == Yield.FAR_SCALE) {
							auto = new DriveForwardAuto();
						}
						else {
							auto = new FailureAuto();
						}
					}
					else {
						auto = new FailureAuto();
					}
				}
				else {
					auto = new FailureAuto();
				}
				
			}

			// Scale Autos
			//Branch 0
			else if (preference == Preference.SCALE) {
				if(interpreter.scalePos == Possesion.CLOSE) {
					if(yield == Yield.FAR_SCALE || yield == Yield.NONE) {
						//Run Our Scale
					}
					else if(yield == Yield.ALL_SCALE) {
						if(interpreter.nearSwitchPos == Possesion.CLOSE) {
							//Run Close Switch
						}
						else if(interpreter.nearSwitchPos == Possesion.FAR) {
							auto = new DriveForwardAuto();
						}
						else {
							auto = new FailureAuto();
						}
					}
					else {
						auto = new FailureAuto();
					}
				}
				else if(interpreter.scalePos == Possesion.FAR) {
					if(yield == Yield.NONE) {
						//Run Opposite Scale
					}
					else if(yield == Yield.FAR_SCALE || yield == Yield.ALL_SCALE) {
						if(interpreter.nearSwitchPos == Possesion.CLOSE) {
							//Run Close Switch
						}
						else if(interpreter.nearSwitchPos == Possesion.FAR) {
							auto = new DriveForwardAuto();
						}
						else {
							auto = new FailureAuto();
						}
					}
					else {
						auto = new FailureAuto();
					}
				}
			}
			else {
				auto = new FailureAuto();
			}
		}
	}
}
