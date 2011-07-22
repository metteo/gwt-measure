package com.googlecode.gwtmeasure.client.delivery;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.googlecode.gwtmeasure.shared.IncidentReport;
import com.googlecode.gwtmeasure.shared.JsonEncoder;
import com.googlecode.gwtmeasure.shared.PerformanceTiming;

import java.util.Map;

/**
 * @author dmitry.buzdin
 */
public final class JsonEncoderImpl implements JsonEncoder {

    public String encode(IncidentReport value) {
        JSONObject object = new JSONObject();

        object.put("timestamp", new JSONNumber(value.getTimestamp()));
        if (value.getText() != null) object.put("text", new JSONString(value.getText()));
        if (value.getMessage() != null) object.put("message", new JSONString(value.getMessage()));
        if (value.getStrongName() != null) object.put("strongName", new JSONString(value.getStrongName()));
        if (value.getUrl() != null) object.put("url", new JSONString(value.getUrl()));

        if (value.getStackTrace() != null) {
            JSONArray jsonArray = new JSONArray();
            for (int i = 0, stackTraceLength = value.getStackTrace().length; i < stackTraceLength; i++) {
                String element = value.getStackTrace()[i];
                jsonArray.set(i, new JSONString(element));
            }
            object.put("stackTrace", jsonArray);
        }

        if (!value.getParameterNames().isEmpty()) {
            JSONObject params = new JSONObject();
            for (String name : value.getParameterNames()) {
                String paramValue = value.getParameter(name);
                if (paramValue != null) {
                    params.put(name, new JSONString(paramValue));
                }
            }
            object.put("parameters", params);
        }

        return object.toString();
    }

    public String encode(PerformanceTiming value) {
        JSONObject object = new JSONObject();

        if (value.getModuleName() != null) object.put("moduleName", new JSONString(value.getModuleName()));
        if (value.getSubSystem() != null) object.put("subSystem", new JSONString(value.getSubSystem()));
        if (value.getEventGroup() != null) object.put("eventGroup", new JSONString(value.getEventGroup()));
        object.put("millis", new JSONNumber(value.getMillis()));
        if (value.getType() != null) object.put("type", new JSONString(value.getType()));

        if (!value.getParameterNames().isEmpty()) {
            JSONObject params = new JSONObject();
            for (String name : value.getParameterNames()) {
                String paramValue = value.getParameter(name);
                if (paramValue != null) {
                    params.put(name, new JSONString(paramValue));
                }
            }
            object.put("parameters", params);
        }

        return object.toString();
    }

}
