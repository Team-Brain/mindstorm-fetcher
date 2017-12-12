package dk.aau.teambrain.mindstormfetchy.robot;

import dk.aau.teambrain.mindstormfetchy.State;
import dk.aau.teambrain.mindstormfetchy.model.Task;
import dk.aau.teambrain.mindstormfetchy.thread.SocketIoThread;

import javax.annotation.OverridingMethodsMustInvokeSuper;

public abstract class BaseRobot implements FetchingRobot, TaskHandler {

    protected Task currentTask;
    protected State currentState;

    public boolean carryingObject = false;

    public Task getCurrentTask() {
        return currentTask;
    }

    public void setCurrentTask(Task currentTask) {
        this.currentTask = currentTask;
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

    public void finishTask() {
        currentTask = null;
        SocketIoThread.notifyRequestCompleted();
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
    }

    @Override
    public void onAbortTask() {
        currentState = State.ABORT;
    }
}
