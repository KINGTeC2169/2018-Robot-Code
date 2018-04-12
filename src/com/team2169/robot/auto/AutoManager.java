package com.team2169.robot.auto;

import com.team2169.robot.Constants;
import com.team2169.robot.auto.AutoConstants.AutoSequenceMode;
import com.team2169.robot.auto.AutoConstants.CenterPriority;
import com.team2169.robot.auto.AutoConstants.Possesion;
import com.team2169.robot.auto.AutoConstants.Preference;
import com.team2169.robot.auto.AutoConstants.RobotSide;
import com.team2169.robot.auto.AutoConstants.SidePriority;
import com.team2169.robot.auto.AutoConstants.Yield;
import com.team2169.robot.auto.modes.AutoMode;
import com.team2169.robot.auto.modes.DoNothing;
import com.team2169.robot.auto.modes.FailureAuto;
import com.team2169.robot.auto.modes.SelfTest;
import com.team2169.robot.auto.modes.driveForward.DriveForwardAuto;
import com.team2169.robot.auto.modes.oneBlock.center.SwitchAuto;
import com.team2169.robot.auto.modes.oneBlock.side.OneBlockScaleCloseAuto;
import com.team2169.robot.auto.modes.oneBlock.side.OneBlockScaleFarAuto;
import com.team2169.robot.auto.modes.oneBlock.side.SwitchCloseAuto;
import com.team2169.robot.auto.modes.twoBlock.center.TwoBlockSwitchAuto;
import com.team2169.robot.auto.modes.twoBlock.side.SwitchScaleCloseAuto;
import com.team2169.robot.auto.modes.twoBlock.side.SwitchScaleFarAuto;
import com.team2169.robot.auto.modes.twoBlock.side.TwoBlockScaleFarAuto;
import com.team2169.robot.auto.modes.twoBlock.side.TwoBlockScaleCloseAuto;
import com.team2169.util.FieldInterpreter;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//End users shouldn't need to touch this.

public class AutoManager {

	// Alliance IDs

	String gameMessage;
	private AutoMode auto;
	boolean doSwitch;
	boolean doScale;
	AutoSequenceMode autoMode;
	FieldInterpreter interpreter;
	RobotSide position;
	Preference preference;
	CenterPriority centPriority;
	SidePriority sidePriority;
	Yield yield;

	// Sendable Chooser Declarations

	private static SendableChooser<AutoSequenceMode> modeChooser;
	private static SendableChooser<RobotSide> positionChooser;
	private static SendableChooser<Preference> preferenceChooser;
	private static SendableChooser<Yield> yieldChooser;
	private static SendableChooser<CenterPriority> centerPriorityChooser;
	private static SendableChooser<SidePriority> sidePriorityChooser;
	private static SendableChooser<Boolean> doSwitchChooser;
	private static SendableChooser<Boolean> doScaleChooser;

	// Command Declarations

	public AutoManager() {

		positionChooser = new SendableChooser<>();
		modeChooser = new SendableChooser<>();
		preferenceChooser = new SendableChooser<>();
		yieldChooser = new SendableChooser<>();
		centerPriorityChooser = new SendableChooser<>();
		sidePriorityChooser = new SendableChooser<>();
		doSwitchChooser = new SendableChooser<>();
		doScaleChooser = new SendableChooser<>();

		// Alliance Choosers
		modeChooser.addDefault("Normal", AutoSequenceMode.NORMAL);
		modeChooser.addObject("Self-Test", AutoSequenceMode.SELF_TEST);
		modeChooser.addObject("Drive Forward", AutoSequenceMode.DRIVE_FORWARD);
		modeChooser.addObject("Do Nothing", AutoSequenceMode.NOTHING);

		// Position Choosers
		positionChooser.addDefault("Left", RobotSide.LEFT);
		positionChooser.addObject("Center", RobotSide.CENTER);
		positionChooser.addObject("Right", RobotSide.RIGHT);

		// Mode Choosers
		preferenceChooser.addDefault(Constants.preferenceOneName, Preference.SWITCH);
		preferenceChooser.addObject(Constants.preferenceTwoName, Preference.SCALE);

		sidePriorityChooser.addDefault("1 In Scale", SidePriority.ONE_SCALE);
		sidePriorityChooser.addObject("2 In Scale", SidePriority.TWO_SCALE);
		sidePriorityChooser.addObject("Switch and Scale - 1 Block Backup", SidePriority.SWITCH_SCALE_ONE_BLOCK);
		sidePriorityChooser.addObject("Switch and Scale - 2 Block Backup", SidePriority.SWITCH_SCALE_TWO_BLOCK);

		centerPriorityChooser.addDefault("1 Block", CenterPriority.ONE_BLOCK);
		centerPriorityChooser.addObject("2 Block", CenterPriority.TWO_BLOCK);

		yieldChooser.addDefault("None", Yield.NONE);
		yieldChooser.addObject("Far Scale", Yield.FAR_SCALE);
		yieldChooser.addObject("All Scale", Yield.ALL_SCALE);

		doSwitchChooser.addDefault("No", false);
		doSwitchChooser.addObject("Yes", true);

		doScaleChooser.addDefault("No", false);
		doScaleChooser.addObject("Yes", true);

		SmartDashboard.putData("Mode Selector", modeChooser);
		SmartDashboard.putData("Field Position Selector", positionChooser);
		SmartDashboard.putData("Auto Mode Selector", preferenceChooser);
		SmartDashboard.putData("Preference Chooser", yieldChooser);
		SmartDashboard.putData("Center Priority Chooser", centerPriorityChooser);
		SmartDashboard.putData("Side Priority Chooser", sidePriorityChooser);
		SmartDashboard.putData("Do Switch on Drive Forward", doSwitchChooser);
		SmartDashboard.putData("Do Switch-Scale on Side Switch", doScaleChooser);

	}

	private void determineField() {

		gameMessage = DriverStation.getInstance().getGameSpecificMessage();

		interpreter = new FieldInterpreter(gameMessage, position);

		setAutoMode();

		SmartDashboard.putString("Robot Location:", position.name());

	}

	public void runAuto() {

		autoMode = modeChooser.getSelected();
		position = positionChooser.getSelected();
		preference = preferenceChooser.getSelected();
		yield = yieldChooser.getSelected();
		centPriority = centerPriorityChooser.getSelected();
		sidePriority = sidePriorityChooser.getSelected();
		doSwitch = doSwitchChooser.getSelected();
		doScale = doScaleChooser.getSelected();

		determineField();

		SmartDashboard.putString("Field ID", gameMessage);

		// auto = new DriveForwardAuto();

		auto.printName();
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

	// I actually commented this bit because it is hard to follow.
	private void setAutoMode() {

		if (autoMode == AutoSequenceMode.SELF_TEST) {
			auto = new SelfTest();
		} else if (autoMode == AutoSequenceMode.DRIVE_FORWARD) {
			auto = new DriveForwardAuto();
		} else if (autoMode == AutoSequenceMode.NOTHING) {
			auto = new DoNothing();
		} else if (autoMode == AutoSequenceMode.NORMAL) {

			// Run Center Autos
			if (position == RobotSide.CENTER) {
				if (centPriority == CenterPriority.ONE_BLOCK) {
					auto = new SwitchAuto(interpreter.nearSwitchSide);
				} else if (centPriority == CenterPriority.TWO_BLOCK) {
					auto = new TwoBlockSwitchAuto(interpreter.nearSwitchSide);
				} else {
					auto = new FailureAuto();
				}
			}

			// Run Side Autos
			else {

				// Switch Autos
				if (preference == Preference.SWITCH) {
					// Check if we have the switch
					if (interpreter.nearSwitchPos == Possesion.CLOSE) {
						// Check if we can/should do the scale as well
						if (doScale && yield != Yield.ALL_SCALE && interpreter.nearSwitchPos == Possesion.CLOSE) {
							auto = new SwitchScaleCloseAuto(position);
						}
						// We shouldn't/can't do the scale as well
						else {
							auto = new SwitchCloseAuto(position);
						}

					}
				}
				// We don't have the switch
				else if (interpreter.nearSwitchPos == Possesion.FAR) {
					// Check the yield to see if we can access the scale and check if we have the
					// scale
					if (doScale) {
						// We have the scale and we have clearance to use it
						if (interpreter.scalePos == Possesion.CLOSE && yield != Yield.ALL_SCALE) {
							
							// Check if drivers have asked for a switch-scale auto
							if (sidePriority == SidePriority.SWITCH_SCALE_ONE_BLOCK
									|| sidePriority == SidePriority.SWITCH_SCALE_TWO_BLOCK) {
								
								//If they have, set priority to the backup
								if (sidePriority == SidePriority.SWITCH_SCALE_ONE_BLOCK) {
									sidePriority = SidePriority.ONE_SCALE;
								} else if (sidePriority == SidePriority.SWITCH_SCALE_TWO_BLOCK) {
									sidePriority = SidePriority.TWO_SCALE;
								}
							}
							
							// Check if priority is One Block Scale auto, and run that.
							if (sidePriority == SidePriority.ONE_SCALE) {
								auto = new OneBlockScaleCloseAuto(position);
							}

							// Check if priority is Two Block Scale auto, and run that.
							else if (sidePriority == SidePriority.TWO_SCALE) {
								auto = new TwoBlockScaleCloseAuto(position);
							}
						
						}

						// We don't have the scale but we still have clearance to use it
						// Run Far Scale autos
						else if (interpreter.scalePos == Possesion.FAR && yield == Yield.NONE) {

							// Check if drivers have asked for a switch-scale auto
							if (sidePriority == SidePriority.SWITCH_SCALE_ONE_BLOCK
									|| sidePriority == SidePriority.SWITCH_SCALE_TWO_BLOCK) {
								// Run a SwitchScaleFar
								auto = new SwitchScaleFarAuto(position);
							}
							
							// Check if priority is One Block Scale auto, and run that.
							else if (sidePriority == SidePriority.ONE_SCALE) {
								auto = new OneBlockScaleFarAuto(position);
							}

							// Check if priority is Two Block Scale auto, and run that.
							else if (sidePriority == SidePriority.TWO_SCALE) {
								auto = new TwoBlockScaleFarAuto(position);
							}

						}

						
					}
				} else {
					// We are yielded against using the scale, and we don't have the switch. Drive
					// forward.
					auto = new DriveForwardAuto();
				}
			}
		}

		// Scale Autos
		else if (preference == Preference.SCALE) {

			// Close Scale Handler
			if (interpreter.scalePos == Possesion.CLOSE) {
				// Check who we are yielding to, if we have access to the scale, carry on.
				if (yield == Yield.FAR_SCALE || yield == Yield.NONE) {
					// Check if drivers have asked for a switch-scale auto
					if (sidePriority == SidePriority.SWITCH_SCALE_ONE_BLOCK
							|| sidePriority == SidePriority.SWITCH_SCALE_TWO_BLOCK) {
						// Check if we have the opportunity to run a switch-scale
						if (interpreter.nearSwitchPos == Possesion.CLOSE) {
							// If we can, set that as our auto
							auto = new SwitchScaleCloseAuto(position);
						}

						// We can't run a switch scale auto, so set the priority to the backup plan
						else {
							if (sidePriority == SidePriority.SWITCH_SCALE_ONE_BLOCK) {
								sidePriority = SidePriority.ONE_SCALE;
							} else if (sidePriority == SidePriority.SWITCH_SCALE_TWO_BLOCK) {
								sidePriority = SidePriority.TWO_SCALE;
							}
						}
					}

					// Check if priority is One Block Scale auto, and run that.
					if (sidePriority == SidePriority.ONE_SCALE) {
						auto = new OneBlockScaleCloseAuto(position);
					}

					// Check if priority is Two Block Scale auto, and run that.
					else if (sidePriority == SidePriority.TWO_SCALE) {
						auto = new TwoBlockScaleCloseAuto(position);
					}
				}

				// We have yields preventing us from using the scale
				else if (yield == Yield.ALL_SCALE) {
					// Check if we can/should drop it in the switch
					if (doSwitch && interpreter.nearSwitchPos == Possesion.CLOSE) {
						auto = new SwitchCloseAuto(position);
					}
					// We don't want to drop it in, drive forward.
					else {
						auto = new DriveForwardAuto();
					}
				}
			}

			// Far Scale Handler
			else if (interpreter.scalePos == Possesion.FAR) {
				// Check who we are yielding to, if we have access to the scale, carry on.
				if (yield == Yield.NONE) {
					// Check if drivers have asked for a switch-scale auto
					if (sidePriority == SidePriority.SWITCH_SCALE_ONE_BLOCK
							|| sidePriority == SidePriority.SWITCH_SCALE_TWO_BLOCK) {
						// Check if we have the opportunity to run a switch-scale
						if (interpreter.nearSwitchPos == Possesion.FAR) {
							// If we can, set that as our auto
							auto = new SwitchScaleFarAuto(position);
						}

						// We can't run a switch scale auto, so set the priority to the backup plan
						else {
							if (sidePriority == SidePriority.SWITCH_SCALE_ONE_BLOCK) {
								sidePriority = SidePriority.ONE_SCALE;
							} else if (sidePriority == SidePriority.SWITCH_SCALE_TWO_BLOCK) {
								sidePriority = SidePriority.TWO_SCALE;
							}
						}
					}

					// Check if priority is One Block Scale auto, and run that.
					if (sidePriority == SidePriority.ONE_SCALE) {
						auto = new OneBlockScaleFarAuto(position);
					}

					// Check if priority is Two Block Scale auto, and run that.
					else if (sidePriority == SidePriority.TWO_SCALE) {
						auto = new TwoBlockScaleFarAuto(position);
					}
				}

				// We have yields preventing us from using the scale
				else if (yield == Yield.FAR_SCALE || yield == Yield.ALL_SCALE) {
					// Check if we can/should drop it in the switch
					if (doSwitch && interpreter.nearSwitchPos == Possesion.CLOSE) {
						auto = new SwitchCloseAuto(position);
					}
					// We don't want to drop it in, drive forward.
					else {
						auto = new DriveForwardAuto();
					}
				}
			}
		} else {
			auto = new FailureAuto();
		}
	}
}