package py.edu.uc.lp32025.interfaces;

import py.edu.uc.lp32025.exception.PermisoNoConcedidoException;
import java.time.LocalDate;

public interface Permisionable {

    /**
     * Solicita un período de vacaciones para un empleado específico.
     * La lógica de negocio calculará los días y validará la antigüedad.
     *
     * @param empleadoId El ID único del empleado que solicita las vacaciones.
     * @param fechaInicio La fecha de inicio de las vacaciones.
     * @param fechaFin La fecha de fin de las vacaciones (último día de ausencia).
     * @throws PermisoNoConcedidoException Si el solicitante no cumple con los requisitos legales (antigüedad, días disponibles).
     */
    void solicitarVacaciones(Long empleadoId, LocalDate fechaInicio, LocalDate fechaFin)
            throws PermisoNoConcedidoException;

    /**
     * Solicita un permiso especial o licencia justificada (ej: matrimonio, duelo).
     *
     * @param empleadoId El ID único del empleado que solicita el permiso.
     * @param fechaInicio La fecha de inicio del permiso.
     * @param fechaFin La fecha de fin del permiso.
     * @param motivo El motivo legal o justificado de la solicitud.
     * @throws PermisoNoConcedidoException Si el motivo es inválido o excede el límite de días por ley.
     */
    void solicitarPermisoEspecial(Long empleadoId, LocalDate fechaInicio, LocalDate fechaFin, String motivo)
            throws PermisoNoConcedidoException;
}