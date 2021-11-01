package org.kttp.listener.resolver;

import org.kttp.listener.Handler;
import org.kttp.listener.HandlerHolder;
import org.kttp.listener.model.HttpMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class HttpUrlMappingResolver implements UrlMappingResolver {

    private final HandlerHolder holder;
    private final List<RequestMappingPatternInfo> patternList;

    public HttpUrlMappingResolver(HandlerHolder holder) {
        this.holder = holder;

        this.patternList = new ArrayList<>();
        holder.getInfoSet().forEach(info ->
                patternList.add(
                        new RequestMappingPatternInfo(Pattern.compile(preparePattern(info.getUrlPattern())), info)
                )
        );
    }

    @Override
    public Handler resolve(HttpMethod method, String url) {
        return patternList.stream()
                .parallel()
                .filter(patternInfo ->
                        patternInfo.getRequestMappingInfo().getMethod() == method
                                && patternInfo.getPattern().matcher(url).matches())
                .findFirst()
                .map(requestMappingPatternInfo -> holder.get(requestMappingPatternInfo.getRequestMappingInfo()))
                .orElse(null);
    }

    private String preparePattern(String pattern) {
        return pattern.replace("*", ".*");
    }
}
