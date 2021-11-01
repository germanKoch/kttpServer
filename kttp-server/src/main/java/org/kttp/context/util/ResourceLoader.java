package org.kttp.context.util;

import org.kttp.context.model.exception.ResourceLoaderException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ResourceLoader {

    public static String getResource(String path) {
        var fileStream = ResourceLoader.class.getClassLoader().getResourceAsStream("static/" + path);
        if (fileStream == null) {
            throw new ResourceLoaderException("Resource not found");
        }
        try {
            return new String(fileStream.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new ResourceLoaderException("Resource can not be loaded", e);
        }
    }

}
