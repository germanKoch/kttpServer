package org.kttp.context;

import org.kttp.context.model.exception.HandlersIntializationException;
import org.kttp.context.model.exception.IncorrectAnnotationStateException;
import org.kttp.server.Handler;
import org.kttp.server.model.HttpMethod;
import org.kttp.server.model.annotations.KttpHandler;
import org.reflections.Reflections;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class HandlerInitializer {

    public Map<HttpMethod, Map<String, Handler>> init(String basePackage) {
        var reflections = new Reflections(basePackage);
        var handlers = new EnumMap<HttpMethod, Map<String, Handler>>(HttpMethod.class);
        var classes = reflections.getTypesAnnotatedWith(KttpHandler.class);

        classes.forEach(aClass -> {
            try {
                if (!Handler.class.isAssignableFrom(aClass)) {
                    throw new IncorrectAnnotationStateException();
                }
                var handler = ((Class<? extends Handler>) aClass).getDeclaredConstructor().newInstance();
                var annotation = aClass.getAnnotation(KttpHandler.class);
                var method = annotation.method();
                var url = annotation.url();

                var urlMap = handlers.computeIfAbsent(method, meth -> new HashMap<>());
                urlMap.put(url, handler);
            } catch (Exception e) {
                throw new HandlersIntializationException("Error while handler initialization", e);
            }
        });
        return handlers;
    }
}
