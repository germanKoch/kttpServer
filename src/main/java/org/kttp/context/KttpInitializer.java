package org.kttp.context;

import org.kttp.parser.HttpParser;
import org.kttp.server.HandlerDispatcher;
import org.kttp.server.KttpListener;

public class KttpInitializer {

    private final String basePackage;

    public KttpInitializer(String basePackage) {
        this.basePackage = basePackage;
    }

    public void start() {
        var handlers = new HandlerInitializer().init(basePackage);
        var dispatcher = new HandlerDispatcher(handlers);
        var server = new KttpListener(new HttpParser(), dispatcher);
        server.boostrap();
    }
}
