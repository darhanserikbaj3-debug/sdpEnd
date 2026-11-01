package com.example.assic6.observer.listeners;

import com.example.assic6.observer.Observer;
import com.example.assic6.observer.SensorEvent;

public class StatisticsTracker implements Observer<SensorEvent> {
    private int totalEventsReceived = 0;

    @Override
    public synchronized void onEvent(SensorEvent event) {
        totalEventsReceived++;
        System.out.println("Total amount of events: " + totalEventsReceived);
    }

    public synchronized int getTotalEventsReceived() {
        return totalEventsReceived;
    }
}