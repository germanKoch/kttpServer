package org.kttp.listener;

import org.kttp.listener.model.HttpRequest;
import org.kttp.listener.model.HttpResponse;

public interface HandlerDispatcher {
    HttpResponse handleRequest(HttpRequest request);
}
