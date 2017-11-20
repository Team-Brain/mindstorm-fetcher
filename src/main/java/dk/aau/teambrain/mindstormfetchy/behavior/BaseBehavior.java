package dk.aau.teambrain.mindstormfetchy.behavior;

import lejos.robotics.subsumption.Behavior;

import javax.annotation.OverridingMethodsMustInvokeSuper;

public abstract class BaseBehavior implements Behavior {

    protected static boolean suppressed;

    protected abstract String getName();

    @OverridingMethodsMustInvokeSuper
    @Override
    public void action() {
//        System.out.println(getName());
    }
}
