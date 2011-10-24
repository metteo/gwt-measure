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

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.inject.Provides;
import com.googlecode.gwtmeasure.sample.client.screen.FirstPresenter;

import javax.inject.Provider;
import javax.inject.Singleton;

/**
 * @author <a href="mailto:dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class SampleModule extends AbstractGinModule {

    @Override
    protected void configure() {
        bind(EventBus.class).to(SimpleEventBus.class).in(Singleton.class);
        bind(Display.class).to(MainDisplay.class).in(Singleton.class);
    }

    @Provides @Singleton
    public PlaceController createPlaceController(EventBus eventBus) {
        return new PlaceController(eventBus);
    }

    @Provides @Singleton
    public PlaceHistoryHandler createPlaceHistoryHandler(PlaceHistoryMapper placeHistoryMapper,
                                                         PlaceController placeController,
                                                         EventBus eventBus) {
        PlaceHistoryHandler placeHistoryHandler = new PlaceHistoryHandler(placeHistoryMapper);
        placeHistoryHandler.register(placeController, eventBus, FirstPresenter.PLACE);
        return placeHistoryHandler;
    }

    @Provides @Singleton
    public PlaceHistoryMapper createPlaceHistoryMapper() {
        return new PlaceHistoryMapper() {
            public Place getPlace(String token) {
                return FirstPresenter.PLACE;
            }

            public String getToken(Place place) {
                return "";
            }
        };
    }

    @Provides @Singleton
    public ActivityMapper createActivityMapper(final Provider<FirstPresenter> firstPresenterProvider) {
        return new ActivityMapper() {
            public Activity getActivity(Place place) {
                return firstPresenterProvider.get();
            }
        };
    }

    @Provides @Singleton
    public ActivityManager createActivityManager(ActivityMapper activityMapper, EventBus eventBus, Display display) {
        ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
        activityManager.setDisplay(display);
        return activityManager;
    }

}
