package com.example.assic6.observer;

public interface Subject<T, E> {
    void registerObserver(T type, Observer<E> observer);
    void unregisterObserver(T type, Observer<E> observer);
    void notifyObservers(E event);
}