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

window.sinkGwtEvents = function() {
    if (this.eventBuffer) {
        // We have some data that was reported before the div was connected
        for (var i = 0; i < this.eventBuffer.length; i++) {
            var bufferedEvent = this.eventBuffer[i];
            handleEvent("" + bufferedEvent.evtGroup,
                    "" + bufferedEvent.moduleName,
                    "" + bufferedEvent.subSystem,
                    "" + bufferedEvent.type,
                    "" + bufferedEvent.millis,
                    "" + bufferedEvent.sessionId,
                    "" + bufferedEvent.method,
                    "" + bufferedEvent.bytes
                    );
        }
        this.eventBuffer = null;
    }
};

window.__gwtStatsEvent = function(event) {
    this.eventBuffer = (this.eventBuffer) ? this.eventBuffer : [];
    this.eventBuffer.push(event);
    return true;
};
