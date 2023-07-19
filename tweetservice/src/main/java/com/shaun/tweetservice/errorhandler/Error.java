package com.shaun.tweetservice.errorhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Error {

    private String statusCode;
    private String description;
    private String timestamp;

    public Error(String statusCode,String description)
    {
        this.statusCode = statusCode;
        this.description=description;
        this.timestamp=calculateTimeStamp();
    }

    private String calculateTimeStamp() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now).toString();

    }


}

