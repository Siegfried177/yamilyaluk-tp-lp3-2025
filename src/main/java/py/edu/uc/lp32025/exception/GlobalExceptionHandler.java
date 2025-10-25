package py.edu.uc.lp32025.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import py.edu.uc.lp32025.dto.ErrorResponseDTO;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FechaInvalidaException.class)
    public ResponseEntity<ErrorResponseDTO> handleFutureDateException(FechaInvalidaException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                ex.getClass().getSimpleName(), // mensaje técnico
                ex.getMessage() // mensaje usuario
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
