package org.kttp;

import org.kttp.model.HttpHeaders;
import org.kttp.model.HttpMethod;
import org.kttp.model.HttpRequest;

import java.util.Arrays;

public class KttpParser {


//    private static final String HEADERS =
//            "HTTP/1.1 200 OK\n" +
//                    "Server: naive\n" +
//                    "Content-Type: text/html\n" +
//                    "Content-Length: %s\n" +
//                    "Connection: close\n\n";
//
//    public String handleRequest(String request) {
//        var lines = request.split("\n");
//
//        var metaInf = lines[0].split(" ");
//
//        var method = HttpMethod.valueOf(metaInf[0]);
//        var url = metaInf[1];
//
//
//        var protocolVersion = Double.parseDouble(metaInf[2].substring(5));
//        var headers = new HttpHeaders();
//
//        Arrays.stream(lines).skip(1).filter(header -> header.contains(":")).forEach(headerStr -> {
//            var headerArr = headerStr.split(":");
//            headers.addHeader(headerArr[0].trim(), headerArr[1].trim());
//        });
//        new HttpRequest(protocolVersion, url, headers, null, null, null);
//        return HEADERS;
//    }

}
