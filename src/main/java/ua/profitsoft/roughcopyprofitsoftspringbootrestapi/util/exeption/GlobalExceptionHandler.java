package ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.exeption.book.ResourceNotFoundException;
import ua.profitsoft.roughcopyprofitsoftspringbootrestapi.util.exeption.book.ResourceWasDeletedException;

import java.util.Date;

/**
 * Author: Viacheslav Korbut
 * Date: 17.04.2024
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private final static String BOOK_NOT_FOUND = "Book not found with id = ";
    private final static String BOOK_WAS_DELETED = "Book was deleted with id = ";

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                BOOK_NOT_FOUND + getIdFromWebRequest(request),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceWasDeletedException.class)
    public ResponseEntity<?> resourceWasDeletedException(WebRequest request) {
        ErrorDetails errorDetails = new ErrorDetails(new Date(),
                BOOK_WAS_DELETED + getIdFromWebRequest(request),
                request.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.GONE);
    }

    private static String getIdFromWebRequest(WebRequest request) {
        String path = request.getDescription(false);
        String[] pathSegments = path.split("/");
        return pathSegments[pathSegments.length - 1];
    }
}
