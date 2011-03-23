package com.googlecode.gwtmeasure.client.internal;

import com.googlecode.gwtmeasure.shared.IncidentReport;
import com.googlecode.gwtmeasure.shared.PerformanceTiming;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dmitry.buzdin
 */
public class DeliveryBuffer {

    private static final DeliveryBuffer instance = new DeliveryBuffer();

    public static DeliveryBuffer instance() {
        return instance;
    }

    private DeliveryBuffer() {
    }

    private final List<PerformanceTiming> timings = new ArrayList<PerformanceTiming>();
    private final List<IncidentReport> incidents = new ArrayList<IncidentReport>();

    public void pushTiming(List<PerformanceTiming> newTimings) {
        timings.addAll(newTimings);
    }

    public void pushTiming(PerformanceTiming timing) {
        timings.add(timing);
    }

    public void pushIncident(List<IncidentReport> reports) {
        incidents.addAll(reports);
    }

    public void pushIncident(IncidentReport report) {
        incidents.add(report);
    }

    public List<PerformanceTiming> popTimings() {
        List<PerformanceTiming> result = new ArrayList<PerformanceTiming>(timings);
        timings.clear();
        return result;
    }

    public List<IncidentReport> popIncidents() {
        List<IncidentReport> result = new ArrayList<IncidentReport>(incidents);
        incidents.clear();
        return result;
    }

    public boolean hasTimings() {
        return !timings.isEmpty();
    }

    public boolean hasIncidents() {
        return !incidents.isEmpty();
    }

}
