package com.example.assic6.observer;

public final class SensorEvent {
    private final EventType type;
    private final String districtId;
    private final String sensorId;
    private final double value;

    public SensorEvent(EventType type, String districtId, String sensorId, double value) {
        this.type = type;
        this.districtId = districtId;
        this.sensorId = sensorId;
        this.value = value;
    }

    public EventType getType() {
        return type;
    }

    public String getDistrictId() {
        return districtId;
    }

    public String getSensorId() {
        return sensorId;
    }

    public double getValue() {
        return value;
    }
}