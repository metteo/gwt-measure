# Prerequisites #

Currently gwt-measure depends on GWT 2.1 version.
There has been changes in GWT 2.2 and some minor adaptations will be required to support that. This will be done in the nearest future.

# Step I #

Download gwt-measure.jar from release repository. All project artifacts are synced to Maven central repository.

Maven Dependency
```
<dependency>
    <groupId>com.googlecode</groupId>
    <artifactId>gwt-measure</artifactId>
    <version>x.x.x</version>
</dependency>
```

Check the overview of versions here.

http://mvnrepository.com/artifact/com.googlecode/gwt-measure

# Step II #

Register library as the dependency in your classpath and GWT module configuration file.

Enable the debug handler in order to check if everything is working properly.

```
<inherits name="com.googlecode.gwtmeasure.GWTMeasure" />

<set-property name="gwt.measure.debugHandler" value="ENABLED"/>
```

# Step III #

Register provided servlet filter in order to catch incoming requests. It should catch all RPC or REST communication between client and server.

```
<filter>
  <filter-name>measuringFilter</filter-name>
  <filter-class>com.googlecode.gwtmeasure.server.servlet.MeasureFilter</filter-class>
</filter>
<filter-mapping>
  <filter-name>measuringFilter</filter-name>
  <servlet-name>gwtservlet</servlet-name>
</filter-mapping>
```

# Step IV #

Register standalone servlet for consuming bulk measurements.

```
<servlet>
   <servlet-name>measurementServlet</servlet-name>
   <servlet-class>com.googlecode.gwtmeasure.server.servlet.MeasuringServlet</servlet-class>
</servlet>
<servlet-mapping>
   <servlet-name>measurementServlet</servlet-name>
   <url-pattern>/measurements</url-pattern>
</servlet-mapping>
```

# Step V #

Embed the following JavaScript chunk **before** loading GWT module.

```
<script type="text/javascript">                
  document.cookie = "resourceLoadStart=" + new Date().getTime();
</script>
```

# Step VI #

Import or embed the following JavaScript fragment on your page.

```
window.sinkGwtEvents = function() {
    if (this.eventBuffer) {        
        for (var i = 0; i < this.eventBuffer.length; i++) {
            var event = this.eventBuffer[i];
            handleEvent(event);
        }
        this.eventBuffer = null;
    }
};
window.__gwtStatsEvent = function(event) {
    this.eventBuffer = (this.eventBuffer) ? this.eventBuffer : [];
    this.eventBuffer.push(event);
    return true;
};
```

# Step VII #

You are ready to go. Custom measurements could be registered in the following manner.

```
PendingMeasurement measurement = Measurements.start("custom");
measurement.stop();
```

# Result #

You should see the list of performance events displayed in a popup as shown on the screenshot.

![http://gwt-measure.googlecode.com/hg/celltable.png](http://gwt-measure.googlecode.com/hg/celltable.png)