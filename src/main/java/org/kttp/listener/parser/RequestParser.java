package org.kttp.listener.parser;

import org.kttp.listener.model.HttpRequest;
import org.kttp.listener.model.HttpRequestMeta;
import org.kttp.listener.model.HttpResponse;

public interface RequestParser {

    HttpRequest parseRequest(String request);

    String parseResponse(HttpResponse response);

    HttpRequestMeta parseMeta(String metaRequest);
}
