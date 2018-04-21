package com.budjb.views.gson

import org.springframework.boot.autoconfigure.template.TemplateAvailabilityProvider
import org.springframework.core.env.Environment
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.ResourceLoader

class GsonAvailabilityProvider implements TemplateAvailabilityProvider {
    @Override
    boolean isTemplateAvailable(String view, Environment environment, ClassLoader classLoader, ResourceLoader resourceLoader) {
        return new ClassPathResource(buildPath(view, environment)).exists()
    }

    private String buildPath(String view, Environment environment) {
        String prefix = environment.getProperty('gson.view.prefix', GsonProperties.View.DEFAULT_PREFIX)
        String suffix = environment.getProperty('gson.view.suffix', GsonProperties.View.DEFAULT_SUFFIX)
        return prefix + view + suffix
    }
}
