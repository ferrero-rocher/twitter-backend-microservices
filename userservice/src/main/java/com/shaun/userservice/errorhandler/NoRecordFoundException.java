package com.shaun.userservice.errorhandler;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class NoRecordFoundException extends RuntimeException{
    private String message;

    public NoRecordFoundException(String message)
    {
        super();
        this.message=message;
    }


}
