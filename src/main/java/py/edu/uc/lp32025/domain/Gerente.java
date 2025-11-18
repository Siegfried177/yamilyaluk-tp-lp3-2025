package py.edu.uc.lp32025.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import py.edu.uc.lp32025.exception.DiasInsuficientesException;
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
        // ... (Lógica de autorización) ...
        return true;
    }

    @Override
    public void solicitarVacaciones(Long empleadoId, LocalDate fechaInicio, LocalDate fechaFin)
            throws PermisoNoConcedidoException, DiasInsuficientesException { // AÑADIDA DIASINSUFICIENTESEXCEPTION

        long diasSolicitados = ChronoUnit.DAYS.between(fechaInicio, fechaFin.plusDays(1));

        log.info("[GERENTE ID: {}] Solicitud Vacaciones: Días: {}", this.getId(), diasSolicitados);

        // 1. Validación de fechas (se mantiene)
        if (fechaInicio.isAfter(fechaFin)) {
            throw new PermisoNoConcedidoException("La fecha de inicio no puede ser posterior a la fecha de fin.");
        }

        // 2. Lógica Especial de Gerente (Más de 20 días)
        if (diasSolicitados > 20) {
            log.warn("Gerente {} solicitando más de 20 días. Validando antigüedad especial...", this.getNombre());
            // Regla: Si solicita más de 20 días Y no tiene 5 años de antigüedad
            if (añosAntiguedad < 5) {
                log.error("Gerente ID {} - Solicitud Rechazada: {} días sin 5 años de antigüedad.", empleadoId, diasSolicitados);

                // 🚨 Se lanza PermisoNoConcedidoException, ya que la regla de negocio prohíbe el permiso.
                throw new PermisoNoConcedidoException(
                        "Solicitud excede 20 días. Se requiere 5 años de antigüedad como Gerente para días adicionales (Antigüedad actual: " + añosAntiguedad + " años)."
                );
            }

            // Regla: Si supera el máximo absoluto de 25 días (incluso con antigüedad)
            if (diasSolicitados > 25) {
                // *** CAMBIO CLAVE: Lanzar DiasInsuficientesException ***
                throw new DiasInsuficientesException("Solicitud excede el máximo absoluto de 25 días permitido para Gerentes de alta antigüedad.");
            }
        }

        // 3. Chequeo contra días disponibles totales (30)
        if (diasSolicitados > this.diasVacacionesDisponibles) {
            // También se lanza DiasInsuficientesException aquí por si se agotaron los 30 días.
            throw new DiasInsuficientesException(
                    "Días agotados. Solicitados: " + diasSolicitados +
                            ", disponibles: " + this.diasVacacionesDisponibles
            );
        }

        // Si pasa todas las validaciones
        this.diasVacacionesDisponibles -= diasSolicitados;

        log.info("✅ Solicitud de Vacaciones para Gerente {} (ID: {}) Aprobada por Regla Especial. Días restantes: {}",
                this.getNombre(), this.getId(), this.diasVacacionesDisponibles);
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
