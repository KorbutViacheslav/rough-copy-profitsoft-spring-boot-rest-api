package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.exeption;

import lombok.Getter;

import java.util.Date;

/**
 * Author: Viacheslav Korbut
 * Date: 19.04.2024
 */

/**
 * Represents details of an exception, including the timestamp of when the exception occurred,
 * the exception message, and additional details about the exception.
 */
@Getter
public class ErrorDetails {

    private final Date timestamp;

    private final String message, details;

    public ErrorDetails(Date timestamp, String message, String details) {
        super();
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

}
