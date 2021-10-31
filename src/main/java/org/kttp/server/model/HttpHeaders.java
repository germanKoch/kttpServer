package org.kttp.server.model;

import java.util.HashMap;
import java.util.Map;

public class HttpHeaders {
    private final Map<String, String> values = new HashMap<>();

    public Map<String, String> getHeaders() {
        return new HashMap<>(values);
    }

    public String getValue(String header) {
        return values.get(header);
    }

    public void addHeader(String header, String value) {
        values.put(header, value);
    }

    public boolean contains(String header) {
        return values.containsKey(header);
    }
}
