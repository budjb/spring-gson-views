package com.budjb.views.gson

import org.springframework.web.servlet.view.AbstractUrlBasedView

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class GsonView extends AbstractUrlBasedView {
    /**
     * Compiled GSON script class.
     */
    private final Class<? extends GsonScript> clazz

    /**
     * GSON renderer.
     */
    private final GsonRenderer gsonRenderer

    /**
     * Constructor.
     *
     * @param clazz
     * @param gsonRenderer
     */
    GsonView(Class<? extends GsonScript> clazz, GsonRenderer gsonRenderer) {
        this.clazz = clazz
        this.gsonRenderer = gsonRenderer
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        gsonRenderer.render(clazz, model, response)
    }
}
