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

package com.googlecode.gwtmeasure.sample.server;

import com.googlecode.gwtmeasure.sample.shared.Model;
import com.googlecode.gwtmeasure.sample.client.MyService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.gwtmeasure.shared.Measurements;
import com.googlecode.gwtmeasure.shared.OpenMeasurement;

/**
 * @author <a href="buzdin@gmail.com">Dmitry Buzdin</a>
 */
public class MyServiceImpl extends RemoteServiceServlet implements MyService {

    public Model doStuff(Model model) {
        OpenMeasurement start = Measurements.start("group", "server");
        System.out.println("rpc method invoked.");
        start.stop();

        return model;
    }

}
