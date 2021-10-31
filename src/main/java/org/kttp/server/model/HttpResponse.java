package org.kttp.server.model;

import lombok.Data;

@Data
public class HttpResponse {
    private final HttpHeaders headers;
    private Object body;
    private HttpStatusCode status;

    public HttpResponse(HttpStatusCode status) {
        this(new HttpHeaders(), status);
    }

    public HttpResponse(Object body, HttpStatusCode status) {
        this(new HttpHeaders(), body, status);
    }

    public HttpResponse(HttpHeaders headers, HttpStatusCode status) {
        this.headers = headers;
        this.status = status;
    }

    public HttpResponse(HttpHeaders headers, Object body, HttpStatusCode status) {
        this.headers = headers;
        this.body = body;
        this.status = status;
    }
}
