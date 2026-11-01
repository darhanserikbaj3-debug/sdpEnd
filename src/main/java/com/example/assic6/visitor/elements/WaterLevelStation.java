package com.example.assic6.visitor.elements;

import com.example.assic6.visitor.Element;
import com.example.assic6.visitor.Visitor;

public class WaterLevelStation implements Element {
    private final String id;
    private final double powerConsumption;

    public WaterLevelStation(String id, double powerConsumption) {
        this.id = id;
        this.powerConsumption = powerConsumption;
    }

    public String getId() { return id; }
    public double getPowerConsumption() { return powerConsumption; }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitWaterLevelStation(this);
    }
}