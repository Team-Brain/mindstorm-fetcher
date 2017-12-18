package dk.aau.teambrain.mindstormfetchy.utils;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.RangeFinder;
import lejos.robotics.SampleProvider;

/**
 * IRSensorWrapper wraps the EV3IRSensor and provides methods get current range.
 */
public class IRSensorWrapper implements RangeFinder {

    private EV3IRSensor sensor;
    private SampleProvider sp;
    private float[] sample;

    /**
     * Creates IRSensorWrapper object.
     *
     * @param port SensorPort of EV3IRSensor device.
     */
    public IRSensorWrapper(Port port) {
        sensor = new EV3IRSensor(port);
        sp = sensor.getDistanceMode();
        sample = new float[sp.sampleSize()];
    }


    /**
     * Get range (distance) to object detected by IR sensor.
     *
     * @return Distance in meters.
     */
    @Override
    public float getRange() {
        sp.fetchSample(sample, 0);
        return sample[0];
    }

    /**
     * Get range (distance) to object detected by IR sensor.
     *
     * @return Distance in meters. Only one distance value is returned.
     */
    @Override
    public float[] getRanges() {
        sp.fetchSample(sample, 0);
        return sample;
    }

    /**
     * Release resources.
     */
    public void close() {
        sensor.close();
    }
}