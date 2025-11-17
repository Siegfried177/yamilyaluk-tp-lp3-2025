package py.edu.uc.lp32025.exception;

/**
 * Excepción lanzada cuando una solicitud de vacaciones o permiso es rechazada
 * por no cumplir con las reglas de negocio, antigüedad o documentación.
 * Mapeada a 400 Bad Request o 403 Forbidden en la capa de controlador.
 */
public class PermisoNoConcedidoException extends RuntimeException {

    public PermisoNoConcedidoException(String mensaje) {
        super("Fallo en la Solicitud de Permiso: " + mensaje);
    }
}