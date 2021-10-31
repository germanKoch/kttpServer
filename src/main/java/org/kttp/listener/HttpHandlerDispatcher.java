package org.kttp.listener;

import org.kttp.listener.model.HttpRequest;
import org.kttp.listener.model.HttpResponse;
import org.kttp.listener.model.HttpStatusCode;
import org.kttp.listener.model.mapping.RequestMappingInfo;

public class HttpHandlerDispatcher implements HandlerDispatcher {
    HandlerHolder holder;

    public HttpHandlerDispatcher(HandlerHolder holder) {
        this.holder = holder;
    }

    @Override
    public HttpResponse handleRequest(HttpRequest request) {
        var requestInfo = new RequestMappingInfo(
                request.getMetadata().getMethod(),
                request.getMetadata().getUrl()
        );
        var handler = holder.get(requestInfo);

        if (handler == null) {
            return new HttpResponse("NOT FOUND", HttpStatusCode.NOT_FOUND);
        }
        return handler.handle(request);
    }

}
