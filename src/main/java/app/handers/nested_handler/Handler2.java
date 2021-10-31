package app.handers.nested_handler;

import org.kttp.listener.model.HttpHeaders;
import org.kttp.listener.model.HttpMethod;
import org.kttp.listener.model.HttpRequest;
import org.kttp.listener.model.HttpResponse;
import org.kttp.listener.model.HttpStatusCode;
import org.kttp.context.model.annotations.Controller;
import org.kttp.context.model.annotations.RequestHandler;

@Controller
public class Handler2 {

    @RequestHandler(method = HttpMethod.GET, url = "/lalala")
    public HttpResponse handle(HttpRequest request) {
        var headers = new HttpHeaders();
        headers.addHeader("Content-Type", "text/html");
        return new HttpResponse(headers, "Hello-lalala", HttpStatusCode.OK);
    }

}
