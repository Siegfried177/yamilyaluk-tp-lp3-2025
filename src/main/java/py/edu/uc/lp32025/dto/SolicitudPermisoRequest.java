package py.edu.uc.lp32025.dto;

import java.time.LocalDate;
import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class SolicitudPermisoRequest {

    // El ID del empleado es una Path Variable, no parte del cuerpo (Body) del DTO.

    @NotNull(message = "La fecha de inicio es obligatoria.")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria.")
    private LocalDate fechaFin;

    // Solo requerido para permisos especiales
    private String motivo;
}