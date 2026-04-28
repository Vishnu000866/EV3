package multisensor;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.utility.Delay;

public class LightSensor {
    private static EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S1);
    private static SensorMode lightMode = colorSensor.getRedMode();
    private static float[] sample = new float[lightMode.sampleSize()];
    private static float lightThreshold = 0.35f;
    public static void calibrate() {










        
    }






