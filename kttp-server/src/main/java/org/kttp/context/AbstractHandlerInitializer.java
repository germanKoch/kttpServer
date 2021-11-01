package org.kttp.context;

import org.kttp.context.model.annotations.RequestHandler;
import org.kttp.context.model.exception.HandlersIntializationException;
import org.kttp.listener.Handler;
import org.kttp.listener.HandlerHolder;
import org.kttp.listener.model.mapping.RequestMappingInfo;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractHandlerInitializer implements HandlerInitializer {

    @Override
    public HandlerHolder init(List<String> packages, HandlerHolder holder) {
        packages.forEach(packageName -> {
            var reflections = new Reflections(packageName);
            var classes = reflections.getTypesAnnotatedWith(getAnnotationTrigger());
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
                        holder.add(new RequestMappingInfo(urlMethod, url), getHandler(handler, controller));
                    });
                } catch (Exception e) {
                    throw new HandlersIntializationException("Error while handler initialization", e);
                }
            });
        });
        return holder;
    }

    protected abstract Class<? extends Annotation> getAnnotationTrigger();

    protected abstract Handler getHandler(Method annotatedMethod, Object annotatedObject);
}
