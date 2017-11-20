package dk.aau.teambrain.mindstormfetchy.model;

import com.google.api.client.util.Key;

public class Request {

    @Key
    public String action;

    @Key
    public String color;

    @Key
    public String object;

    @Override
    public String toString() {
        return "Request{" +
                "action='" + action + '\'' +
                ", color='" + color + '\'' +
                ", object='" + object + '\'' +
                '}';
    }
}