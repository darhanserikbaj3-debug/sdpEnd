package com.example.assic6.observer;

public interface Observer<E> {
    void onEvent(E event);
}