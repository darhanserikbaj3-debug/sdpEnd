package com.example.assic6.visitor.elements;

import com.example.assic6.visitor.Element;
import com.example.assic6.visitor.Visitor;

public class AirQualityDevice implements Element {
    private final String id;
    private final double powerConsumption;
    private final boolean needsMaintenance;

    public AirQualityDevice(String id, double powerConsumption, boolean needsMaintenance) {
        this.id = id;
        this.powerConsumption = powerConsumption;
        this.needsMaintenance = needsMaintenance;
    }

    public String getId() { return id; }
    public double getPowerConsumption() { return powerConsumption; }
    public boolean isNeedsMaintenance() { return needsMaintenance; }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitAirQualityDevice(this);
    }
}