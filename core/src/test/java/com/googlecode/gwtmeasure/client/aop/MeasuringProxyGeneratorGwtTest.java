package com.googlecode.gwtmeasure.client.aop;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.googlecode.gwtmeasure.client.GwtTestConstants;
import com.googlecode.gwtmeasure.client.PendingMeasurement;
import com.googlecode.gwtmeasure.client.internal.VoidHub;
import com.googlecode.gwtmeasure.client.internal.VoidMeasurementListener;
import com.googlecode.gwtmeasure.shared.Measurements;
import com.googlecode.gwtmeasure.shared.OpenMeasurement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author dmitry.buzdin
 */
public class MeasuringProxyGeneratorGwtTest extends GWTTestCase {

    final List<String> triggeredEvents = new ArrayList<String>();

    MeasuredClass measured;

    @Override
    public String getModuleName() {
        return GwtTestConstants.MODULE_NAME;
    }

    @Override
    protected void gwtSetUp() throws Exception {
        super.gwtSetUp();

        Measurements.setClientImpl(new Measurements.Impl() {
            public OpenMeasurement run(String eventGroup, String subSystem) {
                triggeredEvents.add(eventGroup);

                VoidHub hub = new VoidHub();
                VoidMeasurementListener listener = new VoidMeasurementListener();
                PendingMeasurement measurement = new PendingMeasurement(hub, listener);
                measurement.start(eventGroup, subSystem);
                return measurement;
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

        assertEquals(1, triggeredEvents.size());

        String eventGroup = triggeredEvents.iterator().next();
        assertEquals("MeasuredClass.goToServer", eventGroup);
    }

    public void testVoidMethod() {
        measured.goToServer();
        assertTrue(measured.called);
        assertEquals(1, triggeredEvents.size());
    }

    public void testPrimitiveMethod() {
        int result = measured.getResult();
        assertEquals(42, result);
        assertEquals(1, triggeredEvents.size());
    }

    public void testArrayMethod() {
        int[] result = measured.array();
        assertTrue(Arrays.equals(new int[]{1, 2, 3}, result));
        assertEquals(1, triggeredEvents.size());
    }

    public void testObjectMethod() {
        Set<String> result = measured.strings();
        assertNotNull(result);
        assertEquals(1, triggeredEvents.size());
    }

    public void testPassObject() {
        measured.passObject(new Object());
        assertNotNull(measured.object);
        assertEquals(1, triggeredEvents.size());
    }

    public void testPassObjects() {
        measured.passObjects(new Object(), new Object());
        assertNotNull(measured.object);
        assertEquals(1, triggeredEvents.size());
    }

    public void testPassVarargs() {
        measured.passVarargs(new Object(), new Object());
        assertNotNull(measured.object);
        assertEquals(1, triggeredEvents.size());
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
            assertEquals(1, triggeredEvents.size());
        }
    }

    public void testCheckedException() {
        try {
            measured.throwsCheckedException();
            fail();
        } catch (IOException e) {
            assertEquals(1, triggeredEvents.size());
        }
    }

    public void testEnums() {
        MeasuredClass.Status result = measured.returnsEnum();
        assertNotNull(result);
        assertEquals(1, triggeredEvents.size());
    }

   public void testGenerics() {
       Object result = measured.returnsGenericType();
       assertNotNull(result);
       assertEquals(1, triggeredEvents.size());
   }

}
