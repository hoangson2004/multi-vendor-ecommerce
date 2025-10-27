package hust.hoangson.order.exception;

import hust.hoangson.order.response.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<BaseResponse<Object>> handleRuntimeException(RuntimeException ex) {
        logger.error(ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.error(400, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<Object>> handleValidationException(MethodArgumentNotValidException ex) {
        logger.error(ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.error(400,  ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<Object>> handleGenericException(Exception ex) {
        logger.error(ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(BaseResponse.error(500, "Internal Server Error"));
    }
}
