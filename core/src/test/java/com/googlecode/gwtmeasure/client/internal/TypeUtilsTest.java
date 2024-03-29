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

package com.googlecode.gwtmeasure.client.internal;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

/**
 * @author <a href="mailto:buzdin@gmail.com">Dmitry Buzdin</a>
 */
public class TypeUtilsTest extends Assert {

    @Test
    public void testGetSimpleName() throws Exception {
        String result = TypeUtils.classSimpleName("com.google.code.sample.Class");
        assertThat(result, equalTo("Class"));
    }

    @Test
    public void testGetSimpleName_InnerClass() throws Exception {
        String result = TypeUtils.classSimpleName("com.google.code.sample.Class$Inner");
        assertThat(result, equalTo("Class$Inner"));
    }

}
