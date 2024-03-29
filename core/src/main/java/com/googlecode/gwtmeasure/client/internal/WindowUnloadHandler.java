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

import com.google.gwt.user.client.Command;

/**
 * @author <a href="mailto:buzdin@gmail.com">Dmitry Buzdin</a>
 */
public class WindowUnloadHandler {

    private static Command command;

    public static Command getUnloadCommand() {
        return command;
    }

    public static void attach(Command command) {
        WindowUnloadHandler.command = command;
        attachOnUnloadHook();
    }

    public static void onUnload() {
        detachOnUnloadHook();
        if (command != null) {
            command.execute();
        }
    }

    private static native void attachOnUnloadHook() /*-{
         $wnd.onunload = function() {
             @com.googlecode.gwtmeasure.client.internal.WindowUnloadHandler::onUnload()();
         }
    }-*/;

    private static native void detachOnUnloadHook() /*-{
         $wnd.onunload = null
    }-*/;


}
