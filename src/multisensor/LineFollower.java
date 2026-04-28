package multisensor;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class LineFollower implements Runnable {

    
    private int limit(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    private int smoothSpeed(int oldSpeed, int newSpeed, int maxChange) {
        if (newSpeed > oldSpeed + maxChange) {
            return oldSpeed + maxChange;
        }
        if (newSpeed < oldSpeed - maxChange) {
            return oldSpeed - maxChange;
        }
        return newSpeed;
    }

    public void run() {
        int normalSpeed = 160;
        int maxMotorSpeed = 330;
        int minMotorSpeed = 80;

        float proportionalGain = 350;
        float deadBand = 0.015f;

        int lastLeftSpeed = normalSpeed;
        int lastRightSpeed = normalSpeed;
        int displayCounter = 0;


    

        while (!Button.ESCAPE.isDown()) {

            if (MultiSensorRobot.avoidObstacle) {
                Delay.msDelay(50);
                continue;
            }

            float currentLight = LightSensor.getLightValue();
            float deviation = currentLight - LightSensor.getThreshold();

            if (Math.abs(deviation) < deadBand) {
                deviation = 0;
            }

            int adjust = (int) (proportionalGain * deviation);

            int leftSpeed = normalSpeed - adjust;
            int rightSpeed = normalSpeed + adjust;

            leftSpeed = limit(leftSpeed, minMotorSpeed, maxMotorSpeed);
            rightSpeed = limit(rightSpeed, minMotorSpeed, maxMotorSpeed);

            leftSpeed = smoothSpeed(lastLeftSpeed, leftSpeed, 20);
            rightSpeed = smoothSpeed(lastRightSpeed, rightSpeed, 20);

            lastLeftSpeed = leftSpeed;
            lastRightSpeed = rightSpeed;

             synchronized (MultiSensorRobot.motorLock) {
                MultiSensorRobot.leftMotor.setSpeed(leftSpeed);
                MultiSensorRobot.rightMotor.setSpeed(rightSpeed);

                MultiSensorRobot.leftMotor.forward();
                MultiSensorRobot.rightMotor.forward();
            }
