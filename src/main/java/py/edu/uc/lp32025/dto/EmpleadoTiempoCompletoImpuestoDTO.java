package py.edu.uc.lp32025.dto;

import java.math.BigDecimal;

public class EmpleadoTiempoCompletoImpuestoDTO {

    private Long id;
    private String nombreCompleto;
    private BigDecimal salarioMensual;
    private BigDecimal impuestoCalculado;
    private BigDecimal deducciones;
    private String departamento;
    private String informacionCompleta;

    public EmpleadoTiempoCompletoImpuestoDTO() {
    }

    public EmpleadoTiempoCompletoImpuestoDTO(Long id, String nombreCompleto, BigDecimal salarioMensual,
                                             BigDecimal impuestoCalculado, BigDecimal deducciones,
                                             String departamento, String informacionCompleta) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.salarioMensual = salarioMensual;
        this.impuestoCalculado = impuestoCalculado;
        this.deducciones = deducciones;
        this.departamento = departamento;
        this.informacionCompleta = informacionCompleta;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public BigDecimal getSalarioMensual() {
        return salarioMensual;
    }

    public void setSalarioMensual(BigDecimal salarioMensual) {
        this.salarioMensual = salarioMensual;
    }

    public BigDecimal getImpuestoCalculado() {
        return impuestoCalculado;
    }

    public void setImpuestoCalculado(BigDecimal impuestoCalculado) {
        this.impuestoCalculado = impuestoCalculado;
    }

    public BigDecimal getDeducciones() {
        return deducciones;
    }

    public void setDeducciones(BigDecimal deducciones) {
        this.deducciones = deducciones;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getInformacionCompleta() {
        return informacionCompleta;
    }

    public void setInformacionCompleta(String informacionCompleta) {
        this.informacionCompleta = informacionCompleta;
    }
}
