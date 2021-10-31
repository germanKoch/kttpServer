package org.kttp.server;

import org.kttp.server.model.HttpMethod;
import org.kttp.server.model.HttpRequest;
import org.kttp.server.model.HttpResponse;
import org.kttp.server.model.HttpStatusCode;

import java.util.Map;

public class HandlerDispatcher {
    Map<HttpMethod, Map<String, Handler>> handlers;

    public HandlerDispatcher(Map<HttpMethod, Map<String, Handler>> handlers) {
        this.handlers = handlers;
    }

    public HttpResponse handleRequest(HttpRequest request) {
        var handlersMap = handlers.get(request.getMetadata().getMethod());
        var handler = handlersMap.get(request.getMetadata().getUrl());

        if (handler == null) {
            return new HttpResponse("NOT FOUND",HttpStatusCode.NOT_FOUND);
        }
        return handler.handle(request);
    }

}
