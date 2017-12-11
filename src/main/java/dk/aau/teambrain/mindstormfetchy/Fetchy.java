package dk.aau.teambrain.mindstormfetchy;

import dk.aau.teambrain.mindstormfetchy.behavior.*;
import dk.aau.teambrain.mindstormfetchy.model.Task;
import dk.aau.teambrain.mindstormfetchy.thread.SocketIoThread;
import dk.aau.teambrain.mindstormfetchy.utils.Log;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class Fetchy {

    public static FetchingRobot robot;

    private static volatile Task currentTask;
    private static volatile State currentState;

    public static boolean carryingObject = false;


    public static void init(FetchingRobot r) {
        Log.i("Initializing Fetchy");

        Fetchy.currentState = State.WAITING_FOR_COMMAND;

        robot = r;

        // Initialize behaviours
        Behavior searchBeh = new SearchBehavior();
        Behavior scanBeh = new ScanObjectBehavior();
        Behavior goHomeBehavior = new GoHomeBehavior();
        Behavior carryToUserBeh = new CarryToUserBehavior();
        Behavior abortBeh = new AbortBehavior();
        Behavior waitForCommandBeh = new WaitForCommandBehavior();
        Behavior[] bArray = {searchBeh, scanBeh, goHomeBehavior, carryToUserBeh, abortBeh, waitForCommandBeh};

        Log.i("Initialization complete");

        Arbitrator arb = new Arbitrator(bArray);
        arb.go();

    }

    public static State getCurrentState() {
        return currentState;
    }

    public static synchronized void setCurrentState(State currentState) {
        Fetchy.currentState = currentState;
    }

    public static void grabObject() {
        robot.grab();
        carryingObject = true;
    }

    public static void leaveObject() {
        robot.letGo();
        carryingObject = false;
    }

    public static void leaveOnTheSide(boolean turnToStartAngle) {
        robot.leaveObjectOnSide(turnToStartAngle);
        carryingObject = false;
    }

    public static void createDemoRequest() {
        Task task = new Task();
        task.setColor("Blue");
        currentTask = task;
    }

    public static boolean hasTask() {
        return currentTask != null;
    }

    public static void finishRequest() {
        if (Fetchy.hasTask()) {
            currentTask = null;
        }
        SocketIoThread.notifyRequestCompleted();
    }

    public static Task getCurrentTask() {
        if (!hasTask()) {
            throw new IllegalArgumentException("No requests in the queue.");
        }
        return currentTask;
    }

    public static void setCurrentTask(Task task) {
        currentTask = task;
    }

    public static void abortCurrentTask() {
        if (currentState != State.WAITING_FOR_COMMAND) {
            setCurrentState(State.ABORT);
        }
    }

    public static void close() {
        robot.close();
    }

}