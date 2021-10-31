package org.kttp.listener.model.mapping;

import lombok.Data;
import org.kttp.listener.model.HttpMethod;

@Data
public class RequestMappingInfo {

    private HttpMethod method;

    private String urlPattern;

}
