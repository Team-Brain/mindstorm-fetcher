package dk.aau.teambrain.mindstormfetchy.utils;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.SampleProvider;

/**
 * SeekerSensorWrapper wraps the EV3IRSensor and provides methods
 * to get seekDistance and seekDirection
 */
public class SeekSensorWrapper {

    private EV3IRSensor sensor;
    private SampleProvider sp;
    private float[] sample;

    /**
     * Creates IRSensorWrapper object.
     *
     * @param port SensorPort of EV3IRSensor device.
     */
    public SeekSensorWrapper(Port port) {
        sensor = new EV3IRSensor(port);
        sp = sensor.getSeekMode();
        sample = new float[sp.sampleSize()];
    }

    public float getSeekDistance() {
        sp.fetchSample(sample, 0);
        return sample[1];
    }

    public float getSeekDirection() {
        sp.fetchSample(sample, 0);
        return sample[0];
    }

    /**
     * Release resources.
     */
    public void close() {
        sensor.close();
    }
}
