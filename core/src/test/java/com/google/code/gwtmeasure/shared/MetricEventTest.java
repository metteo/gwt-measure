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

    private static final String RAW_STRING = "moduleName|subSystem|eventGroup|100|type";
    private MetricEvent event;

    @Before
    public void setUp() {
        event = new MetricEvent.Builder()
                .setModuleName("moduleName")
                .setEventGroup("eventGroup")
                .setSubSystem("subSystem")
                .setType("type")
                .setMillis(100)
                .create();
    }

    @Test
    public void testEncode() throws Exception {
        String encodedEvent = event.encode();
        
        assertThat(encodedEvent, equalTo(RAW_STRING));
    }

    @Test
    public void testDecode() throws Exception {
        MetricEvent decodedEvent = MetricEvent.decode(RAW_STRING);

        assertThat(decodedEvent, is(event));
    }

}
