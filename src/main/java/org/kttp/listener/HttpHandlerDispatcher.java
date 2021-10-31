package org.kttp.listener;

import org.kttp.listener.model.HttpMethod;
import org.kttp.listener.model.HttpRequest;
import org.kttp.listener.model.HttpResponse;
import org.kttp.listener.model.HttpStatusCode;

import java.util.Map;

public class HttpHandlerDispatcher implements HandlerDispatcher {
    Map<HttpMethod, Map<String, Handler>> handlers;

    public HttpHandlerDispatcher(Map<HttpMethod, Map<String, Handler>> handlers) {
        this.handlers = handlers;
    }

    @Override
    public HttpResponse handleRequest(HttpRequest request) {
        var handlersMap = handlers.get(request.getMetadata().getMethod());
        var handler = handlersMap.get(request.getMetadata().getUrl());

        if (handler == null) {
            return new HttpResponse("NOT FOUND", HttpStatusCode.NOT_FOUND);
        }
        return handler.handle(request);
    }

}
