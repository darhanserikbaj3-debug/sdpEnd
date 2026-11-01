package com.example.assic6.observer.listeners;

import com.example.assic6.observer.AbstractSubject;
import com.example.assic6.observer.EventType;
import com.example.assic6.observer.Observer;
import com.example.assic6.observer.SensorEvent;

public class DistrictAggregator extends AbstractSubject implements Observer<SensorEvent> {
    private final String districtId;
    private double totalValue = 0;
    private int count = 0;

    public DistrictAggregator(String districtId) {
        this.districtId = districtId;
    }

    @Override
    public synchronized void onEvent(SensorEvent event) {
        if (event.getDistrictId().equals(this.districtId)) {
            totalValue += event.getValue();
            count++;
            double average = totalValue / count;

            SensorEvent aggregatedEvent = new SensorEvent(
                EventType.AGGREGATED_DISTRICT_DATA,
                districtId,
                "AGGREGATOR-" + districtId,
                average
            );
            notifyObservers(aggregatedEvent);
        }
    }
}