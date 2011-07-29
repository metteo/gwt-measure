package com.googlecode.gwtmeasure.client.aop;

/**
 * Marker interface, which allows generating measuring proxy for the implementing type.
 * All public methods will have 'begin' and 'end' events triggered.
 *
 * @author dmitry.buzdin
 */
public interface Measured {
}
