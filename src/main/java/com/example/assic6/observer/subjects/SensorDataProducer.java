package com.example.assic6.observer.subjects;

import java.util.concurrent.ThreadLocalRandom;

public class SensorDataProducer implements Runnable {
    private final AirQualitySensor airSensor;
    private final int eventCount;

    public SensorDataProducer(AirQualitySensor airSensor, int eventCount) {
        this.airSensor = airSensor;
        this.eventCount = eventCount;
    }

    @Override
    public void run() {
        for (int i = 0; i < eventCount; i++) {
            double randomAqi = ThreadLocalRandom.current().nextDouble(101.0, 200.0);
            airSensor.checkAirQuality(randomAqi);
        }
    }
}