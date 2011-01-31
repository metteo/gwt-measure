package com.google.code.gwtmeasure.shared;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

/**
 * @author dmitry.buzdin
 */
public class MetricEventTest extends Assert {

    private static final String RAW_STRING = "moduleName|subSystem|eventGroup|100|type|sessionId|200|method";
    private PerformanceMetrics event;

    @Before
    public void setUp() {
        event = new PerformanceMetrics.Builder()
                .setModuleName("moduleName")
                .setEventGroup("eventGroup")
                .setSubSystem("subSystem")
                .setType("type")
                .setMillis(100)
                .setSessionId("sessionId")
                .setMethod("method")
                .setBytes(200)       
                .create();
    }

    @Test
    public void testEncode() throws Exception {
        String encodedEvent = event.encode();
        
        assertThat(encodedEvent, equalTo(RAW_STRING));
    }

    @Test
    public void testDecode() throws Exception {
        PerformanceMetrics decodedEvent = PerformanceMetrics.decode(RAW_STRING);

        assertThat(decodedEvent, is(event));
    }

    @Test
    public void shouldEncodeEmptyMessage() throws Exception {
        PerformanceMetrics event = new PerformanceMetrics();
        String encodedEvent = event.encode();
        assertThat(encodedEvent, equalTo("|||0|||0|"));
    }

    @Test
    public void shouldWorkWithEmptyMessage() throws Exception {
        PerformanceMetrics event = new PerformanceMetrics.Builder()
                .create();
        String encodedEvent = event.encode();

        PerformanceMetrics decodedEvent = PerformanceMetrics.decode(encodedEvent);

        assertThat(decodedEvent, equalTo(event));
    }

}
