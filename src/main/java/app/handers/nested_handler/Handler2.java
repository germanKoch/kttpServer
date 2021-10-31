package app.handers.nested_handler;

import org.kttp.server.Handler;
import org.kttp.server.model.HttpHeaders;
import org.kttp.server.model.HttpMethod;
import org.kttp.server.model.HttpRequest;
import org.kttp.server.model.HttpResponse;
import org.kttp.server.model.HttpStatusCode;
import org.kttp.server.model.annotations.KttpHandler;

@KttpHandler(method = HttpMethod.GET, url = "/lalala")
public class Handler2 implements Handler {
    @Override
    public HttpResponse handle(HttpRequest request) {
        var headers = new HttpHeaders();
        headers.addHeader("Content-Type", "text/html");
        return new HttpResponse(headers, "Hello-lalala", HttpStatusCode.OK);
    }
}
