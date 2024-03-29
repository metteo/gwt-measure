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

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.*;

/**
 * @author <a href="mailto:dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class MainDisplay extends Composite implements Display {

    @UiField
    SimplePanel content;
    @UiField
    SimplePanel output;

    interface ViewUIBinder extends UiBinder<Widget, MainDisplay> {
    }

    public MainDisplay() {
        initWidget(GWT.<ViewUIBinder>create(ViewUIBinder.class).createAndBindUi(this));
    }

    public void setWidget(IsWidget widget) {
        content.setWidget(widget);
    }

    public void init() {
        RootLayoutPanel.get().add(this);
    }

}
