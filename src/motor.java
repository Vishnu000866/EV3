import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.UnregulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class motor {
    public static void main (String[] args) {

        EV3LargeRegulatedMotor leftMotor = new EV3LargeRegulatedMotor(MotorPort.A);
        EV3LargeRegulatedMotor rightMotor = new EV3LargeRegulatedMotor(MotorPort.B);
        System.out.println("assignment statring...");

        // move straight for 10 sec using setspeed()

        leftMotor.setspeed(360);  // degree per second
        rightMotor.setspeed(360); 

        leftMotor.forward();
        rightMotor.forward();
        Delay.msDelay(10000)

        leftMotor.stop(false);
        rightMotor.stop(false);
        Delay.msDelay(1000);

        // do a 360 degree turn using rotate()
        leftMotor.setspeed(250);
        rightMotor.setspeed(250);

        leftMotor.rotate(720, true); // ajust if needed for your robot 
        rightMotor.rotate(-720);

        //stop(true) = stop with braking 
        leftMotor.stop(true);
        rightMotor.stop(true);
        Delay.msDelay(10000);

        // Display message on LCD
        LCD.clear();
        LCD.drawString("Reached halfway,",0, 0);
        LCD.drawString("returing to base",0, 0);
        Delay.msDelay(3000);


        //Turn robot so it faces back to base using rotateTo()
        leftMotor.resetTachoCount();
        rightMotor.resetTachoCount();

        leftMotor.setspeed(200);
        rightMotor.setspeed(200);


        leftMotor.rotateTo(360,true);  //about 180- degree turn 
        rightMotor.rotateTo(-360);     //adjust if needed for your robot


        // stop() = default stop with brake 
        leftMotor.stop();
        rightMotor.stop();
        Delay.msDelay(1000);

        //close regulated motors before opening unregulated motors
        leftMotor.close();
        rightMotor.close();

        UnregulatedMotor leftPowerMotor = new UnregulatedMotor(MotorPort.A);
        UnregulatedMotor rightPowerMotor = new UnregulatedMotor(MotorPort.B);


        // return to base using setPower()
        leftPowerMotor.setPower(30); 
        rightPowerMotor.setPower(30);
       
        leftPowerMotor.forward();
        rightPowerMotor.forward();
        Delay.msDelay(10000);

        leftPowerMotor.stop();
        rightPowerMotor.stop();

        leftPowerMotor.stop();
        rightPowerMotor.stoop();

        System.out.println("....");


    }
