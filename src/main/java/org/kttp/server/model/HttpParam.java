package org.kttp.server.model;

import lombok.Data;

@Data
public class HttpParam {
    private final String name;
    private final String value;
}
