package py.edu.uc.lp32025.exception;

public class EmpleadoNoEncontradoException extends RuntimeException {

    public EmpleadoNoEncontradoException() {
        super("Empleado no encontrado.");
    }

    public EmpleadoNoEncontradoException(String mensaje) {
        super(mensaje);
    }

    public EmpleadoNoEncontradoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
