package org.kttp.model;

import lombok.Data;

@Data
public class HttpRequest {
    private final HttpRequestMeta metadata;
    private final Object body;
}
