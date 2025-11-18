package py.edu.uc.lp32025.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class SolicitudDiasDto {

    private Long empleadoId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String motivo; // Puede ser nulo para vacaciones

}
