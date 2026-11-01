package com.example.assic6.observer.listeners;

import com.example.assic6.observer.Observer;
import com.example.assic6.observer.SensorEvent;

public class AlertService implements Observer<SensorEvent> {
    @Override
    public void onEvent(SensorEvent event) {
        System.out.println("Alert in the area " + event.getDistrictId() + ": " + event.getValue());
    }
}