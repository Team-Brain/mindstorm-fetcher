package dk.aau.teambrain.mindstormfetcher;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

//import lejos.hardware.*;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;



//import lejos.hardware.BrickFinder;
//import lejos.hardware.Keys;
//import lejos.hardware.ev3.EV3;
//import lejos.hardware.lcd.TextLCD;

/**
 * Example leJOS EV3 Project.
 */
public class HelloWorld {
	RemoteEV3 ev3;
	static RMIRegulatedMotor motorA, motorB, motorC, motorD;
	static boolean stop=false;

    public static void main(String[] args) throws RemoteException, MalformedURLException, NotBoundException {
       
    	RemoteEV3 ev3=new RemoteEV3("10.0.1.1");
    	ev3.isLocal();
    	
    	//EV3 ev3 = (EV3) BrickFinder.getLocal();
        //TextLCD lcd = ev3.getTextLCD();
        //Keys keys = ev3.getKeys();

        //lcd.drawString("Hello World", 4, 4);
        //keys.waitForAnyPress();
        
    	//get ports
    	ev3.getPort("A");
    	ev3.getPort("B");
    	ev3.getPort("C");
    	ev3.getPort("D");
    	
        //creates motors
        if(motorA==null){motorA = ev3.createRegulatedMotor("A",'L');}
        if(motorB==null){motorB = ev3.createRegulatedMotor("B",'L');}
        if(motorC==null){motorC = ev3.createRegulatedMotor("C",'L');}
        if(motorD==null){motorD = ev3.createRegulatedMotor("D",'L');}
        
        //lets move some motors
       /* while(!stop){
            motorB.forward();
            motorC.forward();
            timer(5000);
            motorB.stop(true);
            motorC.stop(true);
            stop=true;   
        }
        timer(1000);
        stop = false;*/
        while(!stop){
        	motorB.rotate(1540);
        	motorC.rotate(0);
        	timer(1000);
        	stop=true;
        }
        while(!stop){
        	 motorB.backward();
             motorC.backward();
             timer(5000);
             motorB.stop(true);
             motorC.stop(true);
             stop=true;
        }
        //closes ports.
        if(stop){
        	  motorA.close();
              motorB.close();
              motorC.close();
              motorD.close();
        }
    }
        public static void timer(long wait){
        	try{
        		Thread.sleep(wait);
        	}
        	catch(InterruptedException e){
        		e.printStackTrace();
        	}
}

}