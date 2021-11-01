package org.kttp.context;

import org.kttp.listener.HandlerHolder;

import java.util.List;

public interface HandlerInitializer {

    HandlerHolder init(List<String> packages, HandlerHolder holder);

}
