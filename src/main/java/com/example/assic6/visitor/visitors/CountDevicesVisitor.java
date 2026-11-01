package com.example.assic6.visitor.visitors;

import com.example.assic6.visitor.Element;
import com.example.assic6.visitor.Visitor;
import com.example.assic6.visitor.elements.*;

public class CountDevicesVisitor implements Visitor<Integer> {

    @Override
    public Integer visitCity(City city) {
        int count = 0;
        for (District district : city.getDistricts()) {
            count += district.accept(this);
        }
        return count;
    }

    @Override
    public Integer visitDistrict(District district) {
        int count = 0;
        for (Element device : district.getDevices()) {
            count += device.accept(this);
        }
        return count;
    }

    @Override public Integer visitLightSensor(LightSensor s) { return 1; }
    @Override public Integer visitTrafficCamera(TrafficCamera c) { return 1; }
    @Override public Integer visitAirQualityDevice(AirQualityDevice a) { return 1; }
    @Override public Integer visitWaterLevelStation(WaterLevelStation w) { return 1; }
}