package com.example.assic6;

import com.example.assic6.observer.EventType;
import com.example.assic6.observer.listeners.*;
import com.example.assic6.observer.subjects.*;
import com.example.assic6.visitor.elements.*;
import com.example.assic6.visitor.visitors.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== 1. CREATING CITY TREE (COMPOSITE TREE) ===");
        City astana = new City("Astana");

        District district1 = new District("D-01", "Esil");
        district1.addDevice(new LightSensor("LS-101", 1.2));
        district1.addDevice(new TrafficCamera("TC-201", 3.5));
        district1.addDevice(new AirQualityDevice("AQ-301", 2.0, true)); // Needs maintenance!

        District district2 = new District("D-02", "Almaty");
        district2.addDevice(new WaterLevelStation("WS-401", 4.0));
        district2.addDevice(new AirQualityDevice("AQ-302", 2.0, false));

        astana.addDistrict(district1);
        astana.addDistrict(district2);

        System.out.println("\n=== 2. TESTING VISITORS ===");
        
        TotalPowerVisitor powerVisitor = new TotalPowerVisitor();
        double totalPower = astana.accept(powerVisitor);
        System.out.println("Total City Power Consumption: " + totalPower + " kW");

        CountDevicesVisitor countVisitor = new CountDevicesVisitor();
        int totalDevices = astana.accept(countVisitor);
        System.out.println("Total Devices in City: " + totalDevices);

        JsonExportVisitor jsonVisitor = new JsonExportVisitor();
        System.out.println("\nExporting City Structure to JSON:");
        System.out.println(astana.accept(jsonVisitor));

        System.out.println("\n=== 3. TESTING OBSERVER + VISITOR INTEGRATION ===");
        
        AirQualitySensor airSensor = new AirQualitySensor("AQ-301", "D-01");

        CityDashboard dashboard = new CityDashboard();
        InspectionAlertService inspectionService = new InspectionAlertService(astana);

        airSensor.registerObserver(EventType.AIR_QUALITY_ALERT, dashboard);
        airSensor.registerObserver(EventType.AIR_QUALITY_ALERT, inspectionService);

        airSensor.checkAirQuality(155.0);
    }
}