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

package com.googlecode.gwtmeasure.server.internal;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

/**
 * @author <a href="mailto:dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class BeanContainerTest {

    private BeanContainer container;

    @Before
    public void setUp() {
        container = new BeanContainer();
    }

    @Test
    public void testGetBean() throws Exception {
        Service bean = container.getBean(Service.class);

        assertThat(bean, notNullValue());
    }

    @Test
    public void testConstructorInjection() throws Exception {
        ServiceWithDeps bean = container.getBean(ServiceWithDeps.class);

        assertThat(bean, notNullValue());
        assertThat(bean.getService(), notNullValue());
        assertThat(bean.getString(), notNullValue());
    }

    @Test
    public void testReusesInstances() throws Exception {
        Service dependency = container.getBean(Service.class);
        ServiceWithDeps bean = container.getBean(ServiceWithDeps.class);

        assertThat(bean.getService(), sameInstance(dependency));
    }

    @Test
    public void testInterfaceImplemenation() throws Exception {
        container.register(I.class, Impl.class);
        I bean = container.getBean(I.class);
        assertThat(bean, is(Impl.class));
    }

    @Test
    public void testImplementationReplacement() throws Exception {
        container.register(I.class, Impl.class);
        container.register(I.class, Alternate.class);
        I bean = container.getBean(I.class);
        assertThat(bean, is(Alternate.class));
    }

    @Test
    public void testRegisterBean() throws Exception {
        Impl bean = new Impl();
        container.register(I.class, bean);
        I result = container.getBean(I.class);
        assertThat(bean, sameInstance(result));
    }

    @Test
    public void testBeanReplacement() throws Exception {
        Impl bean = new Impl();
        container.register(I.class, bean);

        Impl replacement = new Impl();
        container.register(I.class, replacement);

        I result = container.getBean(I.class);

        assertThat(replacement, sameInstance(result));
    }

    public static interface I {

    }

    public static class Impl implements I {

    }

    public static class Alternate implements I {

    }

    public static class Service {
    }

    public static class ServiceWithDeps {
        private Service service;
        private String string;

        public ServiceWithDeps(Service service, String string) {
            this.service = service;
            this.string = string;
        }

        public Service getService() {
            return service;
        }

        public void setService(Service service) {
            this.service = service;
        }

        public String getString() {
            return string;
        }

        public void setString(String string) {
            this.string = string;
        }
    }

}
