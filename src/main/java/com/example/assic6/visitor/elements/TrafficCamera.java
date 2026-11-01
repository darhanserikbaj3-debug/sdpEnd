package com.example.assic6.visitor.elements;

import com.example.assic6.visitor.Element;
import com.example.assic6.visitor.Visitor;

public class TrafficCamera implements Element {
    private final String id;
    private final double powerConsumption;

    public TrafficCamera(String id, double powerConsumption) {
        this.id = id;
        this.powerConsumption = powerConsumption;
    }

    public String getId() { return id; }
    public double getPowerConsumption() { return powerConsumption; }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitTrafficCamera(this);
    }
}