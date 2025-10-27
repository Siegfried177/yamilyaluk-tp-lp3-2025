package py.edu.uc.lp32025.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("EMPLEADO_TIEMPO_COMPLETO")
public class EmpleadoTiempoCompleto extends Persona {

    @Column(nullable = false)
    private BigDecimal salarioMensual;

    @Column(nullable = false)
    private String departamento;

    public EmpleadoTiempoCompleto() {}

    public EmpleadoTiempoCompleto(String nombre, String apellido, String numeroDocumento,
                                  LocalDate fechaNacimiento, BigDecimal salarioMensual, String departamento) {
        super(nombre, apellido, numeroDocumento, fechaNacimiento);
        this.salarioMensual = salarioMensual;
        this.departamento = departamento;
    }

    // -------------------------------------------------
    // IMPLEMENTACIÓN DE MÉTODOS ABSTRACTOS DE PERSONA
    // -------------------------------------------------

    /**
     * Calcula el salario con un descuento del 9%.
     */
    @Override
    public BigDecimal calcularSalario() {
        if (salarioMensual == null) return BigDecimal.ZERO;

        BigDecimal descuento = salarioMensual.multiply(BigDecimal.valueOf(0.09));
        return salarioMensual.subtract(descuento);
    }

    /**
     * Calcula deducciones fijas del 5%.
     */
    @Override
    public BigDecimal calcularDeducciones() {
        if (salarioMensual == null) return BigDecimal.ZERO;
        return salarioMensual.multiply(BigDecimal.valueOf(0.05));
    }

    /**
     * Valida que el salario sea mayor o igual a 2.899.048.
     */
    @Override
    public boolean validarDatosEspecificos() {
        return salarioMensual != null &&
                salarioMensual.compareTo(BigDecimal.valueOf(2_899_048)) >= 0;
    }

    // -------------------------------------------------
    // MÉTODO CONCRETO SOBRESCRITO
    // -------------------------------------------------

    @Override
    public String obtenerInformacionCompleta() {
        return String.format(
                "Empleado Tiempo Completo: %s %s | Doc: %s | Departamento: %s | Salario: %s",
                getNombre(), getApellido(), getNumeroDocumento(), departamento, salarioMensual
        );
    }

    // -------------------------------------------------
    // GETTERS Y SETTERS
    // -------------------------------------------------

    public BigDecimal getSalarioMensual() {
        return salarioMensual;
    }

    public void setSalarioMensual(BigDecimal salarioMensual) {
        this.salarioMensual = salarioMensual;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
}
