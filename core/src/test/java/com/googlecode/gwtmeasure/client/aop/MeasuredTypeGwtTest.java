package com.googlecode.gwtmeasure.client.aop;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.junit.client.GWTTestCase;
import com.googlecode.gwtmeasure.client.Configuration;
import com.googlecode.gwtmeasure.client.PerformanceEventHandler;
import com.googlecode.gwtmeasure.client.spi.MeasurementHub;
import com.googlecode.gwtmeasure.shared.PerformanceTiming;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author dmitry.buzdin
 */
public class MeasuredTypeGwtTest extends GWTTestCase {

    final Set<PerformanceTiming> triggeredEvents = new HashSet<PerformanceTiming>();
    MeasuredClass measured;

    @Override
    public String getModuleName() {
        return "com.googlecode.gwtmeasure.GWTMeasure";
    }

    @Override
    protected void gwtSetUp() throws Exception {
        super.gwtSetUp();

        Configuration.setMeasurementHub(new MeasurementHub() {
            public void submit(PerformanceTiming event) {
                triggeredEvents.add(event);
            }

            public HandlerRegistration addHandler(PerformanceEventHandler handler) {
                return null;
            }
        });

        measured = GWT.create(MeasuredClass.class);
    }

    @Override
    protected void gwtTearDown() throws Exception {
        super.gwtTearDown();

        triggeredEvents.clear();
    }

    public void testGeneration() {
        assertTrue(measured.getClass().getName().endsWith("_Proxy"));
        assertFalse(measured.called);
        assertNull(measured.object);
        assertTrue(triggeredEvents.isEmpty());
    }

    public void testEventContent() {
        measured.goToServer();

        assertEquals(2, triggeredEvents.size());

        PerformanceTiming timing = triggeredEvents.iterator().next();
        assertEquals("MeasuredClass.goToServer", timing.getEventGroup());
        assertEquals("MeasuredClass.goToServer", timing.getMethod());
    }

    public void testVoidMethod() {
        measured.goToServer();
        assertTrue(measured.called);
        assertEquals(2, triggeredEvents.size());
    }

    public void testPrimitiveMethod() {
        int result = measured.getResult();
        assertEquals(42, result);
        assertEquals(2, triggeredEvents.size());
    }

    public void testArrayMethod() {
        int[] result = measured.array();
        assertTrue(Arrays.equals(new int[]{1, 2, 3}, result));
        assertEquals(2, triggeredEvents.size());
    }

    public void testObjectMethod() {
        Set<String> result = measured.strings();
        assertNotNull(result);
        assertEquals(2, triggeredEvents.size());
    }

    public void testPassObject() {
        measured.passObject(new Object());
        assertNotNull(measured.object);
        assertEquals(2, triggeredEvents.size());
    }

    public void testPassObjects() {
        measured.passObjects(new Object(), new Object());
        assertNotNull(measured.object);
        assertEquals(2, triggeredEvents.size());
    }

    public void testPassVarargs() {
        measured.passVarargs(new Object(), new Object());
        assertNotNull(measured.object);
        assertEquals(2, triggeredEvents.size());
    }

    public void testFinalMethod() {
        measured.notMeasured();
        assertTrue(measured.called);
        assertEquals(0, triggeredEvents.size());
    }

    public void testRuntimeException() {
        try {
            measured.throwsRuntimeException();
            fail();
        } catch (NullPointerException e) {
            assertEquals(0, triggeredEvents.size());
        }
    }

    public void testCheckedException() {
        try {
            measured.throwsCheckedException();
            fail();
        } catch (IOException e) {
            assertEquals(0, triggeredEvents.size());
        }
    }

    public void testEnums() {
        MeasuredClass.Status result = measured.returnsEnum();
        assertNotNull(result);
        assertEquals(2, triggeredEvents.size());
    }

   public void testGenerics() {
       Object result = measured.returnsGenericType();
       assertNotNull(result);
       assertEquals(2, triggeredEvents.size());
   }

}
