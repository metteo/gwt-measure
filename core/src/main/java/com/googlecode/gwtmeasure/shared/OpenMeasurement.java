/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.googlecode.gwtmeasure.shared;

import java.util.Set;

/**
 * @author <a href="mailto:dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public interface OpenMeasurement {

    public static enum Status {
        CREATED, STARTED, DISCARDED, STOPPED
    }

    String getEventGroup();

    String getSubSystem();

    void setParameter(String name, String value);

    String getParameter(String name);

    Set<String> getParameterNames();

    void stop();

    void discard();

    Status getStatus();

    OpenMeasurement start(String subSystem);

}
