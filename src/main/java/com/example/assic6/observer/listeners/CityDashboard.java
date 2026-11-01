package com.example.assic6.observer.listeners;

import com.example.assic6.observer.Observer;
import com.example.assic6.observer.SensorEvent;

public class CityDashboard implements Observer<SensorEvent> {
    @Override
    public void onEvent(SensorEvent event) {
        System.out.println("Dashboard update: " + event.getType() + " = " + event.getValue());
    }
}