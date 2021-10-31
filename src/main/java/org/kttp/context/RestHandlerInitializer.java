package org.kttp.context;

import org.kttp.context.model.annotations.Controller;
import org.kttp.context.model.annotations.RequestHandler;
import org.kttp.context.model.exception.HandlersIntializationException;
import org.kttp.listener.Handler;
import org.kttp.listener.model.HttpMethod;
import org.kttp.listener.model.HttpResponse;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RestHandlerInitializer implements HandlerInitializer {

    @Override
    public Map<HttpMethod, Map<String, Handler>> init(String basePackage) {
        var reflections = new Reflections(basePackage);
        var handlersMap = new EnumMap<HttpMethod, Map<String, Handler>>(HttpMethod.class);
        var classes = reflections.getTypesAnnotatedWith(Controller.class);

        classes.forEach(aClass -> {
            try {
                var controller = aClass.getDeclaredConstructor().newInstance();
                var handlers = Arrays.stream(aClass.getMethods())
                        .filter(method -> method.isAnnotationPresent(RequestHandler.class))
                        .collect(Collectors.toList());
                handlers.forEach(handler -> {
                    var annotation = handler.getAnnotation(RequestHandler.class);
                    var urlMethod = annotation.method();
                    var url = annotation.url();
                    var urlMap = handlersMap.computeIfAbsent(urlMethod, meth -> new HashMap<>());
                    urlMap.put(url, request -> {
                        try {
                            return (HttpResponse) handler.invoke(controller, request);
                        } catch (Exception e) {
                            throw new HandlersIntializationException("Handlers should be get HttpRequest and return HttpResponse", e);
                        }
                    });
                });
            } catch (Exception e) {
                throw new HandlersIntializationException("Error while handler initialization", e);
            }
        });
        return handlersMap;
    }
}
