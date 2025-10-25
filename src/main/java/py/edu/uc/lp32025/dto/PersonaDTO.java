package py.edu.uc.lp32025.dto;

import java.time.LocalDate;

public class PersonaDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String numeroDocumento;
    private LocalDate fechaNacimiento;

    public PersonaDTO(Long id, String nombre, String apellido, String numeroDocumento, LocalDate fechaNacimiento) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroDocumento = numeroDocumento;
        this.fechaNacimiento = fechaNacimiento;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getNumeroDocumento() { return numeroDocumento; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
}
