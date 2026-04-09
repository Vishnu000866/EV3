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
            
        // ultrasonic sensor on port S2
        EV3UltrasonicSensor ultrasonicSensor = new EV3UltrasonicSensor(SensorPort.S2);
        SampleProvider distanceMode = ultrasonicSensor.getDistanceMode();
        float[] sample = new float[distanceMode.sampleSize()];

        // motors on A and B
        EV3LargeRegulatedMotor motorA = new EV3LargeRegulatedMotor(MotorPort.A);
        EV3LargeRegulatedMotor motorB = new EV3LargeRegulatedMotor(MotorPort.B);

        // speed values
        int fastSpeed = 300;
        int slowSpeed = 120;
        int turnSpeed = 150;

        // distances in meters
        float slowDistance = 0.40f;
        float stopDistance = 0.20f;

        // time values
        long startTime;
        long endTime;
        long travelTime;

        LCD.clear();
        LCD.drawString("Press any key", 0, 0);
        Button.waitForAnyPress();

        // robot starts moving
        motorA.setSpeed(fastSpeed);
        motorB.setSpeed(fastSpeed);
        motorA.forward();
        motorB.forward();

        startTime = System.currentTimeMillis();

        while (!Button.ESCAPE.isDown()) {

            // read distance from ultrasonic sensor
            distanceMode.fetchSample(sample, 0);
            float distance = sample[0];

            LCD.clear();
            LCD.drawString("Distance:", 0, 0);
            LCD.drawString((distance * 100) + " cm", 0, 1);


             // slow down when object is near
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

        // calculate how long robot moved forward
        endTime = System.currentTimeMillis();
        travelTime = endTime - startTime;

        
        LCD.clear();
        LCD.drawString("Turning...", 0, 0);

        motorA.setSpeed(turnSpeed);
        motorB.setSpeed(turnSpeed);

        motorA.forward();
        motorB.backward();

        Delay.msDelay(1100);

        motorA.stop(true);
        motorB.stop();

          Delay.msDelay(300);

          LCD.clear();
        LCD.drawString("Going back", 0, 0);

        motorA.setSpeed(fastSpeed);
        motorB.setSpeed(fastSpeed);
        motorA.forward();
        motorB.forward();

        Delay.msDelay((int) travelTime);

        motorA.stop(true);
        motorB.stop();

        LCD.clear();
        LCD.drawString("Finished", 0, 0);
        Button.waitForAnyPress();

        ultrasonicSensor.close();
        motorA.close();
        motorB.close();
    }
    }
