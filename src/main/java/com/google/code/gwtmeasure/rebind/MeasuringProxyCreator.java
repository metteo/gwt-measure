package com.google.code.gwtmeasure.rebind;

import com.google.code.gwtmeasure.client.rpc.MeasuringRemoteServiceProxy;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.user.client.rpc.impl.RemoteServiceProxy;
import com.google.gwt.user.rebind.rpc.ProxyCreator;

/**
 * @author <a href="dmitry.buzdin@ctco.lv">Dmitry Buzdin</a>
 */
public class MeasuringProxyCreator extends ProxyCreator {

    public MeasuringProxyCreator(JClassType type) {
        super(type);
    }

    @Override
    protected Class<? extends RemoteServiceProxy> getProxySupertype() {
        return MeasuringRemoteServiceProxy.class;
    }

}