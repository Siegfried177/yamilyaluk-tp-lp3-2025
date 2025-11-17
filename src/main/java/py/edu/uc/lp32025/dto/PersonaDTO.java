package py.edu.uc.lp32025.dto;

import lombok.Data;
import py.edu.uc.lp32025.domain.Persona;

import java.time.LocalDate;

@Data
public class PersonaDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String numeroDocumento;
    private LocalDate fechaNacimiento;
    private String informacionCompleta;

    public PersonaDTO(Long id, String nombre, String apellido, String numeroDocumento, LocalDate fechaNacimiento) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroDocumento = numeroDocumento;
        this.fechaNacimiento = fechaNacimiento;
    }

    public PersonaDTO() {
    }

    /**
     * Constructor estático (o factory method) para convertir una entidad Persona a DTO.
     * @param persona La entidad base de la jerarquía (Empleado, Contratista, etc.)
     * @return El DTO poblado.
     */
    public static PersonaDTO fromEntity(Persona persona) {
        PersonaDTO dto = new PersonaDTO();
        dto.setId(persona.getId());
        dto.setNombre(persona.getNombre());
        dto.setApellido(persona.getApellido());
        dto.setFechaNacimiento(persona.getFechaNacimiento());
        dto.setNumeroDocumento(persona.getNumeroDocumento());

        // El polimorfismo se asegura aquí, llamando al método correcto de la subclase
        dto.setInformacionCompleta(persona.obtenerInformacionCompleta());

        return dto;
    }
}
