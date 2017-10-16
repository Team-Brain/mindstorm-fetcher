package dk.aau.teambrain.mindstormfetcher.model;

import com.google.api.client.util.Key;

public class Request {

    @Key
    public String action;

    @Key
    public String color;

    @Key
    public String displayText;

    @Key
    public String object;

    @Key
    public String speech;

    @Override
    public String toString() {
        return "Request{" +
                "action='" + action + '\'' +
                ", color='" + color + '\'' +
                ", displayText='" + displayText + '\'' +
                ", object='" + object + '\'' +
                ", speech='" + speech + '\'' +
                '}';
    }
}