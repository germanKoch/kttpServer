package org.kttp.server.model.annotations;

import org.kttp.server.model.HttpMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface KttpHandler {
    HttpMethod method();

    String url();
}
