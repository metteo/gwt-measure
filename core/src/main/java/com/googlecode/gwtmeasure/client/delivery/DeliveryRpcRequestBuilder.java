package com.googlecode.gwtmeasure.client.delivery;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.rpc.RpcRequestBuilder;
import com.googlecode.gwtmeasure.client.internal.DeliveryQueue;
import com.googlecode.gwtmeasure.shared.Constants;
import com.googlecode.gwtmeasure.shared.IncidentReport;
import com.googlecode.gwtmeasure.shared.PerformanceTiming;

import java.util.List;

/**
 * @author dmitry.buzdin
 */
public class DeliveryRpcRequestBuilder extends RpcRequestBuilder {

    public DeliveryRpcRequestBuilder() {
    }

    /**
     * Inject pending measurements if any are in the queue.
     *
     * @param requestBuilder
     */
    @Override
    protected void doFinish(RequestBuilder requestBuilder) {
        super.doFinish(requestBuilder);

        DeliveryQueue deliveryQueue = DeliveryQueue.instance();
        List<PerformanceTiming> timings = deliveryQueue.popTimings();
        List<IncidentReport> incidents = deliveryQueue.popIncidents();

        ResultInjector injector = new HeaderInjector();
        HeaderInjector.Result result = injector.inject(requestBuilder, timings, incidents);

        deliveryQueue.pushTiming(result.getExcessTimings());
    }

    /**
     * Sets a unique request id for each RPC call.
     *
     * @param requestBuilder
     * @param id
     */
    @Override
    protected void doSetRequestId(RequestBuilder requestBuilder, int id) {
        requestBuilder.setHeader(Constants.HEADER_UID, Integer.toString(id));
    }

}
