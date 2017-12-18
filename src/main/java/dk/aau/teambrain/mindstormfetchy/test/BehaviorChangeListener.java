package dk.aau.teambrain.mindstormfetchy.test;

/**
 * Interface for classes that listen to behavior changes.
 */
public interface BehaviorChangeListener {

    /**
     * Called when new behavior change happens.
     *
     * @param behaviorName Name of the new behavior.
     */
    void onBehaviorChanged(String behaviorName);

}