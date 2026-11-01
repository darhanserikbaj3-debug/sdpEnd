package com.example.assic6.observer.listeners;

import com.example.assic6.observer.Observer;
import com.example.assic6.observer.SensorEvent;
import com.example.assic6.visitor.elements.City;
import com.example.assic6.visitor.elements.District;
import com.example.assic6.visitor.visitors.MaintenanceInspectionVisitor;

import java.util.List;

public class InspectionAlertService implements Observer<SensorEvent> {
    private final City city;

    public InspectionAlertService(City city) {
        this.city = city;
    }

    @Override
    public void onEvent(SensorEvent event) {
        System.out.println("\nAlert received for district: " + event.getDistrictId());
        
        // Find the target district (subtree)
        District targetDistrict = city.getDistricts().stream()
                .filter(d -> d.getId().equals(event.getDistrictId()))
                .findFirst()
                .orElse(null);

        if (targetDistrict != null) {
            MaintenanceInspectionVisitor visitor = new MaintenanceInspectionVisitor();
            List<String> inspectionReport = targetDistrict.accept(visitor);

            System.out.println("=== Inspection Report for District: " + targetDistrict.getName() + " ===");
            if (inspectionReport.isEmpty()) {
                System.out.println("All devices are operating normally.");
            } else {
                inspectionReport.forEach(System.out::println);
            }
        }
    }
}