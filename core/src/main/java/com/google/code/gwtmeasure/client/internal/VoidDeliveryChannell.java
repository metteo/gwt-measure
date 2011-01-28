package com.google.code.gwtmeasure.client.internal;

import com.google.code.gwtmeasure.client.PendingMeasurement;
import com.google.code.gwtmeasure.client.spi.DeliveryChannel;
import com.google.gwt.core.client.GWT;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class VoidDeliveryChannell implements DeliveryChannel {

    public void submit(PendingMeasurement measurement) {
    }

}
