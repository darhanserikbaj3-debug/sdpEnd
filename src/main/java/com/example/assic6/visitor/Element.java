package com.example.assic6.visitor;

public interface Element {
    <R> R accept(Visitor<R> visitor);
}