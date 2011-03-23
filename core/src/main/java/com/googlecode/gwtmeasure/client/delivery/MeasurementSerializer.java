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

package com.googlecode.gwtmeasure.client.delivery;

import com.googlecode.gwtmeasure.shared.HasJsonRepresentation;

import java.util.List;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class MeasurementSerializer {

    public String serialize(List<? extends HasJsonRepresentation> objects) {
        StringBuilder headerBuilder = new StringBuilder("[");
        int size = objects.size();
        for (int i = 0; i < size; i++) {
            HasJsonRepresentation object = objects.get(i);
            String encoded = object.jsonEncode();
            headerBuilder.append(encoded);
            if (i != size - 1) {
                headerBuilder.append(',');
            }
        }
        headerBuilder.append("]");
        return headerBuilder.toString();
    }

}
