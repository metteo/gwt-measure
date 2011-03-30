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

package com.googlecode.gwtmeasure.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.googlecode.gwtmeasure.shared.IncidentReport;
import com.googlecode.gwtmeasure.shared.PerformanceTiming;

import java.lang.reflect.Type;
import java.util.Collection;

/**
 * @author <a href="buzdin@gmail.com">Dmitry Buzdin</a>
 */
public class JsonDecoder {

    private GsonBuilder gsonBuilder = new GsonBuilder();

    public Collection<PerformanceTiming> decodeTimings(String json) {
        Gson gson = gsonBuilder.create();
        Type collectionType = new TypeToken<Collection<PerformanceTiming>>(){}.getType();
        return gson.fromJson(json, collectionType);
    }

    public Collection<IncidentReport> decodeErrors(String json) {
        Gson gson = gsonBuilder.create();
        Type collectionType = new TypeToken<Collection<IncidentReport>>(){}.getType();
        return gson.fromJson(json, collectionType);
    }


}
