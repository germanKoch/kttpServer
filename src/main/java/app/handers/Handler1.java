package app.handers;

import org.kttp.listener.model.HttpHeaders;
import org.kttp.listener.model.HttpMethod;
import org.kttp.listener.model.HttpRequest;
import org.kttp.listener.model.HttpResponse;
import org.kttp.listener.model.HttpStatusCode;
import org.kttp.context.model.annotations.Controller;
import org.kttp.context.model.annotations.RequestHandler;

@Controller
public class Handler1 {

    @RequestHandler(method = HttpMethod.GET, url = "/lala")
    public HttpResponse handleGet(HttpRequest request) {
        var headers = new HttpHeaders();
        headers.addHeader("Content-Type", "text/html");
        return new HttpResponse(headers, "Hello-lala", HttpStatusCode.OK);
    }

    @RequestHandler(method = HttpMethod.POST, url = "/lala")
    public HttpResponse handlePost(HttpRequest request) {
        var headers = new HttpHeaders();
        headers.addHeader("Content-Type", "text/html");
        return new HttpResponse(headers, "Hello-post-lala", HttpStatusCode.OK);
    }

}
