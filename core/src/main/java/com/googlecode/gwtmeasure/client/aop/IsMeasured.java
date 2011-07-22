package com.googlecode.gwtmeasure.client.aop;

/**
 * Marker interface, which allows generating measuring proxy for the implementing type.
 * All public methods will have 'begin' and 'end' events triggered.
 *
 * Proxy generation is based on Deferred Binding mechanism.
 *
 * Intended to be used on facades or services, which require measurements of all methods.
 *
 * @see com.googlecode.gwtmeasure.rebind.aop.MeasuringProxyGenerator
 * @author dmitry.buzdin
 */
public interface IsMeasured {
}
