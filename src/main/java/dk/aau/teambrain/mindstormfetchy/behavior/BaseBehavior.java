package dk.aau.teambrain.mindstormfetchy.behavior;

import dk.aau.teambrain.mindstormfetchy.robot.BaseRobot;
import dk.aau.teambrain.mindstormfetchy.test.BehaviorChangeListener;
import dk.aau.teambrain.mindstormfetchy.utils.Log;
import lejos.robotics.subsumption.Behavior;

import javax.annotation.OverridingMethodsMustInvokeSuper;

/**
 * BaseBehavior abstract class encapsulates the common logic of all behaviors.
 * All behaviors should extend this class.
 */
public abstract class BaseBehavior implements Behavior {

    protected boolean suppressed;

    protected BaseRobot robot;

    private BehaviorChangeListener listener;

    /**
     * Constructs the behavior.
     *
     * @param robot Robot to perform the operations on.
     */
    public BaseBehavior(BaseRobot robot) {
        this.robot = robot;
    }

    /**
     * Constructs the behavior.
     *
     * @param robot    Robot to perform the operations on.
     * @param listener Listener for behaviour changes.
     */
    public BaseBehavior(BaseRobot robot, BehaviorChangeListener listener) {
        this.robot = robot;
        this.listener = listener;
    }

    protected abstract String getTag();

    @OverridingMethodsMustInvokeSuper
    @Override
    public void action() {
        // Fixes a bug with arbitrator that sometimes calls action even though takeControl returns false
        if (!takeControl()) {
            return;
        }
        Log.d(getTag());
        if (listener != null) {
            listener.onBehaviorChanged(getTag());
        }
    }

    @Override
    public void suppress() {
        suppressed = true;
    }
}
