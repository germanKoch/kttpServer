package org.kttp.listener;

import org.kttp.listener.model.mapping.RequestMappingInfo;

import java.util.Map;

public class HttpHandlerHolder implements HandlerHolder {

    private Map<RequestMappingInfo, Handler> handlers;

    @Override
    public void add(RequestMappingInfo info, Handler handler) {

    }

    @Override
    public Handler get(RequestMappingInfo info) {
        return null;
    }
}
