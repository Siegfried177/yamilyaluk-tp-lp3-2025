package py.edu.uc.lp32025.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PersonaResponseDto {
    private Long id;
    private String tipoPersona; // Muestra el tipo creado (e.g., "Gerente")
    private String nombreCompleto;
    private String numeroDocumento;
    private LocalDate fechaContratacion;
    private String mensaje; // Un mensaje genérico de estado (e.g., "Creado exitosamente")
}