package org.kttp.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class HttpHeaders {
    private Map<String, String> values = new HashMap<>();

    public String getHeader(String header) {
        return values.get(header);
    }

    public void addHeader(String header, String value) {
        values.put(header, value);
    }
}
