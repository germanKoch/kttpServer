package org.kttp.listener.model;

import java.util.HashMap;
import java.util.Map;

public class HttpHeaders {
    private final Map<String, String> values = new HashMap<>();

    public HttpHeaders() {
    }

    public HttpHeaders(String name, String value) {
        values.put(name, value);
    }

    public HttpHeaders(String name1, String value1, String name2, String value2) {
        values.put(name1, value1);
        values.put(name2, value2);
    }

    public HttpHeaders(String name1, String value1, String name2, String value2, String name3, String value3) {
        values.put(name1, value1);
        values.put(name2, value2);
        values.put(name3, value3);
    }


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
