package dk.aau.teambrain.mindstormfetchy.model;

import com.google.api.client.util.Key;

/**
 * Represents a single command to fetch object for the robot.
 */
public class Task {

    @Key
    private String color;

    @Key
    private String object;

    public void setColor(String color) {
        this.color = color;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getColor() {
        return color;
    }

    public String getObject() {
        return object;
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