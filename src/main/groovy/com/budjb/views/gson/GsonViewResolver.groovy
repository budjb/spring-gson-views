package com.budjb.views.gson

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.Ordered
import org.springframework.web.servlet.View
import org.springframework.web.servlet.view.AbstractCachingViewResolver

class GsonViewResolver extends AbstractCachingViewResolver implements Ordered {
    private final GsonRenderer gsonRenderer
    private int order = 0

    @Autowired
    GsonViewResolver(GsonRenderer gsonRenderer) {
        this.gsonRenderer = gsonRenderer
    }

    @Override
    protected View loadView(String viewName, Locale locale) throws Exception {
        Class<?> clazz = gsonRenderer.loadView(viewName)

        if (clazz == null) {
            return null
        }

        return new GsonView(clazz, gsonRenderer)
    }

    @Override
    int getOrder() {
        return order
    }

    void setOrder(int order) {
        this.order = order
    }
}
