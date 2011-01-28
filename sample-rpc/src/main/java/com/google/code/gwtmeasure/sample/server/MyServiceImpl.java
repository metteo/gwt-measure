package com.google.code.gwtmeasure.sample.server;

import com.google.code.gwtmeasure.sample.shared.Model;
import com.google.code.gwtmeasure.sample.client.MyService;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class MyServiceImpl extends RemoteServiceServlet implements MyService {

    public Model doStuff(Model model) {
        return model;
    }

}
