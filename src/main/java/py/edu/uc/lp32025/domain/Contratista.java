package py.edu.uc.lp32025.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
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

    public Contratista(String nombre, String apellido, String numeroDocumento, LocalDate fechaNacimiento) {
        super(nombre, apellido, numeroDocumento, fechaNacimiento);
        this.montoPorProyecto = null;
        this.proyectosCompletados = null;
        this.fechaFinContrato = null;
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

}
