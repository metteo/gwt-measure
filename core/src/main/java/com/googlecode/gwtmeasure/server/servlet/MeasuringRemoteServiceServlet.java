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

package com.googlecode.gwtmeasure.server.servlet;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.gwtmeasure.server.MeasurementEngine;
import com.googlecode.gwtmeasure.server.internal.BeanContainer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author <a href="buzdin@gmail.com">Dmitry Buzdin</a>
 */
public class MeasuringRemoteServiceServlet extends RemoteServiceServlet {

    private HttpRequestHandler handler;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        MeasurementEngine context = MeasurementEngine.instance();
        BeanContainer container = context.getBeanContainer();
        handler = container.getBean(HttpRequestHandler.class);
    }

    @Override
    protected void service(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {
        handler.process(request, new HttpRequestHandler.ServletClosure() {
            public void execute() throws IOException, ServletException {
                MeasuringRemoteServiceServlet.super.service(request, response);
            }
        });
    }

    @Override
    public void destroy() {
        super.destroy();
        MeasurementEngine context = MeasurementEngine.instance();
        BeanContainer container = context.getBeanContainer();
        container.reset();
    }

}
