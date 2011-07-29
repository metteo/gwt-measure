package com.googlecode.gwtmeasure.shared;

/**
 * @author dmitry.buzdin
 */
public interface JsonEncoder {

    String encode(IncidentReport value);

    String encode(PerformanceTiming value);

}
