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

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.InvocationException;
import com.google.gwt.user.client.rpc.StatusCodeException;
import com.googlecode.gwtmeasure.client.internal.TimeUtils;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class IncidentReport implements HasJsonRepresentation {

    private long timestamp;
    private String text;
    private String message;

    public IncidentReport() {
        this.timestamp = TimeUtils.current();
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

    public static IncidentReport createRpcReport(Throwable throwable) {
        IncidentReport report = new IncidentReport();
        report.setMessage(throwable.getMessage());
        try {
            throw throwable;
        } catch (IncompatibleRemoteServiceException e) {
            report.setText("Client version is not compatible with server version");
        } catch (StatusCodeException e) {
            StatusCodeException statusCodeException = (StatusCodeException) throwable;
            int statusCode = statusCodeException.getStatusCode();
            if (statusCode != 0) {
                report.setText("Response had unexpected http status code " + statusCode);
            } else {
                report.setText("Server is not available");
            }
        } catch (InvocationException e) {
            report.setText("Server is not available");
        } catch (Throwable e) {
            report.setText("Internal server error");
        }
        return report;
    }

    public static IncidentReport createReport(Throwable throwable) {
        IncidentReport report = new IncidentReport();
        report.setText("Unhandled client side exception");
        report.setMessage(throwable.getMessage());
        return report;
    }

    public String jsonEncode() {
        JSONObject object = new JSONObject();

        object.put("timestamp", new JSONNumber(timestamp));
        if (text != null) object.put("text", new JSONString(text));
        if (message != null) object.put("message", new JSONString(message));

        return object.toString();
    }

    @Override
    public String toString() {
        return "IncidentReport{" +
                "timestamp=" + timestamp +
                ", text='" + text + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
    
}
