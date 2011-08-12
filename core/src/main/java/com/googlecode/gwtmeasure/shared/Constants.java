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

/**
 * Common measurement types and other relevant constants.
 *
 * @author <a href="buzdin@gmail.com">Dmitry Buzdin</a>
 */
public final class Constants {

    // SubSystems
    public static final String SUB_SYSTEM_DEFAULT = "default";
    public static final String SUB_SYSTEM_STARTUP = "startup";
    public static final String SUB_SYSTEM_RPC = "rpc";    
    public static final String SUB_SYSTEM_RPC_CALLBACK = "rpc-callback";
    public static final String SUB_SYSTEM_HTTP = "http";

    // Types
    public static final String TYPE_BEGIN = "begin";
    public static final String TYPE_END = "end";
    public static final String TYPE_RESPONSE_SENT = "responseSent";
    public static final String TYPE_RESPONSE_RECEIVED = "responseReceived";
    public static final String TYPE_REQUEST_SENT = "requestSent";
    public static final String TYPE_REQUEST_RECEIVED = "requestReceived";
    public static final String TYPE_PAGE_LOADED = "pageLoaded";
    public static final String TYPE_REQUEST_SERIALIZED = "requestSerialized";
    public static final String TYPE_RESPONSE_DESERIALIZED = "responseDeserialized";
    public static final String TYPE_MODULE_LOAD = "onModuleLoadStart";

    // Groups
    public static final String GRP_BOOTSTRAP = "bootstrap";
    public static final String GRP_MODULE_STARTUP = "moduleStartup";

    // HTTP Headers
    public static final String HEADER_UID = "X-GWT-Perf-uid";
    public static final String HEADER_RESULT = "X-GWT-Perf-result";
    public static final String HEADER_ERRORS = "X-GWT-Perf-error";
    public static final String HEADER_WND_ID = "X-GWT-Perf-wnd-id";

    // Servlet Attributes
    public static final String ATTR_PROCESSED = "_processed";

    // Custom parameters
    public static final String PARAM_METHOD = "method";
    public static final String PARAM_FRAGMENT = "fragment";
    public static final String PARAM_SIZE = "size";    
    public static final String PARAM_BYTES = "bytes";
    public static final String PARAM_SESSION_ID = "sessionId";    
    public static final String PARAM_WINDOWID = "windowId";

    // Cookies
    public static final String COOKIE_RESOURCE_LOAD_START = "resourceLoadStart";

    private Constants() {
    }

}
