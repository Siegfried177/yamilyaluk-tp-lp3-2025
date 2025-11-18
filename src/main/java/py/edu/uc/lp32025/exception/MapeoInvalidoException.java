package py.edu.uc.lp32025.exception;

/**
 * Excepción lanzada cuando un Mapper intenta convertir una entidad o DTO
 * que no es del tipo esperado, o cuando falla una transformación de datos crucial.
 * * Es una RuntimeException (unchecked) porque generalmente representa un error
 * de programación o configuración que no se espera que el código llamador maneje
 * de manera explícita (vs. una Checked Exception que obliga a manejarla).
 */
public class MapeoInvalidoException extends RuntimeException {

    // Constructor que acepta solo un mensaje
    public MapeoInvalidoException(String message) {
        super(message);
    }

    // Constructor que acepta un mensaje y la causa original
    public MapeoInvalidoException(String message, Throwable cause) {
        super(message, cause);
    }

    // Constructor que acepta la causa original (útil para envolver otras excepciones)
    public MapeoInvalidoException(Throwable cause) {
        super(cause);
    }
}