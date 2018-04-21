package com.budjb.views.gson

import groovy.json.JsonBuilder
import groovy.json.JsonDelegate

/**
 * Class that builds a data structure based on lists and maps using the {@link JsonDelegate}.
 *
 * This differs from {@link JsonBuilder} in that it executes all embedded
 * closures at build time, whereas the {@link JsonBuilder} does so at render time.
 */
class StructureBuilder {
    /**
     * Create a root data structure with the given closure.
     *
     * @param c
     */
    Object call(@DelegatesTo(JsonDelegate) Closure c) {
        return filter(JsonDelegate.cloneDelegateAndGetContent(c))
    }

    /**
     * Create a root data structure with the given map.
     *
     * @param map
     * @return
     */
    Map call(Map map) {
        return filter(map)
    }

    /**
     * Create a root data structure with the given list.
     *
     * @param list
     * @return
     */
    List call(List list) {
        return filter(list)
    }

    /**
     * Filters the given map by executes any embedded closures against a {@link groovy.json.JsonDelegate}.
     *
     * @param map
     * @return
     */
    protected Map filter(Map map) {
        return map.collectEntries { key, value ->
            return [(key): filterValue(value)]
        }
    }

    /**
     * Filters the given list by executing any embedded closures against a {@link groovy.json.JsonDelegate}.
     *
     * @param list
     * @return
     */
    protected List filter(List list) {
        return list.collect {
            filterValue(it)
        }
    }

    /**
     * Filters a given value. If the value is a {@link groovy.lang.Closure}, it is run against a {@link groovy.json.JsonDelegate}.
     *
     * @param value
     * @return
     */
    protected Object filterValue(Object value) {
        if (value instanceof Map) {
            return filter(value)
        }
        else if (value instanceof List) {
            return filter(value)
        }
        else if (value instanceof Closure) {
            return filter(this.call(value))
        }
        else {
            return value
        }
    }
}
