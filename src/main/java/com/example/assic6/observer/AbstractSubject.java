package com.example.assic6.observer;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class AbstractSubject implements Subject<EventType, SensorEvent> {
    private final Map<EventType, List<Observer<SensorEvent>>> listenersMap = new ConcurrentHashMap<>();

    @Override
    public void registerObserver(EventType type, Observer<SensorEvent> observer) {
        listenersMap.computeIfAbsent(type, k -> new CopyOnWriteArrayList<>()).add(observer);
    }

    @Override
    public void unregisterObserver(EventType type, Observer<SensorEvent> observer) {
        List<Observer<SensorEvent>> listeners = listenersMap.get(type);
        if (listeners != null) {
            listeners.remove(observer);
        }
    }

    @Override
    public void notifyObservers(SensorEvent event) {
        List<Observer<SensorEvent>> listeners = listenersMap.get(event.getType());
        if (listeners != null) {
            for (Observer<SensorEvent> observer : listeners) {
                observer.onEvent(event);
            }
        }
    }
}