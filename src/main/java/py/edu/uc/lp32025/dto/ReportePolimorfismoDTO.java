package py.edu.uc.lp32025.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ReportePolimorfismoDTO {
    private String nombreCompleto;
    private String tipo;
    private String informacionCompleta;
    private BigDecimal impuestos;
    private boolean datosValidos;

    public ReportePolimorfismoDTO(String nombreCompleto, String tipo, String informacionCompleta,
                                  BigDecimal impuestos, boolean datosValidos) {
        this.nombreCompleto = nombreCompleto;
        this.tipo = tipo;
        this.informacionCompleta = informacionCompleta;
        this.impuestos = impuestos;
        this.datosValidos = datosValidos;
    }
}
