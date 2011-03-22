package com.googlecode.gwtmeasure.client.internal;

import com.googlecode.gwtmeasure.shared.PerformanceTiming;

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

    private final List<PerformanceTiming> buffer = new ArrayList<PerformanceTiming>();

    public void push(PerformanceTiming event) {
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
