package nais.sales.service.sales_service.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex, WebRequest request) {
        System.err.println("--- GLOBAL RUNTIME EXCEPTION CAUGHT (RUNTIME) ---");
        System.err.println("Path: " + request.getDescription(false));
        ex.printStackTrace();

        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex, WebRequest request) {
        System.err.println("--- GLOBAL EXCEPTION CAUGHT (GENERAL) ---");
        System.err.println("Path: " + request.getDescription(false));
        ex.printStackTrace();

        return new ResponseEntity<>("Internal Server Error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
