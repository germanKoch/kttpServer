package org.kttp.listener.model;

import lombok.Data;

import java.util.List;

@Data
public class HttpRequestMeta {
    private final double protocolVersion;

    private final String url;
    private final HttpHeaders headers;
    private final List<HttpParam> params;
    private final HttpMethod method;
}
