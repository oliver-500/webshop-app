package org.unibl.etf.ip.webshop.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LogMessage {
    private String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"));
    private String className;

    public LogMessage(Object clazz, Error errorType, String message) {
        this.className = clazz.getClass().getCanonicalName();
        this.errorType = errorType;
        this.message = message;
    }

    private Error errorType;
    private String message;

    public enum Error{
        INFO,
        WARN,
        DEBUG,
        ERROR;



    }



}
