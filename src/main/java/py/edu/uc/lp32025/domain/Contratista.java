package py.edu.uc.lp32025.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("CONTRATISTA")
public class Contratista extends Persona {

    @Column(nullable = false)
    private BigDecimal montoPorProyecto;

    @Column(nullable = false)
    private Integer proyectosCompletados;

    @Column(nullable = false)
    private LocalDate fechaFinContrato;

    public Contratista() {}

    public Contratista(String nombre, String apellido, String numeroDocumento, LocalDate fechaNacimiento,
                       BigDecimal montoPorProyecto, Integer proyectosCompletados, LocalDate fechaFinContrato) {
        super(nombre, apellido, numeroDocumento, fechaNacimiento);
        this.montoPorProyecto = montoPorProyecto;
        this.proyectosCompletados = proyectosCompletados;
        this.fechaFinContrato = fechaFinContrato;
    }

    // ----------------------------
    // IMPLEMENTACIÓN DE MÉTODOS ABSTRACTOS
    // ----------------------------
    @Override
    public BigDecimal calcularSalario() {
        if (montoPorProyecto == null || proyectosCompletados == null) return BigDecimal.ZERO;
        return montoPorProyecto.multiply(BigDecimal.valueOf(proyectosCompletados));
    }

    // ------------------------
// GETSALARIO SOBRESCRITO
// ------------------------
    @Override
    public BigDecimal getSalario() {
        return calcularSalario();
    }


    @Override
    protected BigDecimal calcularDeducciones() {
        return BigDecimal.ZERO; // Contratistas no tienen deducciones
    }

    @Override
    public boolean validarDatosEspecificos() {
        return proyectosCompletados != null && proyectosCompletados >= 0
                && fechaFinContrato != null && fechaFinContrato.isAfter(LocalDate.now());
    }

    @Override
    public String obtenerInformacionCompleta() {
        return String.format(
                "Contratista: %s %s | Proyectos completados: %d | Monto por proyecto: %s | Fin contrato: %s",
                getNombre(), getApellido(), proyectosCompletados, montoPorProyecto, fechaFinContrato
        );
    }

    // ----------------------------
    // MÉTODO ADICIONAL
    // ----------------------------
    public boolean contratoVigente() {
        return fechaFinContrato != null && !fechaFinContrato.isBefore(LocalDate.now());
    }

    // ----------------------------
    // GETTERS Y SETTERS
    // ----------------------------
    public BigDecimal getMontoPorProyecto() { return montoPorProyecto; }
    public void setMontoPorProyecto(BigDecimal montoPorProyecto) { this.montoPorProyecto = montoPorProyecto; }

    public Integer getProyectosCompletados() { return proyectosCompletados; }
    public void setProyectosCompletados(Integer proyectosCompletados) { this.proyectosCompletados = proyectosCompletados; }

    public LocalDate getFechaFinContrato() { return fechaFinContrato; }
    public void setFechaFinContrato(LocalDate fechaFinContrato) { this.fechaFinContrato = fechaFinContrato; }
}
