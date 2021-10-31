package org.kttp.listener;

import org.kttp.listener.model.mapping.RequestMappingInfo;

import java.util.HashMap;
import java.util.Map;

public class HttpHandlerHolder implements HandlerHolder {

    private final Map<RequestMappingInfo, Handler> handlers = new HashMap<>();

    @Override
    public void add(RequestMappingInfo info, Handler handler) {
        handlers.put(info, handler);
    }

    @Override
    public Handler get(RequestMappingInfo info) {
        return handlers.get(info);
    }
}
