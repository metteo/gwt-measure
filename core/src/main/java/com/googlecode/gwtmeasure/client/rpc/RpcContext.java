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

package com.googlecode.gwtmeasure.client.rpc;

/**
 * @author <a href="mailto:dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public final class RpcContext {

    private RpcContext() {
    }

    private static int requestIdCounter;
    private static int lastResolvedRequestId;

    /**
     * Returns last request id, which is resolved in onSuccess() onFailure() callback methods
     * @return id
     */
    public static int getLastResolvedRequestId() {
        return lastResolvedRequestId;
    }

    static void setLastResolvedRequestId(int lastResolvedRequestId) {
        RpcContext.lastResolvedRequestId = lastResolvedRequestId;
    }

    /**
     * Returns last active request id, which may still not be resolved at the moment
     * @return id
     */
    public static int getRequestIdCounter() {
        return requestIdCounter;
    }

    static void setRequestIdCounter(int requestIdCounter) {
        RpcContext.requestIdCounter = requestIdCounter;
    }
}
