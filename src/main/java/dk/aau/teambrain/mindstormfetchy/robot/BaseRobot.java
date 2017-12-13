package dk.aau.teambrain.mindstormfetchy.robot;

import dk.aau.teambrain.mindstormfetchy.State;
import dk.aau.teambrain.mindstormfetchy.model.Task;
import dk.aau.teambrain.mindstormfetchy.thread.SocketIoThread;

import javax.annotation.OverridingMethodsMustInvokeSuper;

public abstract class BaseRobot implements FetchingRobot, TaskHandler {

    protected volatile Task currentTask;
    protected volatile State currentState;

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

    public void createDemoTask() {
        Task task = new Task();
        task.setColor("Red");
        currentTask = task;
    }
}
