package com.google.code.gwtmeasure.shared;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class Measurement {

    private long from;
    private long to;
    private String name;
    private String group;

    private Measurement() {
    }

    public static class Builder {

        private Measurement measurement;

        public Builder() {
            this.measurement = new Measurement();
        }

        Builder setFrom(long from) {
            measurement.from = from;
            return this;
        }

        Builder setTo(long to) {
            measurement.to = to;
            return this;
        }
        Builder setName(String name) {
            measurement.name = name;
            return this;
        }
        Builder setGroup(String group) {
            measurement.group = group;
            return this;
        }

        Measurement create() {
            return measurement;
        }
    }

    public long getFrom() {
        return from;
    }

    public long getTo() {
        return to;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }
    
}
