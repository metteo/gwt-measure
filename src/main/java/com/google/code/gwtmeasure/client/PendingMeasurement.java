package com.google.code.gwtmeasure.client;

import com.google.code.gwtmeasure.client.internal.TimeUtils;

import java.util.Date;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class PendingMeasurement {

    private long created;
    private long stopped;
    private String name;
    private String group;
    private boolean discarded;

    {
        this.created = TimeUtils.current();
    }

    public PendingMeasurement(String name, String group) {
        this.name = name;
        this.group = group;
    }

    public void stop() {
        this.stopped = TimeUtils.current();
    }

    public void discard() {
        this.discarded = true;
    }

}
