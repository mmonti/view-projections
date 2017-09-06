package com.mmonti.view.process;

import com.mmonti.view.model.views.ProcessableView;
import com.mmonti.view.model.views.ViewRegistry;
import com.mmonti.view.support.ProxySupport;

public class ViewProcessor {

    private ViewRegistry viewRegistry;

    public ViewProcessor(ViewRegistry viewRegistry) {
        this.viewRegistry = viewRegistry;
    }

    public <T> Object process(ProcessableView<T> processableView) {
        Class entityIface = viewRegistry.getView(processableView.getEntity().getClass());
        if (entityIface == null) {
            System.out.println("Cannot find view for=" + entityIface.getClass());
            return processableView.getEntity();
        }

//        Object object = ProxySupport.simpleProxy(new CustomInvocationHandler(processableView.getEntity(), viewRegistry), entityIface);
        return  null;
    }
}
