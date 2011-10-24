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

package com.googlecode.gwtmeasure.shared;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsonUtils;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.rpc.StatusCodeException;
import com.googlecode.gwtmeasure.client.internal.TimeUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Representation of application incident. Usually it means unexpected client-side exception.
 *
 * @author <a href="buzdin@gmail.com">Dmitry Buzdin</a>
 */
public class IncidentReport implements HasJsonRepresentation {

    // TODO Incident unique id
    private long timestamp;
    private String text;
    private String message;
    private String strongName;
    private String url;
    private String[] stackTrace;

    private final Map<String, String> parameters = new HashMap<String, String>();

    public IncidentReport() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getParameter(String name) {
        return parameters.get(name);
    }

    public Set<String> getParameterNames() {
        return parameters.keySet();
    }

    public String getStrongName() {
        return strongName;
    }

    public void setStrongName(String strongName) {
        this.strongName = strongName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setParameter(String name, Object value) {
        String string = value == null ? "" : value.toString();
        this.parameters.put(name, string);
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String[] getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String[] stackTrace) {
        this.stackTrace = stackTrace;
    }

    public String jsonEncode(JsonEncoder encoder) {
        return encoder.encode(this);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("IncidentReport{")
                .append("timestamp='")
                .append(timestamp)
                .append("', text='")
                .append(text)
                .append("', message='")
                .append(message)
                .append("', strongName='")
                .append(strongName)
                .append("', url='")
                .append(url)
                .append("', stackTrace=[");

        for (String element : stackTrace) {
            builder.append(element);
            builder.append(",");
        }

        builder.append("', parameters=[");
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            builder.append(entry.getKey());
            builder.append("=");
            builder.append(entry.getValue());
            builder.append(",");
        }
        builder.append("]}");
        return builder.toString();
    }

}
