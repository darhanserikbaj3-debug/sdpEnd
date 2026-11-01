package com.example.assic6.visitor.elements;

import com.example.assic6.visitor.Element;
import com.example.assic6.visitor.Visitor;
import java.util.ArrayList;
import java.util.List;

public class City implements Element {
    private final String name;
    private final List<District> districts = new ArrayList<>();

    public City(String name) {
        this.name = name;
    }

    public void addDistrict(District district) {
        districts.add(district);
    }

    public String getName() { return name; }
    public List<District> getDistricts() { return districts; }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitCity(this);
    }
}