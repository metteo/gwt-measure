The project aims at providing generic and configurable production monitoring framework for GWT applications.

Production monitoring includes performance metrics of major events occurring at user's browsers as well as unexpected exception reporting.

The target of the project is to provide development projects with transparent instrumentation mechanism, which is extensible for specific needs.

GWT provides an approach for capturing performance events out of the box. This approach is called [Lightweight Measurements](http://code.google.com/webtoolkit/doc/latest/DevGuideLightweightMetrics.html). However, according to the documentation, it requires set-up and customization if you want to register your own events. You would need custom code to feed alternative Lightweight Metrics. Another challenge is delivering the measurements back to the centralized server in order to put them into database or log file. People solve this problem differently. There are several blog posts describing how to achieve this.
  * http://goo.gl/sbf2V
  * http://goo.gl/RULO5

Project **gwt-measure** is an attempt to unify all mentioned production and development performance monitoring needs by providing adaptable solution, which you could use to sync events from GWT application to the backend of your choice. It could be specialized Application Performance Monitoring agent, database or plain log file.

Framework design looks like the following.

![http://gwt-measure.googlecode.com/hg/design.png](http://gwt-measure.googlecode.com/hg/design.png)