package dk.aau.teambrain.mindstormfetchy.utils;

import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.robotics.RangeFinder;
import lejos.robotics.SampleProvider;

public class IRSensor implements RangeFinder {

    private EV3IRSensor sensor;
    private SampleProvider sp;
    private float[] sample;

    /**
     * Creates UltraSonicSensor object. This is a wrapper class for EV3UltrasonicSensor.
     *
     * @param port SensorPort of EV3UltrasonicSensor device.
     */
    public IRSensor(Port port) {
        sensor = new EV3IRSensor(port);
        sp = sensor.getDistanceMode();
        sample = new float[sp.sampleSize()];
    }

    /**
     * Returns the underlying EV3UltrasonicSensor object.
     *
     * @return Sensor object reference.
     */
    public EV3IRSensor getSensor() {
        return sensor;
    }

    /**
     * Get range (distance) to object detected by UltraSonic sensor.
     *
     * @return Distance in meters.
     */
    @Override
    public float getRange() {
        sp.fetchSample(sample, 0);
        return sample[0];
    }

    /**
     * Get range (distance) to object detected by UltraSonic sensor.
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