package py.edu.uc.lp32025.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TIPO_PERSONA")
public abstract class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;

    @NotBlank(message = "El número de documento no puede estar vacío")
    @Pattern(regexp = "\\d+", message = "El número de documento debe contener solo dígitos")
    @Size(min = 1, max = 20, message = "El número de documento debe tener entre 1 y 20 dígitos")
    private String numeroDocumento;

    private LocalDate fechaNacimiento;

    // Constructor vacío JPA
    public Persona() {}

    // Constructor básico
    public Persona(String nombre, String apellido, String numeroDocumento, LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroDocumento = numeroDocumento;
        this.fechaNacimiento = fechaNacimiento;
    }

    public abstract BigDecimal calcularSalario();
    protected abstract BigDecimal calcularDeducciones();
    public abstract boolean validarDatosEspecificos();
    public abstract BigDecimal getSalario();

    public String obtenerInformacionCompleta() {
        return String.format("Nombre: %s %s, Documento: %s", nombre, apellido, numeroDocumento);
    }

    public BigDecimal calcularImpuestoBase(BigDecimal salario) {
        if (salario == null) return BigDecimal.ZERO;
        return salario.multiply(BigDecimal.valueOf(0.10));
    }

    public BigDecimal calcularImpuestos() {
        BigDecimal salario = calcularSalario();
        BigDecimal deducciones = calcularDeducciones();
        BigDecimal impuestoBase = calcularImpuestoBase(salario);
        return impuestoBase.subtract(deducciones.max(BigDecimal.ZERO));
    }
}
