package py.edu.uc.lp32025.dto;

import lombok.Data;
import py.edu.uc.lp32025.domain.PosicionGPS; // Asumo que esta clase existe

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class EmpleadoCreationDto {
    // CAMPO DISCRIMINADOR (Usado por el servicio para saber qué clase instanciar)
    private String tipoEmpleado; // Ej: "GERENTE", "EMPLEADO_COMPLETO", "CONTRATISTA"

    // CAMPOS COMUNES DE PERSONA/EMPLEADO
    private String nombre;
    private String apellido;
    private String numeroDocumento;
    private LocalDate fechaNacimiento;
    private LocalDate fechaContratacion;
    private PosicionGPS posicionGPS;

    // CAMPOS ESPECÍFICOS (Pueden ser nulos/omitidos si no aplican al tipo)

    // Específico para Gerente:
    private String areaResponsabilidad;
    private int añosAntiguedad;

    // Específico para EmpleadoTiempoCompleto:
    private BigDecimal salarioMensual;
    private String departamento;

    // Específico para Contratista:
    private BigDecimal montoPorProyecto;     // <-- ¡Añadir este!
    private Integer proyectosCompletados;    // <-- ¡Añadir este!
    private LocalDate fechaFinContrato;
    private BigDecimal tarifaPorHora;
    private Integer horasSemanales;

    // Específico para Empleado por Hora:
    private Integer horasTrabajadas;
}