package com.example.assic6.visitor.elements;

import com.example.assic6.visitor.Element;
import com.example.assic6.visitor.Visitor;

public class LightSensor implements Element {
    private final String id;
    private final double powerConsumption; 

    public LightSensor(String id, double powerConsumption) {
        this.id = id;
        this.powerConsumption = powerConsumption;
    }

    public String getId() { return id; }
    public double getPowerConsumption() { return powerConsumption; }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitLightSensor(this);
    }
}