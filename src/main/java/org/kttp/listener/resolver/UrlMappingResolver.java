package org.kttp.listener.resolver;


import org.kttp.listener.Handler;
import org.kttp.listener.model.HttpMethod;

public interface UrlMappingResolver {

    Handler resolve(HttpMethod method, String url);

}
