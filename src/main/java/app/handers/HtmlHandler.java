package app.handers;

import org.kttp.context.model.annotations.RequestHandler;
import org.kttp.context.model.annotations.ResourceController;
import org.kttp.listener.model.HttpMethod;
import org.kttp.listener.model.HttpRequest;

@ResourceController
public class HtmlHandler {

    @RequestHandler(method = HttpMethod.GET, url = "/index")
    public String getResource(HttpRequest request) {
        return "index";
    }

}
