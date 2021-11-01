package app.handers;

import org.kttp.context.model.annotations.Controller;
import org.kttp.context.model.annotations.RequestHandler;
import org.kttp.listener.model.HttpHeaders;
import org.kttp.listener.model.HttpMethod;
import org.kttp.listener.model.HttpRequest;
import org.kttp.listener.model.HttpResponse;
import org.kttp.listener.model.HttpStatusCode;

@Controller
public class Handler1 {

    @RequestHandler(method = HttpMethod.GET, url = "/lala")
    public HttpResponse handleGet(HttpRequest request) {
        var headers = new HttpHeaders();
        headers.addHeader("Content-Type", "text/html");
        return new HttpResponse(HttpStatusCode.OK, "Hello-lala", headers);
    }

    @RequestHandler(method = HttpMethod.POST, url = "/lala")
    public HttpResponse handlePost(HttpRequest request) {
        var headers = new HttpHeaders();
        headers.addHeader("Content-Type", "text/html");
        return new HttpResponse(HttpStatusCode.OK, "Hello-post-lala", headers);
    }

    @RequestHandler(method = HttpMethod.GET, url = "/lala/pro")
    public HttpResponse handleGetTwo(HttpRequest request) {
        var headers = new HttpHeaders();
        headers.addHeader("Content-Type", "text/html");
        return new HttpResponse(HttpStatusCode.OK, "Hello-post-lala pro", headers);
    }

    @RequestHandler(method = HttpMethod.GET, url = "/lala/pro/*")
    public HttpResponse handleGetTwoPat(HttpRequest request) {
        var headers = new HttpHeaders();
        headers.addHeader("Content-Type", "text/html");
        return new HttpResponse(HttpStatusCode.OK, "Hello-post-lala with pattern", headers);
    }
}
