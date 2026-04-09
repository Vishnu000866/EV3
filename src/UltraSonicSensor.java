package src;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

 public class UltraSonicSensor {
    public static void main(String[] args) {

        EV3UltrasonicSensor ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S2);
        SampleProvider distanceMode = ultrasonicSensor.getDistanceMode();
        float[] sample = new float[distanceMode.sampleSize()];

        EV3LargeRegulatedMotor motorA = new EV3LargeRegulatedMotor(MotorPort.A);
        EV3LargeRegulatedMotor motorB = new EV3LargeRegulatedMotor(MotorPort.B);

        int fastspeed = 300;
        int slowspeed = 120;

        float slowDistance = 0.40f;
        float stopDistance = 0.20f;

        LCD.clear();
        LCD.drawString("Press any key", 0, 0);
        Button.waitForAnyPress();

        while (!Button.ESCAPE.isDown()) {
            distanceMode.fetchSample(sample, 0);
            float distance = sample[0];

            LCD.clear();
            LCD.drawString("Distance:", 0, 0);
            LCD.drawString((distance * 100) + " cm", 0, 1);

            if (distance <= slowDistance && distance > stopDistance) {
                motorA.setSpeed(slowSpeed);
                motorB.setSpeed(slowSpeed);
                motorA.forward();
                motorB.forward();
                LCD.drawString("Slow", 0, 3);
            }
            else if (distance <= stopDistance) {
                motorA.stop(true);
                motorB.stop();
                LCD.drawString("Stop", 0, 3);
                break;
            }
            else {
                motorA.setSpeed(fastSpeed);
                motorB.setSpeed(fastSpeed);
                motorA.forward();
                motorB.forward();
                LCD.drawString("Fast", 0, 3);
            }
             Delay.msDelay(100);
        }

        motorA.stop(true);
        motorB.stop();

        ultrasonicSensor.close();
        motorA.close();
        motorB.close();
    }
    }
