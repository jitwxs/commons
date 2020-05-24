package com.github.jitwxs.commons.web.http;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class HttpResult {
    private int status;
    private String message;
    private String data;

    public boolean isSuccess() {
        return status == 200;
    }
}