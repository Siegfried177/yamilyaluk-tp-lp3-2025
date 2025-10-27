package py.edu.uc.lp32025.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // permite herencia en JPA
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

    // Constructor vacío requerido por JPA
    public Persona() {}

    // Constructor completo
    public Persona(String nombre, String apellido, String numeroDocumento, LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroDocumento = numeroDocumento;
        this.fechaNacimiento = fechaNacimiento;
    }

    // -----------------------
    // MÉTODOS ABSTRACTOS
    // -----------------------

    // Debe ser implementado por cada subclase
    public abstract BigDecimal calcularSalario();

    // Método abstracto que las subclases deben definir según su lógica
    protected abstract BigDecimal calcularDeducciones();

    // Método abstracto de validación específica
    public abstract boolean validarDatosEspecificos();

    // -----------------------
    // MÉTODOS CONCRETOS
    // -----------------------

    // Puede ser sobrescrito por las subclases
    public String obtenerInformacionCompleta() {
        return String.format("Nombre: %s %s, Documento: %s", nombre, apellido, numeroDocumento);
    }

    // Método concreto auxiliar
    public BigDecimal calcularImpuestoBase(BigDecimal salario) {
        if (salario == null) return BigDecimal.ZERO;
        return salario.multiply(BigDecimal.valueOf(0.10)); // 10% del salario
    }

    public abstract BigDecimal getSalario();

    // Método template: define el flujo del cálculo de impuestos
    public BigDecimal calcularImpuestos() {
        BigDecimal salario = calcularSalario();
        BigDecimal deducciones = calcularDeducciones();
        BigDecimal impuestoBase = calcularImpuestoBase(salario);

        // Estructura del template: puede usarse o modificarse por subclases
        return impuestoBase.subtract(deducciones.max(BigDecimal.ZERO));
    }

    // -----------------------
    // GETTERS Y SETTERS
    // -----------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
