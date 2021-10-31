package org.kttp.context;

import org.kttp.listener.HttpHandlerDispatcher;
import org.kttp.listener.HttpHandlerHolder;
import org.kttp.listener.KttpListener;
import org.kttp.listener.parser.HttpRequestParser;

public class KttpInitializer {

    private final String basePackage;

    public KttpInitializer(String basePackage) {
        this.basePackage = basePackage;
    }

    public void start() {
        var holder = new HttpHandlerHolder();
        var handlers = new RestHandlerInitializer().init(basePackage, holder);
        var dispatcher = new HttpHandlerDispatcher(handlers);
        var server = new KttpListener(new HttpRequestParser(), dispatcher);
        server.boostrap();
    }
}
