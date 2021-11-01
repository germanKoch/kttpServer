package org.kttp.handler;

import org.kttp.context.model.annotations.Controller;
import org.kttp.context.model.annotations.RequestHandler;
import org.kttp.context.model.annotations.ResourceController;
import org.kttp.context.util.ResourceLoader;
import org.kttp.listener.model.HttpHeaders;
import org.kttp.listener.model.HttpMethod;
import org.kttp.listener.model.HttpRequest;
import org.kttp.listener.model.HttpResponse;
import org.kttp.listener.model.HttpStatusCode;

@Controller
public class StaticResourceHandler {

    @RequestHandler(method = HttpMethod.GET, url = "/static/*.css")
    public HttpResponse getCssResource(HttpRequest request) {
        String resourcePath = request.getMetadata().getUrl().replace("/static/", "");
        String content = ResourceLoader.getResource(resourcePath);
        return new HttpResponse(HttpStatusCode.OK, content, new HttpHeaders("Content-Type", "text/css"));
    }

}
