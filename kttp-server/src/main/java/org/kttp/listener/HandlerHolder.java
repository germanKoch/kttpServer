package org.kttp.listener;

import org.kttp.listener.model.mapping.RequestMappingInfo;

import java.util.Set;

public interface HandlerHolder {

    void add(RequestMappingInfo info, Handler handler);

    Handler get(RequestMappingInfo info);

    Set<RequestMappingInfo> getInfoSet();

}
