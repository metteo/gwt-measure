package com.google.code.gwtmeasure.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class MeasuringAsyncCallback<T> implements AsyncCallback<T> {

    final AsyncCallback<T> original;
    final MeasurementTree clientTree = ClientMeasurementStorage.startAction();
    private String correlationId;

    public MeasuringAsyncCallback(AsyncCallback<T> original) {
        this.original = original;
    }

    public void onSuccess(T result) {
        if (correlationId != null) {
            clientTree.setAttribute(MeasureAttributes.CORRELATION_ID, correlationId);
        }
        MeasurementTree serverTree = ((MeasurementSupport) result).getMeasurementTree();
        ClientMeasurementStorage.stopRemoteCall(clientTree, serverTree);

        try {
            original.onSuccess(result);
        } finally {
            ClientMeasurementStorage.stopClientAction();
        }
    }

    public void onFailure(Throwable caught) {
        original.onFailure(caught);
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }
}