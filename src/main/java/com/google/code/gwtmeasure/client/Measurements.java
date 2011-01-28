package com.google.code.gwtmeasure.client;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public final class Measurements {

    private Measurements() {
    }

    public PendingMeasurement start(String name) {
        return start(name, "");
    }


    public PendingMeasurement start(String name, String group) {
        return new PendingMeasurement(name, group);
    }

}
