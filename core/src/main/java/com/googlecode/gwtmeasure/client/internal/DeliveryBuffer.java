package com.googlecode.gwtmeasure.client.internal;

import com.googlecode.gwtmeasure.client.exception.IncidentReport;
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

    private final List<PerformanceTiming> buffer = new ArrayList<PerformanceTiming>();    
    private final List<IncidentReport> incidents = new ArrayList<IncidentReport>();

    public void push(PerformanceTiming timing) {
        buffer.add(timing);
    }

    public Object[] popAll() {
        Object[] result = buffer.toArray();
        buffer.clear();
        return result;
    }

    public boolean isEmpty() {
        return buffer.isEmpty();
    }

    public void register(IncidentReport report) {
        incidents.add(report);
    }

}
