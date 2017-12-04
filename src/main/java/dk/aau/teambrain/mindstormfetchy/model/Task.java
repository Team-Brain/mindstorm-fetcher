package dk.aau.teambrain.mindstormfetchy.model;

import com.google.api.client.util.Key;

public class Task {

    @Key
    private String action;

    @Key
    private String color;

    @Key
    private String object;

    public void setAction(String action) {
        this.action = action;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getAction() {
        return action;
    }

    public String getColor() {
        return color;
    }

    public String getObject() {
        return object;
    }

    @Override
    public String toString() {
        return "Task{" +
                "action='" + action + '\'' +
                ", color='" + color + '\'' +
                ", object='" + object + '\'' +
                '}';
    }
}