package com.googlecode.gwtmeasure.client.internal;

import com.googlecode.gwtmeasure.shared.PerformanceMetrics;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dmitry.buzdin
 */
public class MeasurementBuffer {

    private static final MeasurementBuffer instance = new MeasurementBuffer();

    public static MeasurementBuffer instance() {
        return instance;
    }

    private MeasurementBuffer() {
    }

    private final List<PerformanceMetrics> buffer = new ArrayList<PerformanceMetrics>();

    public void push(PerformanceMetrics event) {
        buffer.add(event);
    }

    public Object[] popAll() {
        Object[] result = buffer.toArray();
        buffer.clear();
        return result;
    }

    public boolean isEmpty() {
        return buffer.isEmpty();
    }

}
