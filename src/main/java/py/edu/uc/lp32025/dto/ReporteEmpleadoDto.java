package py.edu.uc.lp32025.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ReporteEmpleadoDto {

    @NotBlank(message = "Nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "Apellido no puede estar vacío")
    private String apellido;

    @NotBlank(message = "Número de documento es obligatorio")
    @Pattern(regexp = "\\d+", message = "Número de documento debe contener solo dígitos")
    @Size(min = 1, max = 20, message = "Número de documento debe tener entre 1 y 20 dígitos")
    private String numeroDocumento;

    @NotNull(message = "Fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimiento;

    @NotNull(message = "Salario es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "Salario debe ser mayor a 0")
    private BigDecimal salario;

    @NotBlank(message = "Tipo de empleado es obligatorio")
    private String tipoEmpleado;

    // GETTERS Y SETTERS
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getNumeroDocumento() { return numeroDocumento; }
    public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public BigDecimal getSalario() { return salario; }
    public void setSalario(BigDecimal salario) { this.salario = salario; }

    public String getTipoEmpleado() { return tipoEmpleado; }
    public void setTipoEmpleado(String tipoEmpleado) { this.tipoEmpleado = tipoEmpleado; }
}
