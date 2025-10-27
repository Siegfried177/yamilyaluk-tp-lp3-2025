package py.edu.uc.lp32025.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@DiscriminatorValue("EMPLEADO_POR_HORA")
public class EmpleadoPorHora extends Persona {

    @Column(nullable = false)
    private BigDecimal tarifaPorHora;

    @Column(nullable = false)
    private Integer horasTrabajadas;

    public EmpleadoPorHora() {}

    public EmpleadoPorHora(String nombre, String apellido, String numeroDocumento,
                           java.time.LocalDate fechaNacimiento,
                           BigDecimal tarifaPorHora, Integer horasTrabajadas) {
        super(nombre, apellido, numeroDocumento, fechaNacimiento);
        this.tarifaPorHora = tarifaPorHora;
        this.horasTrabajadas = horasTrabajadas;
    }

    // ------------------------
    // IMPLEMENTACIÓN DE MÉTODOS ABSTRACTOS
    // ------------------------
    @Override
    public BigDecimal calcularSalario() {
        if (tarifaPorHora == null || horasTrabajadas == null) return BigDecimal.ZERO;

        int horasExtra = Math.max(0, horasTrabajadas - 40);
        BigDecimal salarioBase = tarifaPorHora.multiply(BigDecimal.valueOf(horasTrabajadas));
        BigDecimal bonus = tarifaPorHora.multiply(BigDecimal.valueOf(horasExtra)).multiply(BigDecimal.valueOf(0.5));

        return salarioBase.add(bonus);
    }

    @Override
    protected BigDecimal calcularDeducciones() {
        BigDecimal salario = calcularSalario();
        return salario.multiply(BigDecimal.valueOf(0.02)); // 2% deducciones
    }

    @Override
    public boolean validarDatosEspecificos() {
        return tarifaPorHora != null && tarifaPorHora.compareTo(BigDecimal.ZERO) > 0
                && horasTrabajadas != null && horasTrabajadas >= 1 && horasTrabajadas <= 80;
    }

    // ------------------------
    // MÉTODO CONCRETO SOBRESCRITO
    // ------------------------
    @Override
    public String obtenerInformacionCompleta() {
        return String.format(
                "Empleado Por Hora: %s %s | Tarifa: %s | Horas: %d | Salario Calculado: %s",
                getNombre(), getApellido(), tarifaPorHora, horasTrabajadas, calcularSalario()
        );
    }

    // ------------------------
// GETSALARIO SOBRESCRITO
// ------------------------
    @Override
    public BigDecimal getSalario() {
        return calcularSalario();
    }


    // ------------------------
    // GETTERS Y SETTERS
    // ------------------------
    public BigDecimal getTarifaPorHora() { return tarifaPorHora; }
    public void setTarifaPorHora(BigDecimal tarifaPorHora) { this.tarifaPorHora = tarifaPorHora; }

    public Integer getHorasTrabajadas() { return horasTrabajadas; }
    public void setHorasTrabajadas(Integer horasTrabajadas) { this.horasTrabajadas = horasTrabajadas; }
}
