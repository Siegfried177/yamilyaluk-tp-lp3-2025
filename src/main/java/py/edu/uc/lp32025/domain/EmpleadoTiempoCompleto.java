package py.edu.uc.lp32025.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Setter
@Getter
@Entity
@DiscriminatorValue("EMPLEADO_TIEMPO_COMPLETO")
public class EmpleadoTiempoCompleto extends Empleado {

    @DecimalMin(value = "2899048", message = "El salario debe ser mayor o igual a 2.899.048")
    private BigDecimal salarioMensual;

    @Column(nullable = false)
    private String departamento;

    public EmpleadoTiempoCompleto() {}

    public EmpleadoTiempoCompleto(String nombre, String apellido, String numeroDocumento,
                                  LocalDate fechaNacimiento,
                                  PosicionGPS posicionGPS, // <-- Nuevo
                                  Avatar avatar, // <-- Nuevo
                                  LocalDate fechaContratacion, // <-- Nuevo
                                  BigDecimal salarioMensual,
                                  String departamento) {

        // Llama al constructor completo de la clase Empleado
        super(nombre, apellido, numeroDocumento, fechaNacimiento,
                posicionGPS, avatar, fechaContratacion);

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

    // ------------------------
// GETSALARIO SOBRESCRITO
// ------------------------
    @Override
    public BigDecimal getSalario() {
        return calcularSalario();
    }


    /**
     * Calcula deducciones fijas del 5%.
     */
    @Override
    public BigDecimal calcularDeducciones() {
        if (this.salarioMensual == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal porcentaje;
        String dept = this.departamento == null ? "" : this.departamento.trim().toLowerCase();

        if ("it".equals(dept) || "information technology".equalsIgnoreCase(dept)) {
            porcentaje = new BigDecimal("0.05"); // 5%
        } else {
            porcentaje = new BigDecimal("0.03"); // 3%
        }

        // deducciones = salarioBase * porcentaje
        return this.salarioMensual.multiply(porcentaje).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Valida que el salario sea mayor o igual a 2.899.048.
     */
    @Override
    public boolean validarDatosEspecificos() {
        if (salarioMensual == null || salarioMensual.compareTo(new BigDecimal("2900000")) < 0) {
            return false; // salario inválido
        }
        if (departamento == null || departamento.isBlank()) {
            return false; // departamento inválido
        }
        return true; // todo bien
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

}
