package com.google.code.gwtmeasure.client;

import com.google.code.gwtmeasure.client.spi.DeliveryChannel;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public final class Measurements {

    private static DeliveryChannel deliveryChannel;

    private Measurements() {
    }

    public static void setDeliveryChannel(DeliveryChannel deliveryChannel) {
        Measurements.deliveryChannel = deliveryChannel;
    }

    public static PendingMeasurement start(String name) {
        return start(name, "");
    }

    public static PendingMeasurement start(String name, String group) {
        return new PendingMeasurement(name, group, deliveryChannel);
    }

}
