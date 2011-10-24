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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author <a href="mailto:buzdin@gmail.com">Dmitry Buzdin</a>
 */
public class MeasurementSerializer {

    private static final int BYTES_IN_JS_CHAR = 2;

    /**
     * Provides serialized version of objects to be sent on server.
     *
     * @param objects list of serializable objects. Excessive elements are left in the list to be processed later.
     * @param byteLimit maximum number of bytes supported in HTTP headers
     * @return serialized string ready for transport to server
     */
    public String serialize(List<? extends HasJsonRepresentation> objects, int byteLimit) {
        int size = 2;
        List<String> encodedObjects = new ArrayList<String>();
        for (Iterator<? extends HasJsonRepresentation> iterator = objects.iterator(); iterator.hasNext();) {
            HasJsonRepresentation object = iterator.next();
            String encoded = object.jsonEncode(new JsonEncoderImpl());
            size += encoded.length() + 1;

            if (byteLimit == -1 || size * BYTES_IN_JS_CHAR <= byteLimit) {
                encodedObjects.add(encoded);
                iterator.remove();
            } else {
                break;
            }
        }

        return wrapObjectsInArray(encodedObjects);
    }

    private String wrapObjectsInArray(List<String> jsonObjects) {
        StringBuilder builder = new StringBuilder("[");
        int size = jsonObjects.size();
        for (int i = 0; i < size; i++) {
            String jsonObject = jsonObjects.get(i);
            builder.append(jsonObject);
            if (i != size - 1) {
                builder.append(',');
            }
        }
        builder.append("]");
        return builder.toString();
    }

}
