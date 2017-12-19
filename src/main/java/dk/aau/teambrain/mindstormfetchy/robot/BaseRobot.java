package dk.aau.teambrain.mindstormfetchy.robot;

import dk.aau.teambrain.mindstormfetchy.State;
import dk.aau.teambrain.mindstormfetchy.model.Task;

import javax.annotation.OverridingMethodsMustInvokeSuper;

@SuppressWarnings("WeakerAccess")
public abstract class BaseRobot implements FetchingRobot, TaskHandler {

    protected Task currentTask;
    protected State currentState;

    public boolean carryingObject = false;

    public BaseRobot() {
        currentState = State.WAITING_FOR_COMMAND;
    }

    public Task getCurrentTask() {
        return currentTask;
    }

    public State getCurrentState() {
        return currentState;
    }

    public void clearCurrentTask() {
        currentTask = null;
    }

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    public boolean hasTask() {
        return currentTask != null;
    }

    @OverridingMethodsMustInvokeSuper
    @Override
    public void grab() {
        carryingObject = true;
    }

    @OverridingMethodsMustInvokeSuper
    @Override
    public void letGo() {
        carryingObject = false;
    }

    @Override
    public void onNewTask(Task task) {
        if (currentTask == null) {
            currentTask = task;
            currentState = State.SEARCHING;
        } else {
            // Ignore task from websocket until finished
        }
    }

    @OverridingMethodsMustInvokeSuper
    @Override
    public void onTaskFinished() {
        currentTask.setFinished(true);
    }

    @Override
    public void onAbortTask() {
        currentState = State.ABORT;
    }

    /**
     * Creates demo task.
     */
    public void createDemoTask() {
        Task task = new Task();
        task.setColor("Black");
        onNewTask(task);
    }
}
