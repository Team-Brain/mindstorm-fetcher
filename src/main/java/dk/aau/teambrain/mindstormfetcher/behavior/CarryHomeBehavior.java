package dk.aau.teambrain.mindstormfetcher.behavior;

import dk.aau.teambrain.mindstormfetcher.Fetchy;
import dk.aau.teambrain.mindstormfetcher.State;
import lejos.robotics.subsumption.Behavior;

public class CarryHomeBehavior implements Behavior {

    @Override
    public boolean takeControl() {
        return Fetchy.currentState == State.CARRYING_HOME;
    }

    @Override
    public void action() {
        Fetchy.turn180();
        Fetchy.letGo();
        Fetchy.requestQueue.remove(0);
        Fetchy.currentState = State.WAITING_FOR_COMMAND;
    }

    @Override
    public void suppress() {

    }
}
