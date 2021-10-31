package org.kttp.context;

import org.kttp.context.model.annotations.ResourceController;
import org.kttp.context.model.exception.HandlersIntializationException;
import org.kttp.listener.Handler;
import org.kttp.listener.model.HttpHeaders;
import org.kttp.listener.model.HttpResponse;
import org.kttp.listener.model.HttpStatusCode;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;

public class ResourceHandlerInitializer extends AbstractHandlerInitializer {

    @Override
    protected Class<? extends Annotation> getAnnotationTrigger() {
        return ResourceController.class;
    }

    @Override
    protected Handler getHandler(Method annotatedMethod, Object annotatedObject) {
        return request -> {
            try {
                var htmlFileName = (String) annotatedMethod.invoke(annotatedObject, request);
                var fileStream = getClass().getClassLoader().getResourceAsStream("static/" + htmlFileName + ".html");
                var content = new String(fileStream.readAllBytes(), StandardCharsets.UTF_8);
                var headers = new HttpHeaders("Content-Type", "text/html");
                return new HttpResponse(headers, content, HttpStatusCode.OK);
            } catch (ClassCastException e) {
                throw new HandlersIntializationException("Methods of ResourceController should return string", e);
            } catch (Exception e) {
                throw new HandlersIntializationException("Resource can not be found", e);
            }
        };
    }
}
