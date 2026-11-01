package com.example.assic6.visitor;

import com.example.assic6.visitor.elements.*;

public interface Visitor<R> {
    R visitCity(City city);
    R visitDistrict(District district);
    R visitLightSensor(LightSensor lightSensor);
    R visitTrafficCamera(TrafficCamera camera);
    R visitAirQualityDevice(AirQualityDevice device);
    R visitWaterLevelStation(WaterLevelStation station);
}