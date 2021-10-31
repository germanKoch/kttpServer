package org.kttp.listener.resolver;

import lombok.RequiredArgsConstructor;
import org.kttp.listener.Handler;
import org.kttp.listener.HandlerHolder;
import org.kttp.listener.model.HttpMethod;
import org.kttp.listener.model.mapping.RequestMappingInfo;

@RequiredArgsConstructor
public class HttpUrlNappingResolver implements UrlMappingResolver {

    private final HandlerHolder holder;

    @Override
    public Handler resolve(HttpMethod method, String url) {
        return holder.get(new RequestMappingInfo(method, url));
    }
}
