package com.example.assic6.observer.subjects;

import com.example.assic6.observer.AbstractSubject;
import com.example.assic6.observer.EventType;
import com.example.assic6.observer.SensorEvent;

public class TrafficSensor extends AbstractSubject {
    private final String sensorId;
    private final String districtId;

    public TrafficSensor(String sensorId, String districtId) {
        this.sensorId = sensorId;
        this.districtId = districtId;
    }

    public void checkTraffic(double jamPercentage) {
        if (jamPercentage > 80.0) {
            SensorEvent event = new SensorEvent(EventType.TRAFFIC_CONGESTION, districtId, sensorId, jamPercentage);
            notifyObservers(event);
        }
    }
}