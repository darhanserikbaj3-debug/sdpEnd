package com.example.assic6.visitor.elements;

import com.example.assic6.visitor.Element;
import com.example.assic6.visitor.Visitor;
import java.util.ArrayList;
import java.util.List;

public class District implements Element {
    private final String id;
    private final String name;
    private final List<Element> devices = new ArrayList<>();

    public District(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addDevice(Element device) {
        devices.add(device);
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public List<Element> getDevices() { return devices; }

    @Override
    public <R> R accept(Visitor<R> visitor) {
        return visitor.visitDistrict(this);
    }
}