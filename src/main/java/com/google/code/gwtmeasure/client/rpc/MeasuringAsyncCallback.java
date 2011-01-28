package com.google.code.gwtmeasure.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class MeasuringAsyncCallback<T> implements AsyncCallback<T> {

    final AsyncCallback<T> original;
    private String correlationId;

    public MeasuringAsyncCallback(AsyncCallback<T> original) {
        this.original = original;
    }

    public void onSuccess(T result) {
        try {
            original.onSuccess(result);
        } finally {
        }
    }

    public void onFailure(Throwable caught) {
        original.onFailure(caught);
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }
}