package com.budjb.views.gson

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered

@Configuration
@EnableConfigurationProperties(GsonProperties)
class GsonAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    GsonRenderer gsonRenderer(GsonProperties gsonProperties) {
        return new GsonRenderer(gsonProperties)
    }

    @Bean
    @ConditionalOnMissingBean
    GsonViewResolver gsonViewResolver(GsonRenderer gsonRenderer) {
        GsonViewResolver resolver = new GsonViewResolver(gsonRenderer)
        resolver.setOrder(Ordered.HIGHEST_PRECEDENCE - 10)
        return resolver
    }
}
