package org.kttp.context;

import org.kttp.listener.HandlerHolder;

public interface HandlerInitializer {

    HandlerHolder init(String basePackage, HandlerHolder holdere);

}
