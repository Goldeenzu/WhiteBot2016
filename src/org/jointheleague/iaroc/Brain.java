package org.jointheleague.iaroc;

import android.os.SystemClock;

import ioio.lib.api.IOIO;
import ioio.lib.api.exception.ConnectionLostException;

import org.wintrisstech.irobot.ioio.IRobotCreateAdapter;
import org.wintrisstech.irobot.ioio.IRobotCreateInterface;
import org.jointheleague.iaroc.sensors.UltraSonicSensors;

public class Brain extends IRobotCreateAdapter {
    private final Dashboard dashboard;
    public UltraSonicSensors sonar;

    public Brain(IOIO ioio, IRobotCreateInterface create, Dashboard dashboard)
            throws ConnectionLostException {
        super(create);
        sonar = new UltraSonicSensors(ioio);
        this.dashboard = dashboard;
    }

    /* This method is executed when the robot first starts up. */
    public void initialize() throws ConnectionLostException {
        dashboard.log("Hello! I'm a Clever Robot!");
        //what would you like me to do, Clever Human?


    }

    /* This method is called repeatedly. */
    public void wallHugger() throws ConnectionLostException {
        driveDirect(200, 500);
        readSensors(6);
        dashboard.log(getWallSignal() + "");
        if (isBumpRight()) {
            driveDirect(500, -500);
            SystemClock.sleep(100);
        }
        if (getWallSignal() > 100) {
            driveDirect(500, 200);
            SystemClock.sleep(200);
        }
        if (isBumpRight() && isBumpLeft()) {
            driveDirect(500, -500);
            SystemClock.sleep(500);
        }
    }

    public void goldRush() throws ConnectionLostException {

        driveDirect(50, -50);
        readSensors(SENSORS_INFRARED_BYTE);
        int inByte = getInfraredByte();
        dashboard.log("" + inByte);
        if (inByte != 255) {
            driveDirect(0, 0);
            SystemClock.sleep(10000);
            driveDirect(50,50);
        }
    }




    public void loop() throws ConnectionLostException {
        //wallHugger();
        goldRush();


    }
}