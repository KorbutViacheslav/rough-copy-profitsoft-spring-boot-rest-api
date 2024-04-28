package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.exeption.error.CSVGenerationException;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.exeption.error.ResourceIsExistException;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.exeption.error.ResourceNotFoundException;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.exeption.error.ResourceWasDeletedException;

import java.util.Date;

/**
 * Author: Viacheslav Korbut
 * Date: 17.04.2024
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private final static String ENTITY_NOT_FOUND = "Entity not found with id = ";
    private final static String ENTITY_WAS_DELETED = "Entity was deleted with id = ";
    private final static String ENTITY_IS_EXIST = "Such an entity already exists in the database";
    private final static String CSV_GENERATE = "Failed to generate CSV report";

    @ExceptionHandler(CSVGenerationException.class)
    public ResponseEntity<?> handleCSVGenerationException(WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                CSV_GENERATE, request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                ENTITY_NOT_FOUND + getIdFromWebRequest(request),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceWasDeletedException.class)
    public ResponseEntity<?> resourceWasDeletedException(WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                ENTITY_WAS_DELETED + getIdFromWebRequest(request),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.GONE);
    }

    @ExceptionHandler(ResourceIsExistException.class)
    public ResponseEntity<?> resourceIsExistException(WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(), ENTITY_IS_EXIST, request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT);
    }

    private static String getIdFromWebRequest(WebRequest request) {
        String path = request.getDescription(false);
        String[] pathSegments = path.split("/");
        return pathSegments[pathSegments.length - 1];
    }
}
