package com.example.assic6.visitor.visitors;

import com.example.assic6.visitor.Element;
import com.example.assic6.visitor.Visitor;
import com.example.assic6.visitor.elements.*;
import java.util.ArrayList;
import java.util.List;

public class MaintenanceInspectionVisitor implements Visitor<List<String>> {

    @Override
    public List<String> visitCity(City city) {
        List<String> result = new ArrayList<>();
        for (District district : city.getDistricts()) {
            result.addAll(district.accept(this));
        }
        return result;
    }

    @Override
    public List<String> visitDistrict(District district) {
        List<String> result = new ArrayList<>();
        for (Element device : district.getDevices()) {
            result.addAll(device.accept(this));
        }
        return result;
    }

    @Override public List<String> visitLightSensor(LightSensor s) { return List.of(); }
    @Override public List<String> visitTrafficCamera(TrafficCamera c) { return List.of(); }

    @Override
    public List<String> visitAirQualityDevice(AirQualityDevice a) {
        if (a.isNeedsMaintenance()) {
            return List.of("AirQualityDevice [" + a.getId() + "] Needs repair");
        }
        return List.of();
    }

    @Override public List<String> visitWaterLevelStation(WaterLevelStation w) { return List.of(); }
}