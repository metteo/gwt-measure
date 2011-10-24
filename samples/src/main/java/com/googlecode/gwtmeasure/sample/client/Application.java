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

package com.googlecode.gwtmeasure.sample.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.place.shared.PlaceHistoryHandler;

import javax.inject.Inject;

/**
 * @author <a href="mailto:dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class Application {

    private PlaceHistoryHandler historyHandler;
    private Display display;

    @Inject
    public Application(PlaceHistoryHandler historyHandler,
                       ActivityManager activityManager,
                       Display display) {
        this.historyHandler = historyHandler;
        this.display = display;
    }

    public void init() {
        display.init();
        historyHandler.handleCurrentHistory();
    }

}