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

var gwt_initialized = false;

function sinkGwtEvents() {
    if (this.eventBuffer) {
        // We have some data that was reported before the div was connected
        for (var i = 0; i < this.eventBuffer.length; i++) {
            // print it all to the div
            var bufferedEvent = this.eventBuffer[i];
            handleEvent("" + bufferedEvent.evtGroup,
                    "" + bufferedEvent.moduleName,
                    "" + bufferedEvent.subSystem,
                    "" + bufferedEvent.type,
                    "" + bufferedEvent.millis
                    );
        }
        this.eventBuffer = null;
    }
}

window.__gwtStatsEvent = function(event) {
    alert("event received");
    if (gwt_initialized) {
        //if flag is set to true, then all events goes to GWT created method
        sinkGwtEvents();
        handleEvent("" + event.evtGroup, "" + event.moduleName, "" + event.subSystem, "" + event.type, "" + event.millis);
    } else {
        //if flag is not set to true, then all events goes to eventStorage
        this.eventBuffer = (this.eventBuffer) ? this.eventBuffer : [];
        this.eventBuffer.push(event);
    }
    // The collector function should indicate success
    return true;
};
