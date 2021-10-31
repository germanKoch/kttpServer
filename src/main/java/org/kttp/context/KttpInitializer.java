package org.kttp.context;

import org.kttp.listener.parser.HttpRequestParser;
import org.kttp.listener.HttpHandlerDispatcher;
import org.kttp.listener.KttpListener;

public class KttpInitializer {

    private final String basePackage;

    public KttpInitializer(String basePackage) {
        this.basePackage = basePackage;
    }

    public void start() {
        var handlers = new RestHandlerInitializer().init(basePackage);
        var dispatcher = new HttpHandlerDispatcher(handlers);
        var server = new KttpListener(new HttpRequestParser(), dispatcher);
        server.boostrap();
    }
}
