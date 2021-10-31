package org.kttp.context;

import org.kttp.listener.Handler;
import org.kttp.listener.model.HttpMethod;

import java.util.Map;

public interface HandlerInitializer {

    Map<HttpMethod, Map<String, Handler>> init(String basePackage);

}
