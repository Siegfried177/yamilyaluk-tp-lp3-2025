package py.edu.uc.lp32025.exception;

public class DiasInsuficientesException extends Exception {

    public DiasInsuficientesException() {
        super("El empleado no tiene suficientes días disponibles.");
    }

    public DiasInsuficientesException(String mensaje) {
        super(mensaje);
    }

    public DiasInsuficientesException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
