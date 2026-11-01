package com.example.assic6.observer.subjects;

import com.example.assic6.observer.AbstractSubject;
import com.example.assic6.observer.EventType;
import com.example.assic6.observer.SensorEvent;

public class AirQualitySensor extends AbstractSubject {
    private final String sensorId;
    private final String districtId;

    public AirQualitySensor(String sensorId, String districtId) {
        this.sensorId = sensorId;
        this.districtId = districtId;
    }

    public void checkAirQuality(double aqi) {
        if (aqi > 100.0) {
            SensorEvent event = new SensorEvent(EventType.AIR_QUALITY_ALERT, districtId, sensorId, aqi);
            notifyObservers(event);
        }
    }
}