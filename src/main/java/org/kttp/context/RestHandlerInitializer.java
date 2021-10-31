package org.kttp.context;

import org.kttp.context.model.annotations.Controller;
import org.kttp.context.model.annotations.RequestHandler;
import org.kttp.context.model.exception.HandlersIntializationException;
import org.kttp.listener.HandlerHolder;
import org.kttp.listener.model.HttpResponse;
import org.kttp.listener.model.mapping.RequestMappingInfo;
import org.reflections.Reflections;

import java.util.Arrays;
import java.util.stream.Collectors;

public class RestHandlerInitializer implements HandlerInitializer {

    @Override
    public HandlerHolder init(String basePackage, HandlerHolder holder) {
        var reflections = new Reflections(basePackage);
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
                    holder.add(new RequestMappingInfo(urlMethod, url), request -> {
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
        return holder;
    }
}
