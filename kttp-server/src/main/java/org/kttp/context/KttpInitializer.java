package org.kttp.context;

import org.kttp.context.util.BannerPrinter;
import org.kttp.listener.HttpHandlerDispatcher;
import org.kttp.listener.HttpHandlerHolder;
import org.kttp.listener.KttpListener;
import org.kttp.listener.parser.HttpRequestParser;
import org.kttp.listener.resolver.HttpUrlMappingResolver;

import java.util.List;

public class KttpInitializer {

    private final String basePackage;
    private final List<HandlerInitializer> handlerInitializers;

    public KttpInitializer(String basePackage) {
        this.basePackage = basePackage;
        this.handlerInitializers = List.of(
                new RestHandlerInitializer(),
                new ResourceHandlerInitializer()
        );
    }

    public void start() {
        BannerPrinter.printBanner();
        var holder = new HttpHandlerHolder();
        var scanningPackages = List.of(basePackage, "org.kttp");
        handlerInitializers.forEach(handlerInitializer -> handlerInitializer.init(scanningPackages, holder));

        var resolver = new HttpUrlMappingResolver(holder);
        var dispatcher = new HttpHandlerDispatcher(resolver);
        var server = new KttpListener(new HttpRequestParser(), dispatcher);
        server.boostrap();
    }
}
