/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.code.gwtmeasure.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class Measurement implements IsSerializable {

    private long from;
    private long to;
    private String name;
    private String group;

    public Measurement() {
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
