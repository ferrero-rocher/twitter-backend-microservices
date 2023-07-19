package com.shaun.userservice.errorhandler;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HttpClientErrorException$BadRequest extends Throwable {
    private String message;

    public HttpClientErrorException$BadRequest(String message)
    {
        super();
        this.message=message;
    }
}
