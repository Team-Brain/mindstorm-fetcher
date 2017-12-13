package dk.aau.teambrain.mindstormfetchy.robot;

import dk.aau.teambrain.mindstormfetchy.model.Task;

public interface TaskHandler {

    void onNewTask(Task task);

    void onAbortTask();

    void onTaskFinished();
}
