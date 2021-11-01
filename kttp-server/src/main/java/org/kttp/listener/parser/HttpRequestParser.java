package org.kttp.listener.parser;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.kttp.listener.model.HttpHeaders;
import org.kttp.listener.model.HttpMethod;
import org.kttp.listener.model.HttpParam;
import org.kttp.listener.model.HttpRequest;
import org.kttp.listener.model.HttpRequestMeta;
import org.kttp.listener.model.HttpResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HttpRequestParser implements RequestParser {

    private static final String HEADER_END_TRIGGER = "\r\n\r\n";
    private static final String HEADER_END_TRIGGER_RESPONSE = "\n\n";

    public HttpRequest parseRequest(String request) {
        var parts = request.split(HEADER_END_TRIGGER);
        var metadata = parseMeta(parts[0]);

        String body = null;
        if (parts.length > 1) {
            body = parts[1];
        }
        return new HttpRequest(metadata, body);
    }

    public String parseResponse(HttpResponse response) {
        //TODO: вынести добавление базовых аннотаций в какой-то интерсептор
        var headers = response.getHeaders();
        var responseStr = new StringBuilder()
                .append("HTTP/1.1 ")
                .append(response.getStatus().getCode())
                .append(" ")
                .append(response.getStatus().getHttpCode())
                .append("\nServer: kttp\nConnection: close\n");
        headers.getHeaders().forEach((key, value) -> responseStr
                .append(key)
                .append(": ")
                .append(value));
        responseStr.append(HEADER_END_TRIGGER_RESPONSE);
        if (response.getBody() != null) {
            //TODO: Маппинг сделать умнее, с json
            responseStr.append(response.getBody());
        }
        return responseStr.toString();
    }

    public HttpRequestMeta parseMeta(String metaRequest) {
        var lines = metaRequest.split("\n");
        var metaInf = lines[0].split(" ");

        var method = HttpMethod.valueOf(metaInf[0]);
        var urlParts = parseParams(metaInf[1]);
        var protocolVersion = parseProtocolVersion(metaInf[2]);

        var headers = new HttpHeaders();
        Arrays.stream(lines).skip(1).filter(header -> header.contains(":")).forEach(headerStr -> {
            var headerArr = headerStr.split(":");
            headers.addHeader(headerArr[0].trim(), headerArr[1].trim());
        });

        return new HttpRequestMeta(protocolVersion, urlParts.getLeft(), headers, urlParts.getRight(), method);
    }

    private double parseProtocolVersion(String metaPart) {
        var parts = StringUtils.split(metaPart, "/");
        return Double.parseDouble(parts[1]);
    }

    private Pair<String, List<HttpParam>> parseParams(String paramsUrlPart) {
        int urlEnds = StringUtils.indexOf(paramsUrlPart, "?");
        urlEnds = urlEnds != -1 ? urlEnds : paramsUrlPart.length();

        var url = StringUtils.substring(paramsUrlPart, 0, urlEnds);
        var paramsStr = StringUtils.substring(paramsUrlPart, urlEnds + 1);

        List<HttpParam> params = new ArrayList<>();
        if (paramsStr != null && !paramsStr.isBlank()) {
            var paramsExprs = StringUtils.split(paramsStr, "&");
            for (var paramsExpr : paramsExprs) {
                var exprsParts = StringUtils.split(paramsExpr, "=");
                params.add(new HttpParam(exprsParts[0], exprsParts[1]));
            }
        }
        return Pair.of(url, params);
    }


}
