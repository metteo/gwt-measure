package com.google.code.gwtmeasure.client.spi;

import com.google.code.gwtmeasure.client.PendingMeasurement;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public interface DeliveryChannel {

    void submit(PendingMeasurement measurement);

}
