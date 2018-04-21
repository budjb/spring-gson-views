package com.budjb.views.gson;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("gson")
public class GsonProperties {
    private final View view = new View();

    public View getView() {
        return view;
    }

    public static class View {
        public static final String DEFAULT_PREFIX = "";
        public static final String DEFAULT_SUFFIX = ".gson";

        private String prefix = DEFAULT_PREFIX;
        private String suffix = DEFAULT_SUFFIX;

        public String getSuffix() {
            return suffix;
        }

        public void setSuffix(String suffix) {
            this.suffix = suffix;
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }
    }
}
