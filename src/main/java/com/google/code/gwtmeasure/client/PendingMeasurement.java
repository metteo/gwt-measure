package com.google.code.gwtmeasure.client;

import com.google.code.gwtmeasure.client.internal.TimeUtils;
import com.google.code.gwtmeasure.client.spi.DeliveryChannel;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class PendingMeasurement {

    private long from;
    private long to;
    private String name;
    private String group;
    private DeliveryChannel deliveryChannel;
    private boolean discarded;

    {
        this.from = TimeUtils.current();
    }

    public PendingMeasurement(String name, String group, DeliveryChannel deliveryChannel) {
        this.name = name;
        this.group = group;
        this.deliveryChannel = deliveryChannel;
    }

    public void stop() {
        if (!discarded) {
            this.to = TimeUtils.current();
            deliveryChannel.submit(this);
        }
    }

    public void discard() {
        this.discarded = true;
    }

    public long getFrom() {
        return from;
    }

    public long getTo() {
        return to;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public boolean isDiscarded() {
        return discarded;
    }
    
}
