package com.team2169.robot.auto;

import com.team2169.robot.Constants;
import com.team2169.robot.Robot;
import com.team2169.robot.RobotStates;
import com.team2169.robot.RobotStates.FieldSetup;
import com.team2169.robot.RobotStates.StartingPosition;
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
import com.team2169.robot.auto.modes.pathfinderAutos.*;
import com.team2169.robot.auto.modes.right.RLLAuto;
import com.team2169.robot.auto.modes.right.RLRAuto;
import com.team2169.robot.auto.modes.right.RRLAuto;
import com.team2169.robot.auto.modes.right.RRRAuto;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//End users shouldn't need to touch this.

public class AutoManager {

    // Alliance IDs

    private int position;
    private int mode;
    private int selfTestActive;
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

        String gameMessage_ = Robot.fms.getGameMessage();

        switch (gameMessage_) {
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
        //AutoMode from smartDash Choser
        if (mode == 0) {
            RobotStates.autoMode = RobotStates.AutoMode.CONTINUOUS;
        } else if (mode == 1) {
            RobotStates.autoMode = RobotStates.AutoMode.SEGMENTED;
        }

        setAutoMode();

        SmartDashboard.putString("Robot Location:", RobotStates.startingPosition.toString());

    }

    public void runAuto() {

        selfTestActive = selfTestChooser.getSelected();
        position = positionChooser.getSelected();
        mode = modeChooser.getSelected();

        determineField();

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
        } else {
            switch (RobotStates.autoMode) {
                case CONTINUOUS:
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
                                    auto = new CLLAutoCont();
                                    break;

                                // Robot Pos: Left
                                case LEFT:
                                    auto = new LLLAutoCont();
                                    break;

                                // Robot Pos: Right
                                case RIGHT:
                                    auto = new RLLAutoCont();
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
                                    auto = new CLRAutoCont();
                                    break;

                                // Robot Pos: Left
                                case LEFT:
                                    auto = new LLRAutoCont();
                                    break;

                                // Robot Pos: Right
                                case RIGHT:
                                    auto = new RLRAutoCont();
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
                                    auto = new CRLAutoCont();
                                    break;

                                // Robot Pos: Left
                                case LEFT:
                                    auto = new LRLAutoCont();
                                    break;

                                // Robot Pos: Right
                                case RIGHT:
                                    auto = new RRLAutoCont();
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
                                    auto = new CRRAutoCont();
                                    break;

                                // Robot Pos: Left
                                case LEFT:
                                    auto = new LRRAutoCont();
                                    break;

                                // Robot Pos: Right
                                case RIGHT:
                                    auto = new RRRAutoCont();
                                    break;

                                // FAIL Case
                                default:
                                    auto = new FailureAuto();
                                    break;
                            }

                            break;

                    }
                    break;

                case SEGMENTED:
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
                                    auto = new CLLAuto();
                                    break;

                                // Robot Pos: Left
                                case LEFT:
                                    auto = new LLLAuto();
                                    break;

                                // Robot Pos: Right
                                case RIGHT:
                                    auto = new RLLAuto();
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
                                    auto = new CLRAuto();
                                    break;

                                // Robot Pos: Left
                                case LEFT:
                                    auto = new LLRAuto();
                                    break;

                                // Robot Pos: Right
                                case RIGHT:
                                    auto = new RLRAuto();
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
                                    auto = new CRLAuto();
                                    break;

                                // Robot Pos: Left
                                case LEFT:
                                    auto = new LRLAuto();
                                    break;

                                // Robot Pos: Right
                                case RIGHT:
                                    auto = new RRLAuto();
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
                                    auto = new CRRAuto();
                                    break;

                                // Robot Pos: Left
                                case LEFT:
                                    auto = new LRRAuto();
                                    break;

                                // Robot Pos: Right
                                case RIGHT:
                                    auto = new RRRAuto();
                                    break;

                                // FAIL Case
                                default:
                                    auto = new FailureAuto();
                                    break;
                            }

                            break;

                    }
                    break;
            }
        }
    }

}
