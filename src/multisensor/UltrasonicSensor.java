package multisensor;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class UltrasonicSensor implements Runnable {

    private static EV3UltrasonicSensor ultrasonic = new EV3UltrasonicSensor(SensorPort.S2);
    private static SampleProvider distanceProvider = ultrasonic.getDistanceMode();
    private static float[] sample = new float[distanceProvider.sampleSize()];
    private static Thread avoidanceThread;

    private static final float OBSTACLE_MIN = 0.03f;
    private static final float OBSTACLE_MAX = 0.16f;

    private long lastAvoidTime = 0;
    private int obstacleCount = 0;

    public static void startAvoidance() {
        avoidanceThread = new Thread(new UltrasonicSensor());
        avoidanceThread.start();
    }

    public static float getDistance() {
        synchronized (MultiSensorRobot.sensorLock) {
            distanceProvider.fetchSample(sample, 0);
            return sample[0];
        }
    }

    public static void close() {
        ultrasonic.close();
    }

    @Override
    public void run() {
        while (!Thread.interrupted() && !Button.ESCAPE.isDown()) {

            float distance = getDistance();
            long now = System.currentTimeMillis();

            if (distance > OBSTACLE_MIN
                    && distance < OBSTACLE_MAX
                    && !MultiSensorRobot.avoidObstacle
                    && now - lastAvoidTime > 3000) {

                MultiSensorRobot.avoidObstacle = true;
                obstacleCount++;

                LCD.clear(3);
                LCD.drawString("Obstacle " + obstacleCount, 0, 3);

                try {
                    avoidObstacleRoute();
                } finally {
                    lastAvoidTime = System.currentTimeMillis();
                    MultiSensorRobot.avoidObstacle = false;
                }
            }

            Delay.msDelay(100);
        }
    }

    private void avoidObstacleRoute() {
        stopMotors();
        moveBackward(120, 250);

        // Go around obstacle from right side
        turnRight(180, 550);
        moveForward(180, 900);
        turnLeft(180, 550);
        moveForward(180, 1100);
        turnLeft(180, 550);

        // Search for the black line again
        driveUntilBlackLine(150, 4000);

        // Align robot direction with the line
        turnRight(160, 450);
        stopMotors();
    }

    private void moveForward(int speed, int timeMs) {
        synchronized (MultiSensorRobot.motorLock) {
            MultiSensorRobot.leftMotor.setSpeed(speed);
            MultiSensorRobot.rightMotor.setSpeed(speed);
            MultiSensorRobot.leftMotor.forward();
            MultiSensorRobot.rightMotor.forward();
        }
        Delay.msDelay(timeMs);
        stopMotors();
    }

    private void moveBackward(int speed, int timeMs) {
        synchronized (MultiSensorRobot.motorLock) {
            MultiSensorRobot.leftMotor.setSpeed(speed);
            MultiSensorRobot.rightMotor.setSpeed(speed);
            MultiSensorRobot.leftMotor.backward();
            MultiSensorRobot.rightMotor.backward();
        }
        Delay.msDelay(timeMs);
        stopMotors();
    }

    private void turnRight(int speed, int timeMs) {
        synchronized (MultiSensorRobot.motorLock) {
            MultiSensorRobot.leftMotor.setSpeed(speed);
            MultiSensorRobot.rightMotor.setSpeed(speed);
            MultiSensorRobot.leftMotor.forward();
            MultiSensorRobot.rightMotor.backward();
        }
        Delay.msDelay(timeMs);
        stopMotors();
    }

    private void turnLeft(int speed, int timeMs) {
        synchronized (MultiSensorRobot.motorLock) {
            MultiSensorRobot.leftMotor.setSpeed(speed);
            MultiSensorRobot.rightMotor.setSpeed(speed);
            MultiSensorRobot.leftMotor.backward();
            MultiSensorRobot.rightMotor.forward();
        }
        Delay.msDelay(timeMs);
        stopMotors();
    }

    private void driveUntilBlackLine(int speed, int maxTimeMs) {
        synchronized (MultiSensorRobot.motorLock) {
            MultiSensorRobot.leftMotor.setSpeed(speed);
            MultiSensorRobot.rightMotor.setSpeed(speed);
            MultiSensorRobot.leftMotor.forward();
            MultiSensorRobot.rightMotor.forward();
        }

        int elapsed = 0;

        while (elapsed < maxTimeMs && !Button.ESCAPE.isDown()) {
            float light = LightSensor.getLightValue();

            // Black line normally gives lower light value than threshold
            if (light < LightSensor.getThreshold()) {
                break;
            }

            Delay.msDelay(20);
            elapsed += 20;
        }

        stopMotors();
    }

    private void stopMotors() {
        synchronized (MultiSensorRobot.motorLock) {
            MultiSensorRobot.leftMotor.stop(true);
            MultiSensorRobot.rightMotor.stop();
        }
        Delay.msDelay(100);
    }
}