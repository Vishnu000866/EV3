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

    }
}
