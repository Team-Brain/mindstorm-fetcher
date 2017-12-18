package dk.aau.teambrain.mindstormfetchy.robot;

import dk.aau.teambrain.mindstormfetchy.State;
import dk.aau.teambrain.mindstormfetchy.model.Task;

import javax.annotation.OverridingMethodsMustInvokeSuper;

@SuppressWarnings("WeakerAccess")
public abstract class BaseRobot implements FetchingRobot, TaskHandler {

    protected Task currentTask;
    protected State currentState;

    public boolean carryingObject = false;

    public Task getCurrentTask() {
        return currentTask;
    }

    public State getCurrentState() {
        return currentState;
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
        currentTask = task;
        currentState = State.SEARCHING;
    }

    @OverridingMethodsMustInvokeSuper
    public void onTaskFinished() {
        currentTask = null;
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
        currentTask = task;
    }
}
