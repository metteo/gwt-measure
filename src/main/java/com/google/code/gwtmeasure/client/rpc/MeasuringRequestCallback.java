package com.google.code.gwtmeasure.client.rpc;

import com.google.code.gwtmeasure.shared.Constants;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class MeasuringRequestCallback implements RequestCallback {

    private String name;
    private RequestCallback callback;
    private MeasuringAsyncCallback wrappedCallback;

    private long start = System.currentTimeMillis();

    public MeasuringRequestCallback(String name, RequestCallback callback, MeasuringAsyncCallback wrappedCallback) {
        this.name = name;
        this.callback = callback;
        this.wrappedCallback = wrappedCallback;
    }

    public void onResponseReceived(Request request, Response response) {
        String wilyHeader = response.getHeader(Constants.UID_HEADER);
        if (wilyHeader != null && wilyHeader.trim().length() > 0) {
            wrappedCallback.setCorrelationId(wilyHeader);
        }
        this.callback.onResponseReceived(request, response);
    }

    public void onError(Request request, Throwable exception) {
        this.callback.onError(request, exception);
    }

    public String getName() {
        return name;
    }

    public long getDuration() {
        return System.currentTimeMillis() - start;
    }
}