package org.kttp.listener.resolver;

import lombok.Data;
import org.kttp.listener.model.mapping.RequestMappingInfo;

import java.util.regex.Pattern;

@Data
public class RequestMappingPatternInfo {

    private final Pattern pattern;

    private final RequestMappingInfo requestMappingInfo;

}
