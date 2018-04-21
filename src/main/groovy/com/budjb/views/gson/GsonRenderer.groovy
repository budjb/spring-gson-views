package com.budjb.views.gson

import groovy.json.JsonOutput
import org.codehaus.groovy.control.CompilerConfiguration
import org.springframework.beans.BeanUtils
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource

import javax.servlet.http.HttpServletResponse

/**
 * Provides an interface to load and render GSON views.
 */
class GsonRenderer {
    /**
     * GSON configuration properties.
     */
    private final GsonProperties gsonProperties

    /**
     * Constructor.
     *
     * @param gsonProperties
     */
    GsonRenderer(GsonProperties gsonProperties) {
        this.gsonProperties = gsonProperties
    }

    /**
     * Renders the given GSON script with the given model applied to the runtime.
     *
     * @param clazz
     * @param model
     * @return
     */
    void render(Class<? extends GsonScript> clazz, Map<String, Object> model, HttpServletResponse response) {
        GsonScript script = BeanUtils.instantiateClass(clazz, GsonScript)
        script.setBinding(new Binding(model))
        script.run()

        if (!List.isInstance(script._data) && !Map.isInstance(script._data)) {
            throw new IllegalArgumentException("JSON view must produce a list or map") // TODO: i18n
        }

        if (script.response._contentType != null) {
            response.contentType = script.response._contentType
        }

        if (!script.response._headers.isEmpty()) {
            script.response._headers.each { k, v ->
                v.each { i ->
                    response.addHeader(k, i)
                }
            }
        }

        if (script.response._status != null) {
            response.status = script.response._status
        }

        response.writer.write(JsonOutput.toJson(script._data))
    }

    /**
     * Builds the path to the script based on the GSON configuration.
     *
     * @param view
     * @return
     */
    private String buildPath(String view) {
        return gsonProperties.view.prefix + view + gsonProperties.view.suffix
    }

    /**
     * Loads a view class object based on the view name.
     *
     * @param view
     * @return
     */
    Class<GsonScript> loadView(String view) {
        return loadView(new ClassPathResource(buildPath(view)))
    }

    /**
     * Loads a view class object contained in the given resource, if it exists.
     *
     * @param resource
     * @return
     */
    Class<GsonScript> loadView(Resource resource) {
        if (!resource.exists()) {
            return null
        }

        String className = "view_${resource.filename.replaceAll(/[\/.]/, '_')}"

        CompilerConfiguration compilerConfiguration = new CompilerConfiguration()
        compilerConfiguration.setScriptBaseClass(GsonScript.class.name)
        GroovyClassLoader classLoader = new GroovyClassLoader(getClass().getClassLoader(), compilerConfiguration)
        return (Class<GsonScript>) classLoader.parseClass(new GroovyCodeSource(new InputStreamReader(resource.inputStream), className, GroovyShell.DEFAULT_CODE_BASE))
    }
}
