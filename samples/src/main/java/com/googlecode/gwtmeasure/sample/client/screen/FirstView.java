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

package com.googlecode.gwtmeasure.sample.client.screen;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.gwtmeasure.sample.client.Display;

/**
 * @author <a href="mailto:dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class FirstView extends Composite {

    @UiField
    TextArea textArea;

    @UiField
    Button addButton;
    @UiField
    Button rpcButton;
    @UiField
    Button httpButton;
    @UiField
    Button asyncButton;
    @UiField
    Button errorButton;

    FirstPresenter presenter;

    interface ViewUIBinder extends UiBinder<Widget, FirstView> {
    }

    public FirstView() {
        initWidget(GWT.<ViewUIBinder>create(ViewUIBinder.class).createAndBindUi(this));
    }

    public void setPresenter(FirstPresenter presenter) {
        this.presenter = presenter;
    }

    @UiHandler("addButton")
    public void onAddMeasurement(ClickEvent event) {
        presenter.addMeasurement();
    }

    @UiHandler("rpcButton")
    public void onSendRpc(ClickEvent event) {
        presenter.callServer();
    }

    @UiHandler("httpButton")
    public void onSendHttp(ClickEvent event) {
        presenter.callXhrServer();
    }

    @UiHandler("asyncButton")
    public void onLoadAsync(ClickEvent event) {
        presenter.loadAsync();
    }

    @UiHandler("errorButton")
    public void onError(ClickEvent event) {
        presenter.throwException();
    }

}
