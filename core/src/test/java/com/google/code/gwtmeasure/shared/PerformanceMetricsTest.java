package com.google.code.gwtmeasure.shared;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

/**
 * @author dmitry.buzdin
 */
public class PerformanceMetricsTest extends Assert {

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
                .setParameter(Constants.PARAM_SESSION_ID, "sessionId")
                .setParameter(Constants.PARAM_METHOD, "method")
                .setParameter(Constants.PARAM_BYTES, 200)       
                .create();
    }

    @Test
    public void testEncode() throws Exception {
        String encodedEvent = event.jsonEncode();
        
        assertThat(encodedEvent, equalTo(RAW_STRING));
    }


    @Test
    public void shouldEncodeEmptyMessage() throws Exception {
        PerformanceMetrics event = new PerformanceMetrics();
        String encodedEvent = event.jsonEncode();
        assertThat(encodedEvent, equalTo("|||0|||0|"));
    }

}
