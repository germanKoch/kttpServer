package org.kttp.model;

import lombok.Data;

@Data
public class HttpResponse {
    private final HttpHeaders headers;
    private Object body;
    private int status;
}
