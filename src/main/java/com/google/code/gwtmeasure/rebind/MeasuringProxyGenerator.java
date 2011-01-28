package com.google.code.gwtmeasure.rebind;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.user.rebind.rpc.ProxyCreator;
import com.google.gwt.user.rebind.rpc.ServiceInterfaceProxyGenerator;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class MeasuringProxyGenerator extends ServiceInterfaceProxyGenerator {

    @Override
    protected ProxyCreator createProxyCreator(JClassType remoteService) {
        return new MeasuringProxyCreator(remoteService);
    }

}