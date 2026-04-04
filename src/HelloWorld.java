import lejos.hardware.Button;
import lejos.hardware.Button;
import lejos.hardware.Button;

public class Helloworld
{
    public static void main (String[] args)
    {

     // this is my first LEGO code 
     // make me autonomus
     // press any button to stop

     LCD.clear();
     LCD.drawString("Wellcome", 0, 0);
     Delay.msDelay(1000);

     LCD.drawString("This is my first LEGO code.", 0, 1);
     Delay.msDelay(2000);
     LCD.drawString("Make me autonomus", 0, 2);
     LCD.drawString("Press any button to stop.", 0, 3);

     // wait to a button press to exist
     Button.waitForAnyprss();

    }     
}