package com.google.code.gwtmeasure.sample.client;

import com.google.code.gwtmeasure.sample.shared.Model;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
@RemoteServiceRelativePath("MyService")
public interface MyService extends RemoteService {

    Model doStuff(Model model);

}
