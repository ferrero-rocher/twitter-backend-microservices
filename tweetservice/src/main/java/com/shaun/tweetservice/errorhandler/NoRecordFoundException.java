package com.shaun.tweetservice.errorhandler;

import lombok.Data;
import lombok.NoArgsConstructor;

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
