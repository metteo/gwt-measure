package com.google.code.gwtmeasure.client.internal;

import java.util.Date;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public final class TimeUtils {

    private TimeUtils() {
    }

    public static long current() {
        return new Date().getTime();
    }

}
