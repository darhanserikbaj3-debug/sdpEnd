package com.example.assic6.visitor.visitors;

import com.example.assic6.visitor.Visitor;
import com.example.assic6.visitor.elements.*;

public class JsonExportVisitor implements Visitor<String> {

    @Override
    public String visitCity(City city) {
        StringBuilder json = new StringBuilder();
        json.append("{\n  \"city\": \"").append(city.getName()).append("\",\n  \"districts\": [\n");
        for (int i = 0; i < city.getDistricts().size(); i++) {
            json.append(city.getDistricts().get(i).accept(this));
            if (i < city.getDistricts().size() - 1) json.append(",");
            json.append("\n");
        }
        json.append("  ]\n}");
        return json.toString();
    }

    @Override
    public String visitDistrict(District district) {
        StringBuilder json = new StringBuilder();
        json.append("    {\n      \"id\": \"").append(district.getId()).append("\",\n")
            .append("      \"name\": \"").append(district.getName()).append("\",\n")
            .append("      \"devices\": [\n");
        for (int i = 0; i < district.getDevices().size(); i++) {
            json.append(district.getDevices().get(i).accept(this));
            if (i < district.getDevices().size() - 1) json.append(",");
            json.append("\n");
        }
        json.append("      ]\n    }");
        return json.toString();
    }

    @Override
    public String visitLightSensor(LightSensor s) {
        return "        {\"type\": \"LightSensor\", \"id\": \"" + s.getId() + "\"}";
    }

    @Override
    public String visitTrafficCamera(TrafficCamera c) {
        return "        {\"type\": \"TrafficCamera\", \"id\": \"" + c.getId() + "\"}";
    }

    @Override
    public String visitAirQualityDevice(AirQualityDevice a) {
        return "        {\"type\": \"AirQualityDevice\", \"id\": \"" + a.getId() + "\", \"maintenance\": " + a.isNeedsMaintenance() + "}";
    }

    @Override
    public String visitWaterLevelStation(WaterLevelStation w) {
        return "        {\"type\": \"WaterLevelStation\", \"id\": \"" + w.getId() + "\"}";
    }
}