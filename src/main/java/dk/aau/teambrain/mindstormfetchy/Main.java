package dk.aau.teambrain.mindstormfetchy;

import dk.aau.teambrain.mindstormfetchy.behavior.*;
import dk.aau.teambrain.mindstormfetchy.robot.BaseRobot;
import dk.aau.teambrain.mindstormfetchy.robot.Fetchy;
import dk.aau.teambrain.mindstormfetchy.thread.ExitThread;
import dk.aau.teambrain.mindstormfetchy.thread.SocketIoThread;
import lejos.hardware.Button;
import lejos.hardware.Key;
import lejos.hardware.KeyListener;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Main {

    public static final boolean DEBUG = true;
    public static final boolean OFFLINE = true;

    public static void main(String[] args) throws Exception {

        // Initialize robot
        final BaseRobot fetchy = new Fetchy();
        // Start the exit listener thread
        new ExitThread(fetchy).start();

        // Start the socketIO thread
        if (!OFFLINE) {
            new SocketIoThread(fetchy).start();
        }

        // Initialize behaviours
        Behavior searchBeh = new SearchBehavior(fetchy);
        Behavior scanBeh = new ScanObjectBehavior(fetchy);
        Behavior goHomeBehavior = new GoHomeBehavior(fetchy);
        Behavior carryToUserBeh = new CarryToUserBehavior(fetchy);
        Behavior abortBeh = new AbortBehavior(fetchy);
        Behavior waitForCommandBeh = new WaitForCommandBehavior(fetchy);
        Behavior[] bArray = {searchBeh, scanBeh, goHomeBehavior, carryToUserBeh, abortBeh, waitForCommandBeh};
        Arbitrator arb = new Arbitrator(bArray);
        arb.go();
        
        Button.LEFT.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(Key k) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(Key k) {
				fetchy.turn(-90);
				
			}
        	
        });
        
        Button.RIGHT.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(Key k) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(Key k) {
				fetchy.turn(90);
				
			}
        	
        });
        
        Button.UP.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(Key k) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(Key k) {
				fetchy.turn(180);
				
			}
        	
        });
        
        Button.DOWN.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(Key k) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(Key k) {
				fetchy.turn(-180);
				
			}
        	
        });

    }

}
