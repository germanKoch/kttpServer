package org.kttp.context;

import org.kttp.context.model.annotations.ResourceController;
import org.kttp.context.model.exception.HandlersIntializationException;
import org.kttp.context.util.ResourceLoader;
import org.kttp.listener.Handler;
import org.kttp.listener.model.HttpHeaders;
import org.kttp.listener.model.HttpResponse;
import org.kttp.listener.model.HttpStatusCode;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ResourceHandlerInitializer extends AbstractHandlerInitializer {

    @Override
    protected Class<? extends Annotation> getAnnotationTrigger() {
        return ResourceController.class;
    }

    @Override
    protected Handler getHandler(Method annotatedMethod, Object annotatedObject) {
        //todo: проверять сразу возращаемый тип контрллера, чтоб ошибка была не врантайме
        return request -> {
            try {
                var htmlFileName = (String) annotatedMethod.invoke(annotatedObject, request);
                var content = ResourceLoader.getResource(htmlFileName + ".html");
                var headers = new HttpHeaders("Content-Type", "text/html");
                return new HttpResponse(HttpStatusCode.OK, content, headers);
            } catch (ClassCastException | IllegalAccessException | InvocationTargetException e) {
                throw new HandlersIntializationException("Methods of ResourceController should return string", e);
            }
        };
    }
}
