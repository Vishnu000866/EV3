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
        
}
