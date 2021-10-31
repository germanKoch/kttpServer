package org.kttp.context;

import org.kttp.context.model.annotations.Controller;
import org.kttp.context.model.exception.HandlersIntializationException;
import org.kttp.listener.Handler;
import org.kttp.listener.model.HttpResponse;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class RestHandlerInitializer extends AbstractHandlerInitializer {

    @Override
    protected Class<? extends Annotation> getAnnotationTrigger() {
        return Controller.class;
    }

    @Override
    protected Handler getHandler(Method annotatedMethod, Object annotatedObject) {
        //TODO: переделать без рефлексии
        return request -> {
            try {
                return (HttpResponse) annotatedMethod.invoke(annotatedObject, request);
            } catch (Exception e) {
                throw new HandlersIntializationException("Handlers should be get HttpRequest and return HttpResponse", e);
            }
        };
    }
}
