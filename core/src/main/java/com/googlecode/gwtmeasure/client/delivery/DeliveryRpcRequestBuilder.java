package com.googlecode.gwtmeasure.client.delivery;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.rpc.RpcRequestBuilder;
import com.googlecode.gwtmeasure.shared.Constants;

/**
 * @author dmitry.buzdin
 */
public class DeliveryRpcRequestBuilder extends RpcRequestBuilder {

    private static final RpcRequestBuilder instance = new DeliveryRpcRequestBuilder();

    public static RpcRequestBuilder instance() {
        return instance;
    }

    private DeliveryRpcRequestBuilder() {
    }

    /**
     * Inject pending measurements if any are in the queue.
     *
     * @param requestBuilder
     */
    @Override
    protected void doFinish(RequestBuilder requestBuilder) {
        super.doFinish(requestBuilder);
        HeaderInjector injector = new HeaderInjector();
        injector.inject(requestBuilder);
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
