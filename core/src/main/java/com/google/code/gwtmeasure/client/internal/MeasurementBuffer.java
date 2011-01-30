package com.google.code.gwtmeasure.client.internal;

import com.google.code.gwtmeasure.shared.MetricEvent;

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

    private final List<MetricEvent> buffer = new ArrayList<MetricEvent>();

    public void push(MetricEvent event) {
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
