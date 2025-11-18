package py.edu.uc.lp32025.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import py.edu.uc.lp32025.exception.PermisoNoConcedidoException;
import py.edu.uc.lp32025.interfaces.GerentePermisionable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Slf4j
@Entity
@Getter
@Setter
@DiscriminatorValue("GERENTE")
public class Gerente extends Empleado implements GerentePermisionable {
    private int añosAntiguedad;
    private int diasVacacionesDisponibles = 30;
    private String areaResponsabilidad;
    // ---------------------------------------------------
    // CONSTRUCTORES
    // ---------------------------------------------------

    public Gerente() {
        super();
        this.añosAntiguedad = 1;
    }

    public Gerente(String nombre,
                   String apellido,
                   String numeroDocumento,
                   LocalDate fechaNacimiento,
                   PosicionGPS posicionGPS,
                   Avatar avatar,
                   LocalDate fechaContratacion,
                   int añosAntiguedad,
                   String areaResponsabilidad) {

        super(nombre, apellido, numeroDocumento, fechaNacimiento,
                posicionGPS, avatar, fechaContratacion);
        this.areaResponsabilidad = areaResponsabilidad;
        this.añosAntiguedad = añosAntiguedad;
    }

    public Gerente(String nombre,
                   String apellido,
                   String numeroDocumento,
                   LocalDate fechaNacimiento,
                   int añosAntiguedad) {
        super(nombre, apellido, numeroDocumento, fechaNacimiento);
        this.añosAntiguedad = añosAntiguedad;
    }

    // ---------------------------------------------------
    // MÉTODOS EXCLUSIVOS DE GERENTE
    // ---------------------------------------------------

    // =========================================================
    // IMPLEMENTACIÓN DEL MÉTODO EXCLUSIVO DE GERENTEPERMISIONABLE
    // =========================================================

    @Override
    public PosicionGPS ubicarElemento() {
        return super.ubicarElemento();
    }

    @Override
    public boolean autorizarPermisoSubordinado(Long subordinadoId, String comentarioGerente)
            throws PermisoNoConcedidoException {

        log.info("Gerente {} ({}) autoriza solicitud de Empleado ID: {}",
                this.getNombre(), this.areaResponsabilidad, subordinadoId);

        // Simulación de lógica: Un Gerente solo puede autorizar si no está de vacaciones.
        // Aquí iría la lógica de negocio para verificar si el subordinado existe,
        // y si el gerente tiene las credenciales para aprobar en esa área.

        // Lógica trivial: Siempre aprueba
        return true;
    }

    @Override
    public void solicitarVacaciones(Long empleadoId, LocalDate fechaInicio, LocalDate fechaFin)
            throws PermisoNoConcedidoException {

        long diasSolicitados = ChronoUnit.DAYS.between(fechaInicio, fechaFin.plusDays(1));

        // La lógica de Gerente SOBRESCRIBE la validación de días del Empleado base
        // (ya que tienen derechos especiales)
        log.info("[GERENTE ID: {}] Solicitud Vacaciones: Días: {}", this.getId(), diasSolicitados);

        // 1. Validaciones básicas de la clase padre (Antigüedad, Fechas, etc.)
        // Podemos llamar al método padre y luego añadir la lógica específica,
        // o reescribir toda la lógica de validación aquí.
        // Para la demo, reescribimos la lógica de días.

        // Regla: Solo los gerentes pueden solicitar más de 20 días (suponiendo que su antigüedad lo permite)
        if (diasSolicitados > 20) {
            log.warn("Gerente {} solicitando más de 20 días. Validando antigüedad especial...", this.getNombre());

            // Suponemos una regla específica: Gerentes con > 5 años tienen derecho a 25 días.
            if (ChronoUnit.YEARS.between(this.getFechaContratacion(), LocalDate.now()) < 5) {
                // Aquí lanzamos la excepción específica del ejercicio
                throw new PermisoNoConcedidoException("DiasInsuficientesException: Solicitud excede 20 días y no cumple requisito de antigüedad de Gerente.");
            }
            // Si tiene > 5 años, el Gerente puede solicitar hasta 25 días.
            if (diasSolicitados > 25) {
                throw new PermisoNoConcedidoException("Solicitud excede el máximo de 25 días para Gerente de alta antigüedad.");
            }
        }

        // Si no supera los 20 días, o si pasó la validación de 25 días...
        log.info("✅ Solicitud de Vacaciones para Gerente {} (ID: {}) Aprobada por Regla Especial.",
                this.getNombre(), this.getId());
    }

    @Override
    public void solicitarPermisoEspecial(Long empleadoId,
                                         LocalDate inicio,
                                         LocalDate fin,
                                         String motivo)
            throws PermisoNoConcedidoException {

        if (motivo == null || motivo.isBlank())
            throw new PermisoNoConcedidoException("Debe especificar un motivo.");

        long dias = fin.toEpochDay() - inicio.toEpochDay() + 1;

        if (dias > 10)
            throw new PermisoNoConcedidoException("Un permiso especial no puede exceder 10 días.");
    }

    // ---------------------------------------------------
    // MÉTODOS ABSTRACTOS DE PERSONA (OVERRIDE FINAL)
    // ---------------------------------------------------

    @Override
    public BigDecimal calcularSalario() {
        return new BigDecimal("12000000.00");
    }

    @Override
    protected BigDecimal calcularDeducciones() {
        return new BigDecimal("1500000.00");
    }

    @Override
    public boolean validarDatosEspecificos() {
        return añosAntiguedad >= 0;
    }

    @Override
    public BigDecimal getSalario() {
        return calcularSalario();
    }
}
