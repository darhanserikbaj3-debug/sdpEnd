package com.example.assic6.visitor.visitors;

import com.example.assic6.visitor.Element;
import com.example.assic6.visitor.Visitor;
import com.example.assic6.visitor.elements.*;

public class TotalPowerVisitor implements Visitor<Double> {

    @Override
    public Double visitCity(City city) {
        double total = 0;
        for (District district : city.getDistricts()) {
            total += district.accept(this);
        }
        return total;
    }

    @Override
    public Double visitDistrict(District district) {
        double total = 0;
        for (Element device : district.getDevices()) {
            total += device.accept(this);
        }
        return total;
    }

    @Override public Double visitLightSensor(LightSensor s) { return s.getPowerConsumption(); }
    @Override public Double visitTrafficCamera(TrafficCamera c) { return c.getPowerConsumption(); }
    @Override public Double visitAirQualityDevice(AirQualityDevice a) { return a.getPowerConsumption(); }
    @Override public Double visitWaterLevelStation(WaterLevelStation w) { return w.getPowerConsumption(); }
}