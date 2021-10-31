package org.kttp.server;

import org.kttp.server.model.HttpRequest;
import org.kttp.server.model.HttpResponse;

public interface Handler {

    HttpResponse handle(HttpRequest request);

}
