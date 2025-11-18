package py.edu.uc.lp32025.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import py.edu.uc.lp32025.dto.ErrorResponseDTO;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    // -------------------------------------------------
    // Manejo de Nuevas Excepciones de Nómina/Permisos
    // -------------------------------------------------

    /**
     * Maneja la Checked Exception: DiasInsuficientesException (HTTP 400 Bad Request).
     * Lanzada cuando el empleado solicita más días de los disponibles.
     */
    @ExceptionHandler(DiasInsuficientesException.class)
    public ResponseEntity<ErrorResponseDTO> handleDiasInsuficientesException(DiasInsuficientesException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                status.value(),
                ex.getClass().getSimpleName(), // Mensaje técnico
                ex.getMessage()               // Mensaje específico de la excepción para el usuario
        );
        return new ResponseEntity<>(errorResponse, status);
    }

    /**
     * Maneja la excepción de negocio: PermisoNoConcedidoException (HTTP 400 Bad Request).
     * Lanzada cuando no se cumplen las condiciones para otorgar un permiso.
     */
    @ExceptionHandler(PermisoNoConcedidoException.class)
    public ResponseEntity<ErrorResponseDTO> handlePermisoNoConcedidoException(PermisoNoConcedidoException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                status.value(),
                ex.getClass().getSimpleName(), // Mensaje técnico
                ex.getMessage()               // Mensaje específico de la excepción para el usuario
        );
        return new ResponseEntity<>(errorResponse, status);
    }

    /**
     * Maneja la Runtime Exception: EmpleadoNoEncontradoException (HTTP 404 Not Found).
     * Lanzada cuando se busca un empleado por ID que no existe.
     */
    @ExceptionHandler(EmpleadoNoEncontradoException.class)
    public ResponseEntity<ErrorResponseDTO> handleEmpleadoNoEncontradoException(EmpleadoNoEncontradoException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                status.value(),
                ex.getClass().getSimpleName(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, status);
    }

    // -------------------------------------------------
    // Manejo de fecha inválida (Existente)
    // -------------------------------------------------
    @ExceptionHandler(FechaInvalidaException.class)
    public ResponseEntity<ErrorResponseDTO> handleFutureDateException(FechaInvalidaException ex) {
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                ex.getClass().getSimpleName(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


    // -------------------------------------------------
    // Manejo de errores de negocio generales (Existente, consolidado)
    // -------------------------------------------------
    @ExceptionHandler({ResponseStatusException.class, IllegalArgumentException.class})
    public ResponseEntity<ErrorResponseDTO> handleEmpleadoInvalido(RuntimeException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        // ResponseStatusException puede tener un estado diferente
        if (ex instanceof ResponseStatusException) {
            // --- CORRECCIÓN AQUÍ ---
            ResponseStatusException rsEx = (ResponseStatusException) ex;

            // Intentamos convertir HttpStatusCode a HttpStatus si es posible
            if (rsEx.getStatusCode() instanceof HttpStatus) {
                status = (HttpStatus) rsEx.getStatusCode();
            } else {
                // Si no es un HttpStatus directo, usamos el valor numérico
                status = HttpStatus.valueOf(rsEx.getStatusCode().value());
            }
            // --- FIN DE LA CORRECCIÓN ---
        }

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                status.value(),
                ex.getClass().getSimpleName(),
                ex.getMessage()
        );
        return new ResponseEntity<>(errorResponse, status);
    }

    // -------------------------------------------------
    // Manejo de validaciones de DTOs (@RequestBody) (Existente)
    // -------------------------------------------------
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> errores = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Validation/DTO_Error", // Más específico
                "Error en los campos: " + String.join("; ", errores)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // -------------------------------------------------
    // Manejo de validaciones de entidades persistidas (ConstraintViolation) (Existente)
    // -------------------------------------------------
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleConstraintViolation(ConstraintViolationException ex) {
        List<String> errores = ex.getConstraintViolations()
                .stream()
                // Mapear a un mensaje claro que incluya la propiedad si es posible
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.toList());

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "ConstraintViolation/JPA_Error",
                "Error de persistencia: " + String.join("; ", errores)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // -------------------------------------------------
    // Manejo General
    // -------------------------------------------------
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneralException(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                status.value(),
                ex.getClass().getName(),
                "Ocurrió un error interno del servidor. Intente más tarde."
        );
        return new ResponseEntity<>(errorResponse, status);
    }
}