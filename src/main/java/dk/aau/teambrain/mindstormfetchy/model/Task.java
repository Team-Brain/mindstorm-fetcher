package dk.aau.teambrain.mindstormfetchy.model;

import com.google.api.client.util.Key;

/**
 * Represents a single command to fetch object for the robot.
 */
public class Task {

    @Key
    private String id;

    @Key
    private String color;

    @Key
    private String object;

    @Key
    private String timestamp;

    private boolean finished;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Task() {
    }

    public Task(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Task{" +
                ", color='" + color + '\'' +
                ", object='" + object + '\'' +
                '}';
    }
}