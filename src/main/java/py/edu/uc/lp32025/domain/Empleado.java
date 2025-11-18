package py.edu.uc.lp32025.domain;

import lombok.Getter;
import lombok.Setter;
import py.edu.uc.lp32025.exception.DiasInsuficientesException;
import py.edu.uc.lp32025.exception.PermisoNoConcedidoException;
import py.edu.uc.lp32025.interfaces.Mapeable;
import lombok.extern.slf4j.Slf4j;
import py.edu.uc.lp32025.interfaces.Permisionable;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@Slf4j
public class Empleado extends Persona implements Mapeable, Permisionable {

    private PosicionGPS posicionGPS;
    private Avatar avatar;

    private LocalDate fechaContratacion;

    // NUEVO: Número de días de vacaciones solicitados
    private int diasSolicitados;

    // NUEVO: Número de días de vacaciones disponibles
    private int diasDisponibles;

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

        this.fechaContratacion = LocalDate.now().minusYears(2);
        this.diasSolicitados = 0;
        this.diasDisponibles = 20; // valor por defecto de días disponibles
    }

    public Empleado(String nombre, String apellido, String numeroDocumento,
                    LocalDate fechaNacimiento, PosicionGPS posicionGPS,
                    Avatar avatar, LocalDate fechaContratacion) {
        super(nombre, apellido, numeroDocumento, fechaNacimiento);
        this.posicionGPS = posicionGPS;
        this.avatar = avatar;
        this.fechaContratacion = fechaContratacion;
        this.diasSolicitados = 0;
        this.diasDisponibles = 20;
    }

    public Empleado(String nombre, String apellido, String numeroDocumento,
                    LocalDate fechaNacimiento) {
        super(nombre, apellido, numeroDocumento, fechaNacimiento);
        this.posicionGPS = null;
        this.avatar = null;
        this.fechaContratacion = LocalDate.now();
        this.diasSolicitados = 0;
        this.diasDisponibles = 20;
    }

    @Override
    public BigDecimal calcularSalario() {
        return null;
    }

    @Override
    protected BigDecimal calcularDeducciones() {
        return null;
    }

    @Override
    public boolean validarDatosEspecificos() {
        return false;
    }

    @Override
    public BigDecimal getSalario() {
        return null;
    }

    // ---------------------------------------------------
    // LÓGICA DE VACACIONES
    // ---------------------------------------------------

    public void solicitarVacaciones(Long empleadoId, LocalDate fechaInicio, LocalDate fechaFin)
            throws PermisoNoConcedidoException, DiasInsuficientesException {

        log.info("Empleado ID {} - Solicitud de Vacaciones: {} a {}", empleadoId, fechaInicio, fechaFin);

        if (fechaInicio.isAfter(fechaFin)) {
            log.error("Solicitud Rechazada: Fechas invertidas.");
            throw new PermisoNoConcedidoException("La fecha de inicio no puede ser posterior a la fecha de fin.");
        }

        int diasSolicitadosAhora = (int) java.time.temporal.ChronoUnit.DAYS.between(fechaInicio, fechaFin) + 1;

        if (diasSolicitadosAhora > this.diasDisponibles) {
            throw new DiasInsuficientesException(
                    "No hay suficientes días de vacaciones disponibles. Solicitados: "
                            + diasSolicitadosAhora + ", disponibles: " + this.diasDisponibles
            );
        }

        this.diasDisponibles -= diasSolicitadosAhora;
        this.diasSolicitados += diasSolicitadosAhora;

        log.info("Vacaciones aprobadas. Días restantes: {}", this.diasDisponibles);
    }

    @Override
    public void solicitarPermisoEspecial(Long empleadoId, LocalDate fechaInicio, LocalDate fechaFin, String motivo) throws PermisoNoConcedidoException {
    }

    @Override
    public PosicionGPS ubicarElemento() {
        return null;
    }

    @Override
    public Avatar obtenerImagen() {
        return null;
    }
}