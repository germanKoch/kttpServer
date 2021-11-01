package org.kttp.listener;

import org.kttp.listener.model.HttpRequest;
import org.kttp.listener.model.HttpResponse;
import org.kttp.listener.model.HttpStatusCode;
import org.kttp.listener.resolver.UrlMappingResolver;

public class HttpHandlerDispatcher implements HandlerDispatcher {
    UrlMappingResolver resolver;

    public HttpHandlerDispatcher(UrlMappingResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public HttpResponse handleRequest(HttpRequest request) {
        var handler = resolver.resolve(
                request.getMetadata().getMethod(),
                request.getMetadata().getUrl()
        );

        if (handler == null) {
            return new HttpResponse(HttpStatusCode.NOT_FOUND, "NOT FOUND");
        }
        return handler.handle(request);
    }

}
