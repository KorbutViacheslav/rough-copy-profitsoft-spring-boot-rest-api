package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.exeption.error.CSVGenerationException;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.exeption.error.ResourceIsExistException;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.exeption.error.ResourceNotFoundException;

import java.util.Date;

/**
 * Author: Viacheslav Korbut
 * Date: 17.04.2024
 */

/**
 * Global exception handler for handling various types of errors in the web application.
 * It provides specific handlers for each type of error and returns a response with the corresponding HTTP status.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Error message that occurs when generating a CSV report fails.
     */
    private final static String ENTITY_NOT_FOUND = "Entity not found with id = ";
    /**
     * Error message when the entity was deleted with the specified identifier.
     */
    private final static String ENTITY_IS_EXIST = "Such an entity already exists in the database";
    /**
     * Error message when the entity already exists in the database.
     */
    private final static String CSV_GENERATE = "Failed to generate CSV report";

    /**
     * Exception handler called when a CSV report generation error occurs.
     * Returns a response with status INTERNAL_SERVER_ERROR and an ErrorDetails object.
     */
    @ExceptionHandler(CSVGenerationException.class)
    public ResponseEntity<?> handleCSVGenerationException(WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                CSV_GENERATE, request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Exception handler called when a "Resource not found" error occurs.
     * Returns a response with status NOT_FOUND and an ErrorDetails object.
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                ENTITY_NOT_FOUND + getIdFromWebRequest(request),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    /**
     * Exception handler called when a "Resource already exists" error occurs.
     * Returns a response with status CONFLICT and an ErrorDetails object.
     */
    @ExceptionHandler(ResourceIsExistException.class)
    public ResponseEntity<?> resourceIsExistException(WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ENTITY_IS_EXIST, request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    /**
     * Extracts the identifier from the {@link WebRequest}.
     *
     * @param request the request object
     * @return the resource identifier
     */
    private static String getIdFromWebRequest(WebRequest request) {
        String path = request.getDescription(false);
        String[] pathSegments = path.split("/");
        return pathSegments[pathSegments.length - 1];
    }
}
