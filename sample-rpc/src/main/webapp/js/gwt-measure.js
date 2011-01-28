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
    if (gwt_initialized) {
        //if flag is set to true, then all events goes to GWT created method
        sinkGwtEvents();
        handleEvent("" + event.evtGroup, event.moduleName + "." + event.subSystem, "" + event.type, "" + event.millis);
    } else {
        //if flag is not set to true, then all events goes to eventStorage
        this.eventBuffer = (this.eventBuffer) ? this.eventBuffer : [];
        this.eventBuffer.push(event);
    }
    // The collector function should indicate success
    return true;
};
