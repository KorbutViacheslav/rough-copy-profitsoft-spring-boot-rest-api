package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.exeption;

import lombok.Getter;

import java.util.Date;

/**
 * Author: Viacheslav Korbut
 * Date: 19.04.2024
 */
@Getter
public class ErrorDetails {
    private Date timestamp;
    private String message, details;

    public ErrorDetails(Date timestamp, String message, String details) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

}
