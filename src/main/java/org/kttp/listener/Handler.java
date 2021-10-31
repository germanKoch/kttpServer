package org.kttp.listener;

import org.kttp.listener.model.HttpRequest;
import org.kttp.listener.model.HttpResponse;

public interface Handler {

    HttpResponse handle(HttpRequest request);

}
