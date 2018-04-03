package com.team2169.robot.auto;

import com.team2169.robot.Constants;
import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.FieldSetup;
import com.team2169.robot.RobotStates.StartingPosition;
import com.team2169.robot.auto.modes.AutoMode;
import com.team2169.robot.auto.modes.FailureAuto;
import com.team2169.robot.auto.modes.PrepForMatch;
import com.team2169.robot.auto.modes.SelfTest;
import com.team2169.robot.auto.modes.scaleAutos.left.ScaleLLAuto;
import com.team2169.robot.auto.modes.scaleAutos.left.ScaleLRAuto;
import com.team2169.robot.auto.modes.scaleAutos.right.ScaleRLAuto;
import com.team2169.robot.auto.modes.scaleAutos.right.ScaleRRAuto;
import com.team2169.robot.auto.modes.switchAutos.center.SwitchCLAuto;
import com.team2169.robot.auto.modes.switchAutos.center.SwitchCRAuto;
import com.team2169.robot.auto.modes.switchAutos.left.SwitchLLAuto;
import com.team2169.robot.auto.modes.switchAutos.left.SwitchLRAuto;
import com.team2169.robot.auto.modes.switchAutos.right.SwitchRLAuto;
import com.team2169.robot.auto.modes.switchAutos.right.SwitchRRAuto;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//End users shouldn't need to touch this.

public class AutoManager {

	// Alliance IDs

	private int position;
	private int mode;
	private int selfTestActive;
	String gameMessage;
	private AutoMode auto;

	// Sendable Chooser Declarations

	private static SendableChooser<Integer> selfTestChooser;
	private static SendableChooser<Integer> positionChooser;
	private static SendableChooser<Integer> modeChooser;

	// Command Declarations

	public AutoManager() {

		positionChooser = new SendableChooser<>();
		selfTestChooser = new SendableChooser<>();
		modeChooser = new SendableChooser<>();

		// Alliance Choosers
		selfTestChooser.addDefault("Normal", 0);
		selfTestChooser.addObject("Self-Test", 1);
		selfTestChooser.addObject("Match Prep", 2);

		// Position Choosers
		positionChooser.addDefault("Left", -1);
		positionChooser.addObject("Center", 0);
		positionChooser.addObject("Right", 1);

		// Mode Choosers
		modeChooser.addDefault(Constants.defaultAutoName, 0);
		modeChooser.addObject(Constants.secondAutoName, 1);

		SmartDashboard.putData("Self-Test Selector", selfTestChooser);
		SmartDashboard.putData("Field Position Selector", positionChooser);
		SmartDashboard.putData("Auto Mode Selector", modeChooser);

	}

	private void determineField() {

		gameMessage = DriverStation.getInstance().getGameSpecificMessage();

		switch (gameMessage) {
		case "LRL":
		case "LRR":
			RobotStates.fieldSetup = FieldSetup.LR;
			SmartDashboard.putString("Switch State:", "LEFT");
			SmartDashboard.putString("Scale State:", "RIGHT");
			break;
		case "LLL":
		case "LLR":
			RobotStates.fieldSetup = FieldSetup.LL;
			SmartDashboard.putString("Switch State:", "LEFT");
			SmartDashboard.putString("Scale State:", "LEFT");
			break;
		case "RLL":
		case "RLR":
			RobotStates.fieldSetup = FieldSetup.RL;
			SmartDashboard.putString("Switch State:", "RIGHT");
			SmartDashboard.putString("Scale State:", "LEFT");
			break;
		case "RRL":
		case "RRR":
			RobotStates.fieldSetup = FieldSetup.RR;
			SmartDashboard.putString("Switch State:", "RIGHT");
			SmartDashboard.putString("Scale State:", "RIGHT");
			break;
		default:
			DriverStation.reportError("Failure to recieve Field Data", true);
			RobotStates.fieldSetup = FieldSetup.FAIL;
			break;
		}

		// Set RobotPosition based on SmartDash chooser
		if (position == -1) {
			RobotStates.startingPosition = StartingPosition.LEFT;
		} else if (position == 0) {
			RobotStates.startingPosition = StartingPosition.CENTER;
		} else if (position == 1) {
			RobotStates.startingPosition = StartingPosition.RIGHT;
		} else {
			// Default to Center because TODO come up with a reason to start in the middle
			RobotStates.startingPosition = StartingPosition.CENTER;
		}

		setAutoMode();

		SmartDashboard.putString("Robot Location:", RobotStates.startingPosition.toString());

	}

	public void runAuto() {

		selfTestActive = selfTestChooser.getSelected();
		position = positionChooser.getSelected();
		mode = modeChooser.getSelected();

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

			//Switch Autos
			if(mode == 0) {

				switch (RobotStates.fieldSetup) {

					// FAIL Case
					case FAIL:
					default:

						auto = new FailureAuto();
						break;

					// Switch: Left, Scale: Left
					case LL:

						switch (RobotStates.startingPosition) {

							// Robot Pos: Center
							case CENTER:
								auto = new SwitchCLAuto();
								break;

							// Robot Pos: Left
							case LEFT:
								auto = new SwitchLLAuto();
								break;

							// Robot Pos: Right
							case RIGHT:
								auto = new SwitchRLAuto();
								break;

							// FAIL Case
							default:
								auto = new FailureAuto();
								break;
						}

						break;

					// Switch: Left, Scale: Right
					case LR:

						switch (RobotStates.startingPosition) {

							// Robot Pos: Center
							case CENTER:
								auto = new SwitchCLAuto();
								break;

							// Robot Pos: Left
							case LEFT:
								auto = new SwitchLLAuto();
								break;

							// Robot Pos: Right
							case RIGHT:
								auto = new SwitchRLAuto();
								break;

							// FAIL Case
							default:
								auto = new FailureAuto();
								break;
						}

						break;

					// Switch: Right, Scale: Left
					case RL:

						switch (RobotStates.startingPosition) {

							// Robot Pos: Center
							case CENTER:
								auto = new SwitchCRAuto();
								break;

							// Robot Pos: Left
							case LEFT:
								auto = new SwitchLRAuto();
								break;

							// Robot Pos: Right
							case RIGHT:
								auto = new SwitchRRAuto();
								break;

							// FAIL Case
							default:
								auto = new FailureAuto();
								break;
						}

						break;

					// Switch: Right, Scale: Right
					case RR:

						switch (RobotStates.startingPosition) {

							// Robot Pos: Center
							case CENTER:
								auto = new SwitchCRAuto();
								break;

							// Robot Pos: Left
							case LEFT:
								auto = new SwitchLRAuto();
								break;

							// Robot Pos: Right
							case RIGHT:
								auto = new SwitchRRAuto();
								break;

							// FAIL Case
							default:
								auto = new FailureAuto();
								break;
						}

						break;

				}
			}

			//Scale Autos
			else if(mode == 1) {

				switch (RobotStates.fieldSetup) {

					// FAIL Case
					case FAIL:
					default:

						auto = new FailureAuto();
						break;

					// Switch: Left, Scale: Left
					case LL:

						switch (RobotStates.startingPosition) {

							// Robot Pos: Center
							case CENTER:
								auto = new SwitchCLAuto();
								break;

							// Robot Pos: Left
							case LEFT:
								auto = new ScaleLLAuto();
								break;

							// Robot Pos: Right
							case RIGHT:
								auto = new ScaleRLAuto();
								break;

							// FAIL Case
							default:
								auto = new FailureAuto();
								break;
						}

						break;

					// Switch: Left, Scale: Right
					case LR:

						switch (RobotStates.startingPosition) {

							// Robot Pos: Center
							case CENTER:
								auto = new SwitchCLAuto();
								break;

							// Robot Pos: Left
							case LEFT:
								auto = new ScaleLRAuto();
								break;

							// Robot Pos: Right
							case RIGHT:
								auto = new ScaleRRAuto();
								break;

							// FAIL Case
							default:
								auto = new FailureAuto();
								break;
						}

						break;

					// Switch: Right, Scale: Left
					case RL:

						switch (RobotStates.startingPosition) {

							// Robot Pos: Center
							case CENTER:
								auto = new SwitchCRAuto();
								break;

							// Robot Pos: Left
							case LEFT:
								auto = new ScaleLLAuto();
								break;

							// Robot Pos: Right
							case RIGHT:
								auto = new ScaleRLAuto();
								break;

							// FAIL Case
							default:
								auto = new FailureAuto();
								break;
						}

						break;

					// Switch: Right, Scale: Right
					case RR:

						switch (RobotStates.startingPosition) {

							// Robot Pos: Center
							case CENTER:
								auto = new SwitchCRAuto();
								break;

							// Robot Pos: Left
							case LEFT:
								auto = new ScaleLRAuto();
								break;

							// Robot Pos: Right
							case RIGHT:
								auto = new ScaleRRAuto();
								break;

							// FAIL Case
							default:
								auto = new FailureAuto();
								break;
						}

						break;

				}
			}
		}
	}

}
