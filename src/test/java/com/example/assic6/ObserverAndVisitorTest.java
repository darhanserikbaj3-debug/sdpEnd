package com.example.assic6;

import com.example.assic6.observer.EventType;
import com.example.assic6.observer.Observer;
import com.example.assic6.observer.SensorEvent;
import com.example.assic6.observer.listeners.*;
import com.example.assic6.observer.subjects.*;
import com.example.assic6.visitor.elements.*;
import com.example.assic6.visitor.visitors.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class ObserverAndVisitorTest {

    private City testCity;
    private District district1;
    private District district2;

    @BeforeEach
    void setUp() {
        testCity = new City("TestCity");
        district1 = new District("D-01", "Center");
        district2 = new District("D-02", "North");

        district1.addDevice(new LightSensor("LS-1", 10.0));
        district1.addDevice(new TrafficCamera("TC-1", 20.0));
        district1.addDevice(new AirQualityDevice("AQ-1", 15.0, true));

        district2.addDevice(new WaterLevelStation("WS-1", 30.0));
        district2.addDevice(new AirQualityDevice("AQ-2", 15.0, false));

        testCity.addDistrict(district1);
        testCity.addDistrict(district2);
    }


    @Test
    @DisplayName("TotalPowerVisitor should accurately calculate total energy")
    void testTotalPowerVisitor() {
        TotalPowerVisitor visitor = new TotalPowerVisitor();
        double totalPower = testCity.accept(visitor);
        assertEquals(90.0, totalPower, 0.001, "10 + 20 + 15 + 30 + 15 = 90.0");
    }

    @Test
    @DisplayName("CountDevicesVisitor should return exact total device count")
    void testCountDevicesVisitor() {
        CountDevicesVisitor visitor = new CountDevicesVisitor();
        int totalDevices = testCity.accept(visitor);
        assertEquals(5, totalDevices);
    }

    @Test
    @DisplayName("MaintenanceInspectionVisitor should filter only devices that need repair")
    void testMaintenanceInspectionVisitor() {
        MaintenanceInspectionVisitor visitor = new MaintenanceInspectionVisitor();
        List<String> reports = testCity.accept(visitor);
        assertEquals(1, reports.size());
        assertTrue(reports.get(0).contains("AQ-1"));
    }

    @Test
    @DisplayName("JsonExportVisitor should produce non-empty valid JSON string")
    void testJsonExportVisitor() {
        JsonExportVisitor visitor = new JsonExportVisitor();
        String json = testCity.accept(visitor);
        assertNotNull(json);
        assertTrue(json.contains("\"city\": \"TestCity\""));
        assertTrue(json.contains("\"id\": \"D-01\""));
    }

    @Test
    @DisplayName("Visitor should calculate power for a single subtree (District)")
    void testSubtreeVisitorCalculation() {
        TotalPowerVisitor visitor = new TotalPowerVisitor();
        double districtPower = district1.accept(visitor);
        assertEquals(45.0, districtPower, 0.001);
    }

    // --- OBSERVER TESTS ---

    @Test
    @DisplayName("Observer should receive notified event")
    void testObserverNotification() {
        AirQualitySensor sensor = new AirQualitySensor("AQ-100", "D-01");
        StatisticsTracker tracker = new StatisticsTracker();

        sensor.registerObserver(EventType.AIR_QUALITY_ALERT, tracker);
        sensor.checkAirQuality(150.0);

        assertEquals(1, tracker.getTotalEventsReceived());
    }

    @Test
    @DisplayName("Observer should not receive event below threshold")
    void testObserverThresholdFilter() {
        AirQualitySensor sensor = new AirQualitySensor("AQ-100", "D-01");
        StatisticsTracker tracker = new StatisticsTracker();

        sensor.registerObserver(EventType.AIR_QUALITY_ALERT, tracker);
        sensor.checkAirQuality(50.0); // AQI <= 100 doesn't fire event

        assertEquals(0, tracker.getTotalEventsReceived());
    }

    @Test
    @DisplayName("Unregistered observer should not receive events")
    void testUnregisterObserver() {
        AirQualitySensor sensor = new AirQualitySensor("AQ-100", "D-01");
        StatisticsTracker tracker = new StatisticsTracker();

        sensor.registerObserver(EventType.AIR_QUALITY_ALERT, tracker);
        sensor.unregisterObserver(EventType.AIR_QUALITY_ALERT, tracker);
        sensor.checkAirQuality(150.0);

        assertEquals(0, tracker.getTotalEventsReceived());
    }

    @Test
    @DisplayName("DistrictAggregator should chain and republish aggregated event")
    void testObserverChain() {
        AirQualitySensor sensor = new AirQualitySensor("AQ-100", "D-01");
        DistrictAggregator aggregator = new DistrictAggregator("D-01");
        StatisticsTracker finalTracker = new StatisticsTracker();

        sensor.registerObserver(EventType.AIR_QUALITY_ALERT, aggregator);
        aggregator.registerObserver(EventType.AGGREGATED_DISTRICT_DATA, finalTracker);

        sensor.checkAirQuality(120.0);

        assertEquals(1, finalTracker.getTotalEventsReceived());
    }

    @Test
    @DisplayName("Listener unregistering itself during execution should not throw ConcurrentModificationException")
    void testRuntimeUnsubscribeSelf() {
        AirQualitySensor sensor = new AirQualitySensor("AQ-100", "D-01");

        Observer<SensorEvent> selfRemovingListener = new Observer<>() {
            @Override
            public void onEvent(SensorEvent event) {
                sensor.unregisterObserver(EventType.AIR_QUALITY_ALERT, this);
            }
        };

        sensor.registerObserver(EventType.AIR_QUALITY_ALERT, selfRemovingListener);
        assertDoesNotThrow(() -> sensor.checkAirQuality(150.0));
    }

    @Test
    @DisplayName("Multiple listeners should all receive notification")
    void testMultipleListeners() {
        AirQualitySensor sensor = new AirQualitySensor("AQ-100", "D-01");
        StatisticsTracker tracker1 = new StatisticsTracker();
        StatisticsTracker tracker2 = new StatisticsTracker();

        sensor.registerObserver(EventType.AIR_QUALITY_ALERT, tracker1);
        sensor.registerObserver(EventType.AIR_QUALITY_ALERT, tracker2);

        sensor.checkAirQuality(150.0);

        assertEquals(1, tracker1.getTotalEventsReceived());
        assertEquals(1, tracker2.getTotalEventsReceived());
    }

    @Test
    @DisplayName("TrafficSensor should trigger TRAFFIC_CONGESTION event")
    void testTrafficSensorTrigger() {
        TrafficSensor trafficSensor = new TrafficSensor("TS-1", "D-01");
        StatisticsTracker tracker = new StatisticsTracker();

        trafficSensor.registerObserver(EventType.TRAFFIC_CONGESTION, tracker);
        trafficSensor.checkTraffic(90.0); // > 80%

        assertEquals(1, tracker.getTotalEventsReceived());
    }

    @Test
    @DisplayName("InspectionAlertService integration should handle non-existing district gracefully")
    void testIntegrationWithNonExistingDistrict() {
        AirQualitySensor sensor = new AirQualitySensor("AQ-100", "NON-EXISTENT");
        InspectionAlertService service = new InspectionAlertService(testCity);

        sensor.registerObserver(EventType.AIR_QUALITY_ALERT, service);
        assertDoesNotThrow(() -> sensor.checkAirQuality(150.0));
    }

    @Test
    @DisplayName("Event getters should return correct values")
    void testImmutableSensorEvent() {
        SensorEvent event = new SensorEvent(EventType.AIR_QUALITY_ALERT, "D-01", "S-1", 120.5);
        assertEquals(EventType.AIR_QUALITY_ALERT, event.getType());
        assertEquals("D-01", event.getDistrictId());
        assertEquals("S-1", event.getSensorId());
        assertEquals(120.5, event.getValue());
    }

    // --- CONCURRENCY STRESS TEST (REQUIREMENT: 4 threads x 1000 events) ---

    @Test
    @DisplayName("STRESS TEST: 4 threads x 1000 events = exactly 4000 received events with graceful shutdown")
    void testConcurrencyStressTest() throws InterruptedException {
        int threads = 4;
        int eventsPerThread = 1000;
        int expectedTotalEvents = threads * eventsPerThread;

        AirQualitySensor sensor = new AirQualitySensor("STRESS-AQ", "D-01");
        StatisticsTracker tracker = new StatisticsTracker();
        sensor.registerObserver(EventType.AIR_QUALITY_ALERT, tracker);

        ExecutorService executor = Executors.newFixedThreadPool(threads);

        for (int i = 0; i < threads; i++) {
            executor.submit(new SensorDataProducer(sensor, eventsPerThread));
        }

        // Graceful shutdown
        executor.shutdown();
        boolean terminated = executor.awaitTermination(10, TimeUnit.SECONDS);

        assertTrue(terminated, "Executor service should shutdown gracefully without hanging");
        assertEquals(expectedTotalEvents, tracker.getTotalEventsReceived(),
                "Tracker should process exactly 4,000 events across 4 concurrent threads");
    }
}