package com.budjb.views.gson

import groovy.json.JsonDelegate
import groovy.transform.CompileStatic

/**
 * A JSON view template.
 */
@CompileStatic
abstract class GsonScript extends Script {
    /**
     * Contains the JSON object structure generated from the script.
     */
    Object _data

    /**
     * Response properties object.
     */
    final Response response = new Response()

    /**
     * Creates a JSON data structure based on the given closure.
     *
     * @param closure
     */
    void json(@DelegatesTo(JsonDelegate) Closure closure) {
        this._data = new StructureBuilder().call(closure)
    }

    /**
     * Uses the given map as the JSON data structure.
     *
     * @param data
     */
    void json(Map<?, ?> data) {
        this._data = new StructureBuilder().call(data)
    }

    /**
     * Uses the given list as the JSON data structure.
     *
     * @param data
     */
    void json(List<?> data) {
        this._data = new StructureBuilder().call(data)
    }

    /**
     * A dummy closure that solely exists so that variables used in the script may be defined
     * and discovered by the IDE.
     *
     * @param closure
     */
    void model(Closure closure) {
        // Don't actually do anything, this is just so the GSON script can know types.
    }

    /**
     * Contains details to set on the HTTP response object.
     */
    private static class Response {
        /**
         * Content type.
         */
        String _contentType = 'application/json;charset=utf-8'

        /**
         * HTTP status
         */
        Integer _status

        /**
         * Headers.
         */
        Map<String, List<String>> _headers = [:]

        /**
         * Sets the content type of the response.
         *
         * @param contentType
         */
        void contentType(String contentType) {
            this._contentType = contentType
        }

        /**
         * Adds a header to the response.
         *
         * @param name
         * @param value
         */
        void header(String name, String value) {
            if (!this._headers.containsKey(name)) {
                this._headers.put(name, [value])
            }
            else {
                this._headers.get(name).add(value)
            }
        }

        /**
         * Sets the HTTP status of the response.
         *
         * @param status
         */
        void status(int status) {
            this._status = status
        }
    }
}
