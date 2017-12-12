package dk.aau.teambrain.mindstormfetchy.behavior;

import dk.aau.teambrain.mindstormfetchy.robot.BaseRobot;
import dk.aau.teambrain.mindstormfetchy.utils.Log;
import lejos.robotics.subsumption.Behavior;

import javax.annotation.OverridingMethodsMustInvokeSuper;

public abstract class BaseBehavior implements Behavior {

    protected static boolean suppressed;

    protected BaseRobot robot;

    public BaseBehavior(BaseRobot robot) {
        this.robot = robot;
    }

    protected abstract String getName();

    @OverridingMethodsMustInvokeSuper
    @Override
    public void action() {
        Log.d(getName());
    }

    @Override
    public void suppress() {
        suppressed = true;
    }
}
