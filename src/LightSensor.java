package src;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class LightSensor {

     public static void main(String[] args) {

        EV3ColorSensor sensor = new EV3ColorSensor(SensorPort.S3);
        SampleProvider lightMode = sensor.getRedMode();
        float[] sample = new float[lightMode.sampleSize()];

        LCD.clear();
        LCD.drawString("Press any key", 0, 0);
        Button.waitForAnyPress();

        while (!Button.ESCAPE.isDown()) {

            lightMode.fetchSample(sample, 0);
            int lightValue = (int)(sample[0] * 100);

            LCD.clear();
            LCD.drawString("Light:", 0, 0);
            LCD.drawString(lightValue + " %", 0, 1);

            Delay.msDelay(100);
        }

        sensor.close();
    }


}