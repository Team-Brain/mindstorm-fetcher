package dk.aau.teambrain.mindstormfetchy.robot;

import dk.aau.teambrain.mindstormfetchy.model.Task;

/**
 * Interface representing an object that can handle Tasks.
 */
public interface TaskHandler {

    /**
     * Called when new task is received.
     *
     * @param task Received task.
     */
    void onNewTask(Task task);

    /**
     * Called when the current task has been aborted.
     */
    void onAbortTask();

    /**
     * Called when the current task has been finished.
     */
    void onTaskFinished();
}
