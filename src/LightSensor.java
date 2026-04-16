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

        // light sensor on port S3
        EV3ColorSensor sensor = new EV3ColorSensor(SensorPort.S3);
        SampleProvider lightMode = sensor.getRedMode();
        float[] sample = new float[lightMode.sampleSize()];

        
        EV3LargeRegulatedMotor motorA = new EV3LargeRegulatedMotor(MotorPort.A);
        EV3LargeRegulatedMotor motorB = new EV3LargeRegulatedMotor(MotorPort.B);

        int forwardSpeed = 180;
        int turnSpeed = 120;

        // light value limit
        int threshold = 35;

        LCD.clear();
        LCD.drawString("Press any key", 0, 0);
        Button.waitForAnyPress();

        while (!Button.ESCAPE.isDown()) {

            // read light value
            lightMode.fetchSample(sample, 0);
            int lightValue = (int)(sample[0] * 100);

            LCD.clear();
            LCD.drawString("Light:", 0, 0);
            LCD.drawString(lightValue + " %", 0, 1);
            
            // if dark, move forward
        if (lightValue < threshold) {
            motorA.setSpeed(forwardSpeed);
            motorB.setSpeed(forwardSpeed);
            motorA.forward();
            motorB.forward();
            LCD.drawString("On line", 0, 3);
        }

        // if bright, turn a little to find line

        else{
                motorA.setSpeed(turnSpeed);
                motorB.setSpeed(turnSpeed);
                motorA.forward();
                motorB.backward();
                LCD.drawString("Finding line", 0, 3);
            }

            Delay.msDelay(70);
        }

        motorA.stop(true);
        motorB.stop();

        motorA.close();
        motorB.close();

        sensor.close();
    }


}
