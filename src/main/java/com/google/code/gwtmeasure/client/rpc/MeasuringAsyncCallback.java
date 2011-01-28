package com.google.code.gwtmeasure.client.rpc;

import com.google.code.gwtmeasure.client.Measurements;
import com.google.code.gwtmeasure.client.PendingMeasurement;
import com.google.code.gwtmeasure.shared.Constants;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class MeasuringAsyncCallback<T> implements AsyncCallback<T> {

    final AsyncCallback<T> originalCallback;
    final PendingMeasurement measurement;

    public MeasuringAsyncCallback(AsyncCallback<T> originalCallback) {
        this.originalCallback = originalCallback;
        String callbackType = originalCallback.getClass().getName();
        this.measurement = Measurements.start(callbackType, Constants.GROUP_CALLBACK);
    }

    public void onSuccess(T result) {
        try {
            originalCallback.onSuccess(result);
        } catch (RuntimeException exception) {
            measurement.discard();
            throw exception;
        } finally {
            measurement.stop();
        }
    }

    public void onFailure(Throwable caught) {
        measurement.discard();
        originalCallback.onFailure(caught);
    }

}