package py.edu.uc.lp32025.domain;

import py.edu.uc.lp32025.exception.PermisoNoConcedidoException;
import py.edu.uc.lp32025.interfaces.Mapeable;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
public class Empleado extends Persona implements Mapeable {

    private PosicionGPS posicionGPS;
    private Avatar avatar;

    // *** NUEVO ***
    private LocalDate fechaContratacion;

    // ---------------------------------------------------
    // CONSTRUCTORES
    // ---------------------------------------------------

    public Empleado() {
        super();
        this.setNombre("Empleado");
        this.setApellido("Mock");
        this.setNumeroDocumento("MOCK001");
        this.setFechaNacimiento(LocalDate.of(1990, 1, 1));

        this.posicionGPS = new PosicionGPS(-25.2637, -57.5759);
        this.avatar = new Avatar(
                "https://mock.example.com/empleado_default.jpg",
                "Empleado_MOCK_" + this.getNumeroDocumento()
        );

        // *** Asignación por defecto ***
        this.fechaContratacion = LocalDate.now().minusYears(2); // por defecto cumple antigüedad
    }

    public Empleado(String nombre, String apellido, String numeroDocumento,
                    LocalDate fechaNacimiento, PosicionGPS posicionGPS,
                    Avatar avatar, LocalDate fechaContratacion) {

        super(nombre, apellido, numeroDocumento, fechaNacimiento);
        this.posicionGPS = posicionGPS;
        this.avatar = avatar;
        this.fechaContratacion = fechaContratacion;
    }

    public Empleado(String nombre, String apellido, String numeroDocumento,
                    LocalDate fechaNacimiento) {

        super(nombre, apellido, numeroDocumento, fechaNacimiento);
        this.posicionGPS = null;
        this.avatar = null;

        // para que no quede null
        this.fechaContratacion = LocalDate.now();
    }

    // ---------------------------------------------------
    // MÉTODOS ABSTRACTOS DE PERSONA
    // ---------------------------------------------------

    @Override
    public BigDecimal calcularSalario() {
        return new BigDecimal("5000000.00");
    }

    @Override
    protected BigDecimal calcularDeducciones() {
        return new BigDecimal("500000.00");
    }

    @Override
    public boolean validarDatosEspecificos() {
        return true;
    }

    @Override
    public BigDecimal getSalario() {
        return calcularSalario();
    }

    // ---------------------------------------------------
    // IMPLEMENTACIÓN MAPEABLE
    // ---------------------------------------------------

    @Override
    public PosicionGPS ubicarElemento() {
        return posicionGPS;
    }

    @Override
    public Avatar obtenerImagen() {
        return avatar;
    }

    @Override
    public String getDisplaySummary() {

        String coord = (posicionGPS == null)
                ? "sin-pos"
                : String.format("lat=%.6f,lng=%.6f",
                posicionGPS.getLatitud(),
                posicionGPS.getLongitud());

        String nick = (avatar == null || avatar.getNick() == null)
                ? "no-nick"
                : avatar.getNick();

        return String.format("%s @ %s", nick, coord);
    }

    // ---------------------------------------------------
    // LÓGICA DE PERMISOS
    // ---------------------------------------------------

    public void solicitarVacaciones(Long empleadoId, LocalDate fechaInicio, LocalDate fechaFin)
            throws PermisoNoConcedidoException {

        log.info("Empleado ID {} - Solicitud de Vacaciones: {} a {}",
                empleadoId, fechaInicio, fechaFin);

        if (fechaInicio.isAfter(fechaFin)) {
            log.error("Solicitud Rechazada: Fechas invertidas.");
            throw new PermisoNoConcedidoException("La fecha de inicio no puede ser posterior a la fecha de fin.");
        }
    }

    public void solicitarPermisoEspecial(Long empleadoId, LocalDate fechaInicio, LocalDate fechaFin, String motivo)
            throws PermisoNoConcedidoException {

        log.info("Empleado ID {} - Solicitud de Permiso Especial (Motivo: {}): {} a {}",
                empleadoId, motivo, fechaInicio, fechaFin);

        if (motivo == null || motivo.trim().isEmpty()) {
            log.error("Solicitud Rechazada: Motivo requerido.");
            throw new PermisoNoConcedidoException("El motivo para el permiso especial es obligatorio.");
        }
    }

    // ---------------------------------------------------
    // GETTERS & SETTERS
    // ---------------------------------------------------

    public PosicionGPS getPosicionGPS() {
        return posicionGPS;
    }

    public void setPosicionGPS(PosicionGPS posicionGPS) {
        this.posicionGPS = posicionGPS;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    // *** NUEVO ***
    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }
}
