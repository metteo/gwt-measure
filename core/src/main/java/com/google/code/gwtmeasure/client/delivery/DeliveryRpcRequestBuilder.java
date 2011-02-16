package com.google.code.gwtmeasure.client.delivery;

import com.google.code.gwtmeasure.client.internal.MeasurementBuffer;
import com.google.code.gwtmeasure.shared.Constants;
import com.google.code.gwtmeasure.shared.PerformanceMetrics;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.rpc.RpcRequestBuilder;

/**
 * @author dmitry.buzdin
 */
public class DeliveryRpcRequestBuilder extends RpcRequestBuilder {

    private static final RpcRequestBuilder instance = new DeliveryRpcRequestBuilder();

    private final MeasurementSerializer serializer = new MeasurementSerializer();

    public static RpcRequestBuilder instance() {
        return instance;
    }

    private DeliveryRpcRequestBuilder() {
    }

    /**
     * Inject pending measurements if any are waiting.
     *
     * @param requestBuilder
     */
    @Override
    protected void doFinish(RequestBuilder requestBuilder) {
        super.doFinish(requestBuilder);

        MeasurementBuffer buffer = MeasurementBuffer.instance();
        String results = serializer.serialize(buffer);

        if (!"".equals(results)) {
            requestBuilder.setHeader(Constants.HEADER_RESULT, results);
        }
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
