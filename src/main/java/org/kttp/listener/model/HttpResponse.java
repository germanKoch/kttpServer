package org.kttp.listener.model;

import lombok.Data;

@Data
public class HttpResponse {
    private final HttpHeaders headers;
    private Object body;
    private HttpStatusCode status;

    public HttpResponse(HttpStatusCode status) {
        this(status, new HttpHeaders());
    }

    public HttpResponse(HttpStatusCode status, Object body) {
        this(status, body, new HttpHeaders());
    }

    public HttpResponse(HttpStatusCode status, HttpHeaders headers) {
        this.headers = headers;
        this.status = status;
    }

    public HttpResponse(HttpStatusCode status, Object body, HttpHeaders headers) {
        this.headers = headers;
        this.body = body;
        this.status = status;
    }
}
