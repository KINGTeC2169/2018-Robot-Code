package com.team2169.util;

import com.team2169.robot.Constants;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;

public class CameraManager {

    public void startCameraServer(boolean camera1Active, boolean camera2Active, boolean camera3Active) {

        // Check for naming conflictions, break if they exist
        if (checkConflictions()) {

            if (camera1Active) {
                new Thread(() -> {

                    UsbCamera camera1 = CameraServer.getInstance().startAutomaticCapture(Constants.camera1Name,
                            Constants.camera1ID);
                    camera1.setResolution(Constants.camera1Width, Constants.camera1Height);
                    camera1.setFPS(Constants.camera1FPS);
                    camera1.setWhiteBalanceAuto();

                }).start();
            }

            if (camera2Active) {

                try {
                    new Thread(() -> {

                        UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(Constants.camera2Name,
                                Constants.camera2ID);
                        camera.setResolution(Constants.camera2Width, Constants.camera2Height);
                        camera.setFPS(Constants.camera2FPS);
                        camera.setWhiteBalanceAuto();

                    }).start();
                } catch (Exception e) {
                    DriverStation.reportError(Constants.camera2Name + " not found!", false);
                }
            }

            if (camera3Active) {
                try {
                    new Thread(() -> {

                        UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(Constants.camera3Name,
                                Constants.camera3ID);
                        camera.setResolution(Constants.camera3Width, Constants.camera3Height);
                        camera.setFPS(Constants.camera3FPS);
                        camera.setWhiteBalanceAuto();

                    }).start();
                } catch (Exception e) {
                    DriverStation.reportError(Constants.camera3Name + " not found!", false);
                }
            }
        }
    }

    @SuppressWarnings("unused")
    private boolean checkConflictions() {

        if (Constants.camera2ID == Constants.camera1ID) {

            DriverStation.reportError("Camera Error: Naming Confliction between " + Constants.camera1Name + " ID ("
                            + Constants.camera1ID + ") and " + Constants.camera2Name + " ID (" + Constants.camera2ID + ")",
                    false);
            return false;
        }

        if (Constants.camera1ID == Constants.camera3ID) {

            DriverStation.reportError("Camera Error: Naming Confliction between " + Constants.camera1Name + " ID ("
                            + Constants.camera1ID + ") and " + Constants.camera3Name + " ID (" + Constants.camera3ID + ")",
                    false);
            return false;
        }

        if (Constants.camera2ID == Constants.camera3ID) {

            DriverStation.reportError("Camera Error: Naming Confliction between " + Constants.camera2Name + " ID ("
                            + Constants.camera2ID + ") and " + Constants.camera3Name + " ID (" + Constants.camera3ID + ")",
                    false);
            return false;
        }

        return true;

    }

}
